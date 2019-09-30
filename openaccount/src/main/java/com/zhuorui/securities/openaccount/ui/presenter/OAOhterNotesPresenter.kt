package com.zhuorui.securities.openaccount.ui.presenter

import com.zhuorui.securities.base2app.ui.fragment.AbsPresenter
import com.zhuorui.securities.openaccount.ui.view.OAInvestmentExperienceView
import com.zhuorui.securities.openaccount.ui.view.OAOhterNotesView
import com.zhuorui.securities.openaccount.ui.view.OAPersonalInformationView
import com.zhuorui.securities.openaccount.ui.view.OAUploadDocumentsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAInvestmentExperienceViewModel
import com.zhuorui.securities.openaccount.ui.viewmodel.OAOhterNotesViewModel
import com.zhuorui.securities.openaccount.ui.viewmodel.OAPersonalInformationViewModel
import com.zhuorui.securities.openaccount.ui.viewmodel.OAUploadDocumentsViewModel

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:19
 *    desc   :
 */
class OAOhterNotesPresenter : AbsPresenter<OAOhterNotesView, OAOhterNotesViewModel>() {
    override fun init() {
        super.init()
        view?.init()
    }


}