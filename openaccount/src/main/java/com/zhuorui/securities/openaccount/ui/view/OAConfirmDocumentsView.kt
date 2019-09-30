package com.zhuorui.securities.openaccount.ui.view

import com.zhuorui.securities.base2app.ui.fragment.AbsView
import com.zhuorui.securities.openaccount.model.OADataTips

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:16
 *    desc   :
 */
interface OAConfirmDocumentsView : AbsView {
    fun init()
    fun setBirthday(date: String?)
    fun setName(name: String?)
    fun setGender(gender: String?)
    fun setCardNo(cardNo: String?)
    fun setCardValidStartDate(date: String?)
    fun setCardValidEndDate(date: String?)
    fun setCardAddress(address: String?)
}
