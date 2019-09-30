package com.zhuorui.securities.openaccount.ui.presenter

import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.openaccount.ui.view.OABiopsyView
import com.zhuorui.securities.openaccount.ui.viewmodel.OABiopsyViewModel

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/26
 * Desc:
 */
class OABiopsyPresenter : AbsNetPresenter<OABiopsyView,OABiopsyViewModel>(){

    override fun init() {
        super.init()
        view?.init()
    }

}