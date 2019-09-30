package com.zhuorui.securities.personal.ui.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.personal.R

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:
 */
class LoginPswViewModel :ViewModel(){
    var strdisct = ObservableField<String>()
    var code = ObservableField<String>()
    init {
        strdisct.set(ResUtil.getString(R.string.china_mother_land))
        code.set(ResUtil.getString(R.string.china_area_phone))
    }
}