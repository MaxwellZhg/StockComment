package com.zhuorui.securities.openaccount.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/28 9:59
 *    desc   :
 */
class OARiskDisclosureViewModel : ViewModel() {

    /**
     * 风险披露播放状态
     */
    val playingRisk: MutableLiveData<Boolean> = MutableLiveData()

    init {
        playingRisk.value = false
    }
}