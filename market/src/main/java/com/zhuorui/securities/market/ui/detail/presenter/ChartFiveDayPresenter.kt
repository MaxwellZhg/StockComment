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
import com.zhuorui.securities.market.socket.response.StocksFiveDayKlineResponse
import com.zhuorui.securities.market.stockChart.data.TimeDataManage
import com.zhuorui.securities.market.ui.detail.view.FiveDayKlineView
import com.zhuorui.securities.market.ui.detail.viewmodel.FiveDayKlineViewModel
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/22 10:32
 *    desc   :
 */
class ChartFiveDayPresenter : AbsEventPresenter<FiveDayKlineView, FiveDayKlineViewModel>() {

    private val disposables = LinkedList<Disposable>()
    private var requestIds = ArrayList<String>()

    fun loadKlineFiveDayData() {
        // 发起自选股K线拉取补偿数据
        val klineGetDaily = StockKlineGetDaily("SZ", "000001", 0, 0, 0)
        val reqId = SocketClient.getInstance().requestGetFiveDayKline(klineGetDaily)
        requestIds.add(reqId)
    }

    /**
     * 拉取日K数据
     */
    @RxSubscribe(observeOnThread = EventThread.COMPUTATION)
    fun onStocksFiveDayKlineResponse(response: StocksFiveDayKlineResponse) {
        if (requestIds.remove(response.respId)) {
            val kLineData = response.data
            LogInfra.Log.d(TAG, "onStocksFiveDayKlineResponse ... klineData size = " + kLineData?.size)

            // 展示K线数据
            var kDataManager = viewModel?.kDataManager
            if (kDataManager == null) {
                kDataManager = TimeDataManage()
            }
            kDataManager.parseKlineData(kLineData, "000001.IDX.SZ", 0.0, true)
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
            val stockTopic = StockTopic(StockTopicDataTypeEnum.k5day, "SZ", "000001", 1)
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
            loadKlineFiveDayData()
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