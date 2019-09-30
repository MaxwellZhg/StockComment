package com.zhuorui.securities.openaccount.ui.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView
import com.zhuorui.securities.openaccount.model.OADataTips

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:16
 *    desc   :
 */
interface OADataTipsView : AbsView {
    fun init()

    fun notifyDataSetChanged(list: List<OADataTips>?)
}