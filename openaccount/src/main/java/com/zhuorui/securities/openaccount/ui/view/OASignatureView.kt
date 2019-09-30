package com.zhuorui.securities.openaccount.ui.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 11:27
 *    desc   :
 */
interface OASignatureView : AbsView {

    fun showLoading()

    fun jumpToNext()

    fun hideLoading()
}