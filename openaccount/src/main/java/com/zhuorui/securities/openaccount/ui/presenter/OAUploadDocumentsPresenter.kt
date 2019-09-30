package com.zhuorui.securities.openaccount.ui.presenter

import com.zhuorui.securities.base2app.ui.fragment.AbsNetPresenter
import com.zhuorui.securities.openaccount.ui.view.OAUploadDocumentsView
import com.zhuorui.securities.openaccount.ui.viewmodel.OAUploadDocumentsViewModel

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:19
 *    desc   :
 */
class OAUploadDocumentsPresenter : AbsNetPresenter<OAUploadDocumentsView, OAUploadDocumentsViewModel>() {

    override fun init() {
        super.init()
        view?.init()
    }


}