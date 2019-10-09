package com.zhuorui.securities.market.ui.presenter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import com.zhuorui.commonwidget.ScreenCentralStateToast
import com.zhuorui.securities.base2app.Cache
import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.network.BaseResponse
import com.zhuorui.securities.base2app.network.Network
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.event.LoginStateChangeEvent
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.config.LocalStocksConfig
import com.zhuorui.securities.market.event.*
import com.zhuorui.securities.market.model.*
import com.zhuorui.securities.market.net.IStockNet
import com.zhuorui.securities.market.net.request.DeleteStockRequest
import com.zhuorui.securities.market.net.request.RecommendStocklistRequest
import com.zhuorui.securities.market.net.request.StickyOnTopStockRequest
import com.zhuorui.securities.market.net.request.SynStockRequest
import com.zhuorui.securities.market.net.response.RecommendStocklistResponse
import com.zhuorui.securities.market.socket.SocketClient
import com.zhuorui.securities.market.socket.push.StocksTopicPriceResponse
import com.zhuorui.securities.market.ui.view.TopicStockListView
import com.zhuorui.securities.market.ui.viewmodel.TopicStockListViewModel
import com.zhuorui.securities.market.util.MathUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/19 17:13
 *    desc   :
 */
@Suppress("NAME_SHADOWING")
class TopicStockListPresenter : AbsNetPresenter<TopicStockListView, TopicStockListViewModel>() {

    private var ts: StockTsEnum? = null
    private val disposables = LinkedList<Disposable>()

    override fun init() {
        super.init()
        view?.init()
    }

    fun setType(type: StockTsEnum?) {
        ts = type
    }

    fun setLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        // 监听datas的变化
        lifecycleOwner.let {
            viewModel?.datas?.observe(it,
                androidx.lifecycle.Observer<List<StockMarketInfo>> { t ->
                    RxBus.getDefault().post(NotifyStockCountEvent(ts, t.size))
                    view?.notifyDataSetChanged(t)
                })
        }
    }

    /**
     * 加载推荐自选股列表
     */
    fun requestStocks(currentPage: Int, pageSize: Int) {
        val request = RecommendStocklistRequest(
            if (ts == StockTsEnum.HS) StockTsEnum.SH.name + "," + StockTsEnum.SZ.name else ts?.name,
            currentPage,
            pageSize,
            transactions.createTransaction()
        )
        Cache[IStockNet::class.java]?.list(request)
            ?.enqueue(Network.IHCallBack<RecommendStocklistResponse>(request))
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onRecommendStocklistResponse(response: RecommendStocklistResponse) {
        if (!transactions.isMyTransaction(response)) return
        val datas = response.data?.datas
        if (datas.isNullOrEmpty()) return
        viewModel?.datas?.value = datas

        // 订阅价格
        var disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            emitter.onNext(topicPrice(datas))
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .subscribe()
        disposables.add(disposable)

        if (ts == null) {
            // 保存本地数据
            disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                emitter.onNext(LocalStocksConfig.read().replaceAll(datas))
                emitter.onComplete()
            }).subscribeOn(Schedulers.io())
                .subscribe()
            disposables.add(disposable)
        }
    }

    /**
     *  发起价格订阅
     */
    private fun topicPrice(datas: MutableList<StockMarketInfo>): Boolean {
        for (item in datas) {
            val stockTopic = item.ts?.let {
                item.code?.let { it1 ->
                    item.type?.let { it2 ->
                        StockTopic(
                            StockTopicDataTypeEnum.price, it,
                            it1, it2
                        )
                    }
                }
            }
            SocketClient.getInstance().bindTopic(stockTopic)
        }
        return true
    }

    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onStocksTopicPriceResponse(response: StocksTopicPriceResponse) {

        val datas = viewModel?.datas?.value ?: return

        val stockPriceDatas = response.body

        for (index in datas.indices) {
            val item = datas[index]
            for (sub in stockPriceDatas) {
                if (item.ts == sub.ts && item.code == sub.code) {
                    // 更新数据
                    val item = datas[index]
                    item.price = sub.price!!
                    item.diffRate =
                        MathUtil.division((sub.price!! - sub.openPrice!!) * 100, sub.openPrice!!)
                    view?.notifyItemChanged(index)
                    break
                }
            }
        }
    }

    /**
     * 添加自选股
     */
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onAddTopicStockEvent(event: AddTopicStockEvent) {
        val stockTs = event.stock.ts
        if (ts == null || stockTs.equals(ts?.name) || (ts == StockTsEnum.HS && (stockTs == StockTsEnum.SH.name || stockTs == StockTsEnum.SZ.name))) {
            var datas = viewModel?.datas?.value
            if (datas.isNullOrEmpty()) {
                datas = ArrayList()
            }

            for (item in datas) {
                if (item.ts.equals(stockTs) && item.code.equals(stockTs)) return
            }

            // 发起订阅价格
            val stockTopic = stockTs?.let {
                event.stock.code?.let { it1 ->
                    event.stock.type?.let { it2 ->
                        StockTopic(
                            StockTopicDataTypeEnum.price,
                            it, it1, it2
                        )
                    }
                }
            }
            SocketClient.getInstance().bindTopic(stockTopic)

            // 显示新添加的自选股
            val stock = StockMarketInfo()
            stock.id = event.stock.id
            stock.ts = stockTs
            stock.code = event.stock.code
            stock.name = event.stock.name
            stock.type = event.stock.type
            stock.tsCode = event.stock.tsCode

            datas.add(stock)

            if (viewModel?.datas?.value.isNullOrEmpty()) {
                val disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                    viewModel?.datas?.value = datas
                    emitter.onNext(true)
                    emitter.onComplete()
                }).subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe()
                disposables.add(disposable)
            } else {
                view?.notifyDataSetChanged(datas)
            }
            RxBus.getDefault().post(NotifyStockCountEvent(ts, datas.size))

            // TODO 保存本地数据
            val disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                emitter.onNext(LocalStocksConfig.read().add(stock))
                emitter.onComplete()
            }).subscribeOn(Schedulers.io())
                .subscribe()
            disposables.add(disposable)
        }
    }

    fun onStickyOnTop(item: StockMarketInfo?) {
        // 判断是否登录
        if (LocalAccountConfig.read().isLogin()) {
            val request = StickyOnTopStockRequest(item!!, item.id!!, transactions.createTransaction())
            Cache[IStockNet::class.java]?.stickyOnTop(request)
                ?.enqueue(Network.IHCallBack<BaseResponse>(request))
        } else {
            stickyOnTop(item)
        }
    }

    private fun stickyOnTop(item: StockMarketInfo?) {
        // 更换自选股位置
        val datas = viewModel?.datas?.value ?: return
        datas.remove(item)
        item?.let { datas.add(0, it) }
        // 刷新界面
        view?.notifyDataSetChanged(datas)
        // 提示置顶成功
        ScreenCentralStateToast.show(ResUtil.getString(R.string.sticky_on_top_successful))
    }

    fun onDelete(item: StockMarketInfo?) {
        // 判断是否登录
        if (LocalAccountConfig.read().isLogin()) {
            val ids = arrayOf(item?.id)
            val request = DeleteStockRequest(item!!, ids, transactions.createTransaction())
            Cache[IStockNet::class.java]?.delelte(request)
                ?.enqueue(Network.IHCallBack<BaseResponse>(request))
        } else {
            // 发送删除事件
            item?.let {
                RxBus.getDefault().post(DeleteTopicStockEvent(it))
                // 提示删除成功
                ScreenCentralStateToast.show(ResUtil.getString(R.string.delete_successful))
            }
        }
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onDeleteTopicStockEvent(event: DeleteTopicStockEvent) {
        val datas = viewModel?.datas?.value ?: return
        val stockTs = event.stockInfo.ts
        if (ts == null || stockTs.equals(ts?.name) || (ts == StockTsEnum.HS && (stockTs == StockTsEnum.SH.name || stockTs == StockTsEnum.SZ.name))) {
            for (element in datas) {
                if (element.tsCode.equals(event.stockInfo.tsCode)) {
                    datas?.remove(element)
                    break
                }
            }
            // 刷新界面
            view?.notifyDataSetChanged(datas)
            // 取消订阅
            val stockTopic = event.stockInfo.ts?.let {
                event.stockInfo.code?.let { it1 ->
                    event.stockInfo.type?.let { it2 ->
                        StockTopic(
                            StockTopicDataTypeEnum.price, it,
                            it1, it2
                        )
                    }
                }
            }
            SocketClient.getInstance().bindTopic(stockTopic)
            // 更新最新自选股数目
            RxBus.getDefault().post(NotifyStockCountEvent(ts, datas?.size!!))
        }
    }

    override fun onBaseResponse(response: BaseResponse) {
        super.onBaseResponse(response)
        if (response.request is DeleteStockRequest) {
            // 发送删除事件
            RxBus.getDefault()
                .post(DeleteTopicStockEvent((response.request as DeleteStockRequest).custom as StockMarketInfo))
            // 提示删除成功
            ScreenCentralStateToast.show(ResUtil.getString(R.string.delete_successful))
        } else if (response.request is StickyOnTopStockRequest) {
            stickyOnTop((response.request as StickyOnTopStockRequest).custom as StockMarketInfo)
        } else if (response.request is SynStockRequest) {
            // 通知同步完成
            RxBus.getDefault().post(SynStockEvent())
        }
    }

    /**
     * 登录状态发生改变
     */
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onLoginStateChangeEvent(event: LoginStateChangeEvent) {
        // 只同步全部列表中的自选股
        if (ts == null && event.isLogin) {
            val datas = viewModel?.datas?.value ?: return
            val request = SynStockRequest(datas, transactions.createTransaction())
            Cache[IStockNet::class.java]?.synStock(request)
                ?.enqueue(Network.IHCallBack<BaseResponse>(request))
        }
    }

    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onSynStockEvent(event: SynStockEvent) {
        // 同步完成，重新拉取自选股列表
        view?.requestStocks()
    }

    override fun destroy() {
        super.destroy()

        // 释放disposable
        if (disposables.isNullOrEmpty()) return
        for (disposable in disposables) {
            disposable.dispose()
        }
        disposables.clear()
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onNotifyStockCountEvent(event: TabEvent) {
        if(event.event==ts){
            when(ts) {
                StockTsEnum.HS -> {
                 Log.e("tttttttt-1++++++","11111")
                }
               StockTsEnum.HK-> {
                   Log.e("tttttttt-2++++++","222222")
                }
                null-> {
                    Log.e("tttttttt-3++++++","333333")
                }
            }
        }
    }
}