package com.zhuorui.securities.market.ui.presenter

import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsEventPresenter
import com.zhuorui.securities.market.event.NotifyStockCountEvent
import com.zhuorui.securities.market.event.SocketDisconnectEvent
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.market.socket.SocketClient
import com.zhuorui.securities.market.ui.view.StockTabView
import com.zhuorui.securities.market.ui.viewmodel.StockTabViewModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/19 15:44
 *    desc   :
 */
class StockTabPresenter : AbsEventPresenter<StockTabView, StockTabViewModel>() {

    override fun init() {
        super.init()
        viewModel?.mfragment?.let { view?.init(it) }

        // 启动长链接
        SocketClient.getInstance()?.connect()
    }

    @RxSubscribe(observeOnThread = EventThread.SINGLE)
    fun onSocketDisconnectEvent(event: SocketDisconnectEvent) {
        LogInfra.Log.d(TAG, "onSocketDisconnectEvent()")
        Thread.sleep(1000)
        SocketClient.getInstance()?.connect()
    }

    fun toggleStockTab() {
        viewModel?.toggleStockTab?.value = !viewModel?.toggleStockTab?.value!!
        view?.toggleStockTab(viewModel?.toggleStockTab?.value!!)
    }

    override fun destroy() {
        super.destroy()

        // 关闭长链接
        SocketClient.getInstance()?.destroy()
    }

    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onNotifyStockCountEvent(event: NotifyStockCountEvent) {
        when (event.ts) {
            null -> viewModel?.allNum?.value = event.count
            StockTsEnum.HK -> viewModel?.hkNum?.value = event.count
            else -> viewModel?.hsNum?.value = event.count
        }
    }
}