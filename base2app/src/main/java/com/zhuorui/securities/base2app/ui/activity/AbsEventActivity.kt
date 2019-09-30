package com.zhuorui.securities.base2app.ui.activity

import android.os.Bundle
import com.zhuorui.securities.base2app.network.Transactions
import com.zhuorui.securities.base2app.rxbus.IRxBusEvent
import com.zhuorui.securities.base2app.rxbus.RxBus

/**
 * author : Pengxianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019-05-20 14:13
 * desc   : 抽象带有RxBus的Activity
 */
abstract class AbsEventActivity : AbsActivity(), IRxBusEvent {

    protected var transactions = Transactions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerRxBus()
    }

    override fun registerRxBus() {
        val registered = RxBus.getDefault().isRegistered(this)
        if (!registered) {
            RxBus.getDefault().register(this)
        }
    }

    override fun unregisterRxBus() {
        val registered = RxBus.getDefault().isRegistered(this)
        if (registered) {
            RxBus.getDefault().unregister(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        transactions.clear()
        unregisterRxBus()
    }
}