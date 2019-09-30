package com.zhuorui.securities.market.ui.detail.presenter

import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsEventPresenter
import com.zhuorui.securities.market.event.SocketAuthCompleteEvent
import com.zhuorui.securities.market.model.StockTopic
import com.zhuorui.securities.market.model.StockTopicDataTypeEnum
import com.zhuorui.securities.market.socket.SocketClient
import com.zhuorui.securities.market.socket.request.StockKlineGetDaily
import com.zhuorui.securities.market.socket.response.StocksDayKlineResponse
import com.zhuorui.securities.market.stockChart.data.KLineDataManage
import com.zhuorui.securities.market.ui.detail.view.KlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.KlineViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 9:52
 *    desc   :
 */
class ChartKLinePresenter : AbsEventPresenter<KlineView, KlineViewModel>() {

    var kType: Int = 0//日K：1；周K：7；月K：30
    var land: Boolean = false

    private var indexType = 1
    private val disposables = LinkedList<Disposable>()
    private var requestIds = ArrayList<String>()

    /**
     * 拉取补偿数据
     */
    fun loadKlineData() {
        // TODO 测试代码
        if (kType == 1) {
            val klineGetDaily = StockKlineGetDaily("SZ", "000001", 0, 0, 0)
            val reqId = SocketClient.getInstance().requestGetDailyKline(klineGetDaily)
            requestIds.add(reqId)
        }
    }

    fun loadIndexData() {
        var kDataManager: KLineDataManage? = viewModel?.kDataManager ?: return

        indexType = if (indexType < 5) ++indexType else 1
        when (indexType) {
            1//成交量
            -> view?.doBarChartSwitch(indexType)
            2//请求MACD
            -> {
                kDataManager?.initMACD()
                view?.doBarChartSwitch(indexType)
            }
            3//请求KDJ
            -> {
                kDataManager?.initKDJ()
                view?.doBarChartSwitch(indexType)
            }
            4//请求BOLL
            -> {
                kDataManager?.initBOLL()
                view?.doBarChartSwitch(indexType)
            }
            5//请求RSI
            -> {
                kDataManager?.initRSI()
                view?.doBarChartSwitch(indexType)
            }
            else -> {
            }
        }
    }

    /**
     * 拉取日K数据
     */
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onStocksTopicDayKlineResponse(response: StocksDayKlineResponse) {
        if (requestIds.remove(response.respId)) {
            val kTimeData = response.data
            LogInfra.Log.d(TAG, "onStocksTopicDayKlineResponse ... klineData size = " + kTimeData?.size)

            // 展示K线数据
            var kDataManager = viewModel?.kDataManager
            if (kDataManager == null) {
                kDataManager = KLineDataManage(context)
            }
            kDataManager.parseKlineData(kTimeData, "000001.IDX.SZ", land)
            var disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                view?.setDataToChart(kDataManager)
                emitter.onNext(true)
                emitter.onComplete()
            }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe()
            disposables.add(disposable)

            // 更新本地数据
//            disposable = Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
//                val result = LocalStocksKlineDataConfig.instance?.replaceData("SZ", "000001", kTimeData)
//                if (result != null) {
//                    emitter.onNext(result)
//                }
//                emitter.onComplete()
//            }).subscribeOn(Schedulers.io())
//                .subscribe()
//            disposables.add(disposable)

            // 订阅正常数据
            val stockTopic = StockTopic(StockTopicDataTypeEnum.kday, "SZ", "000001", 1)
            SocketClient.getInstance().bindTopic(stockTopic)
            viewModel?.stockTopic = stockTopic
        }
    }

    @RxSubscribe(observeOnThread = EventThread.NEW)
    fun onSocketAuthCompleteEvent(event: SocketAuthCompleteEvent) {
        // 恢复订阅
        val stockTopic = viewModel?.stockTopic
        if (stockTopic != null) {
            LogInfra.Log.d(TAG, "onSocketAuthCompleteEvent 先再拉一次补偿书记，再恢复订阅")
            loadKlineData()
        }
    }

    override fun destroy() {
        super.destroy()
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