package com.zhuorui.securities.openaccount.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 11:28
 *    desc   :
 */
class OASignatureViewModel : ViewModel() {

    val agreement: MutableLiveData<Boolean> = MutableLiveData()

    init {
        agreement.value = false
    }
}