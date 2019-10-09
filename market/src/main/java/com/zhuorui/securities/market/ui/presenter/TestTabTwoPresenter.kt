package com.zhuorui.securities.market.ui.presenter

import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.rxbus.EventThread
import com.zhuorui.securities.base2app.rxbus.RxSubscribe
import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.market.event.TestEvent
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.market.model.TestEnum
import com.zhuorui.securities.market.ui.view.TestTabTwoView
import com.zhuorui.securities.market.ui.viewmodel.TestTabTwoViewModel

class TestTabTwoPresenter : AbsNetPresenter<TestTabTwoView,TestTabTwoViewModel>(){

    override fun init() {
        super.init()
    }
    private var ts: TestEnum? = null


    fun setType(type: TestEnum?) {
        ts = type
    }
    @RxSubscribe(observeOnThread = EventThread.MAIN)
    fun onNotifyStockCountEvent(event: TestEvent) {
        if(event.enum==ts){
            LogInfra.Log.e("tttttttt-2++++++",event.str)
        }
    }

}