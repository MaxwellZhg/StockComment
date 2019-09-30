package com.zhuorui.securities.market.ui.detail.presenter

import android.annotation.SuppressLint
import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsEventPresenter
import com.zhuorui.securities.market.db.MinuteKlineDataDBManager
import com.zhuorui.securities.market.event.SocketAuthCompleteEvent
import com.zhuorui.securities.market.model.StockTopic
import com.zhuorui.securities.market.model.StockTopicDataTypeEnum
import com.zhuorui.securities.market.socket.SocketClient
import com.zhuorui.securities.market.socket.push.StocksTopicMinuteKlineResponse
import com.zhuorui.securities.market.socket.request.StockMinuteKline
import com.zhuorui.securities.market.socket.response.GetStocksMinuteKlineResponse
import com.zhuorui.securities.market.socket.vo.kline.StockMinuteVo
import com.zhuorui.securities.market.stockChart.data.TimeDataManage
import com.zhuorui.securities.market.ui.detail.view.OneDayKlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.OneDayKlineViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 9:52
 *    desc   :
 */
class ChartOneDayPresenter : AbsEventPresenter<OneDayKlineView, OneDayKlineViewModel>() {

    private var requestIds = ArrayList<String>()
    private val disposables = LinkedList<Disposable>()

    /**
     * 加载数据库
     */
    fun loadDBKlineMinuteData() {
        val disposable = Observable.create(ObservableOnSubscribe<TimeDataManage> { emitter ->
            MinuteKlineDataDBManager.getInstance("SZ000001").queryAll().let {
                LogInfra.Log.d(TAG, "Local kline cache data size : " + it.size)
                if (!it.isNullOrEmpty()) {
                    var kDataManager = viewModel?.kDataManager
                    if (kDataManager == null) {
                        kDataManager = TimeDataManage()
                        kDataManager.parseTimeData(it, "000001.IDX.SZ", 0.0, true)
                        viewModel?.kDataManager = kDataManager
                    }
                    emitter.onNext(kDataManager)
                }
            }
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val kDataManager = viewModel?.kDataManager
                if (kDataManager == null) {
                    view?.setDataToChart(it)
                }
            }
        disposables.add(disposable)
    }

    /**
     * 加载网络数据
     */
    fun loadKNetlineMinuteData() {
        // 发起自选股K线拉取补偿数据
        val stockMinuteKline = StockMinuteKline("SZ", "000001", 1)
        val reqId = SocketClient.getInstance().requestGetMinuteKline(stockMinuteKline)
        requestIds.add(reqId)
    }


    /**
     * 拉取自选股补偿分时数据
     */
    @SuppressLint("CheckResult")
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onGetStocksMinuteKlineResponse(response: GetStocksMinuteKlineResponse) {
        if (requestIds.remove(response.respId)) {
            val klineData = response.data

            LogInfra.Log.d(TAG, "onGetStocksMinuteKlineResponse ... klineData size = " + klineData?.size)

            // 展示K线数据
            var kDataManager = viewModel?.kDataManager
            if (kDataManager == null) {
                kDataManager = TimeDataManage()
            }
            kDataManager.parseTimeData(klineData, "000001.IDX.SZ", 0.0, true)
            var disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                view?.setDataToChart(kDataManager)
                emitter.onNext(true)
                emitter.onComplete()
            }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
            disposables.add(disposable)

            // 更新本地数据
            disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                val manager = MinuteKlineDataDBManager.getInstance("SZ000001")
                manager.deleteAll()
                manager.insertInTx(klineData)
                emitter.onNext(true)
                emitter.onComplete()
            }).subscribeOn(Schedulers.io())
                .subscribe()
            disposables.add(disposable)

            // 订阅正常数据
            val stockTopic = StockTopic(StockTopicDataTypeEnum.kminute, "SZ", "000001", 1)
            SocketClient.getInstance().bindTopic(stockTopic)
            viewModel?.stockTopic = stockTopic
        }
    }

    /**
     * 推送自选股分时数据
     */
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onStocksTopicMinuteKlineResponse(response: StocksTopicMinuteKlineResponse) {
        val klineData = (response.body?.klineData as StockMinuteVo).data
        LogInfra.Log.d(TAG, "onStocksTopicMinuteKlineResponse ... klineData size = " + klineData?.size)

        // 在子线程中整合新数据再更新到界面
        val kDataManager = viewModel?.kDataManager
        kDataManager?.parseTimeData(klineData, "000001.IDX.SZ", 0.0, false)
        var disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            view?.setDataToChart(kDataManager)
            emitter.onNext(true)
            emitter.onComplete()
        }).subscribeOn(AndroidSchedulers.mainThread())
            .subscribe()
        disposables.add(disposable)

        // 缓存K线数据到本地
        disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            MinuteKlineDataDBManager.getInstance("SZ000001").insertInTx(klineData)
            emitter.onNext(true)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .subscribe()
        disposables.add(disposable)
    }

    @RxSubscribe(observeOnThread = EventThread.NEW)
    fun onSocketAuthCompleteEvent(event: SocketAuthCompleteEvent) {
        val stockTopic = viewModel?.stockTopic
        // 恢复订阅
        if (stockTopic != null) {
            LogInfra.Log.d(TAG, "onSocketAuthCompleteEvent 先再拉一次补偿书记，再恢复订阅")
            loadKNetlineMinuteData()
        }
    }

    override fun destroy() {
        super.destroy()
        // 关闭数据库
        val disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
            MinuteKlineDataDBManager.getInstance("SZ000001").closeDataBase()
            emitter.onNext(true)
            emitter.onComplete()
        }).subscribeOn(Schedulers.io())
            .subscribe()
        disposables.add(disposable)

        // 取消数据订阅
        val stockTopic = viewModel?.stockTopic
        if (stockTopic != null) {
            SocketClient.getInstance().unBindTopic(stockTopic)
        }
        // 释放disposable
        if (disposables.isNullOrEmpty()) return
        for (disposable in disposables) {
            disposable.dispose()
        }
        disposables.clear()
    }
}