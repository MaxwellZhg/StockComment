package com.zhuorui.securities.openaccount.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zhuorui.securities.openaccount.model.OADataTips

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-20 14:17
 *    desc   :
 */
class OADataTipsViewModel : ViewModel() {
    var datas: MutableLiveData<MutableList<OADataTips>> = MutableLiveData()
}