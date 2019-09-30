package com.zhuorui.securities.openaccount.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/14
 * Desc:
 */
class OpenAccountTabViewModel : ViewModel(){

    var str= ObservableField<String>()

    init {
        str.set("开户")
    }
}