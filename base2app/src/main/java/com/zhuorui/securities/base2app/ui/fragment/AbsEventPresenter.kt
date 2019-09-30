package com.zhuorui.securities.base2app.ui.fragment

import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.network.Transactions
import com.zhuorui.securities.base2app.rxbus.IRxBusEvent
import com.zhuorui.securities.base2app.rxbus.RxBus

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/16 15:32
 *    desc   :
 */
open class AbsEventPresenter<V : AbsView, VM : ViewModel> : AbsPresenter<V, VM>(), IRxBusEvent {

    protected var transactions = Transactions()


    override fun init() {
        super.init()
        registerRxBus()
    }

    override fun registerRxBus() {
        val rxBus = RxBus.getDefault()
        val registered = rxBus.isRegistered(this)
        if (!registered) {
            rxBus.register(this)
        }
    }

    override fun unregisterRxBus() {
        val rxBus = RxBus.getDefault()
        val registered = rxBus.isRegistered(this)
        if (registered) {
            rxBus.unregister(this)
        }
    }

    override fun destroy() {
        super.destroy()
        unregisterRxBus()
    }
}