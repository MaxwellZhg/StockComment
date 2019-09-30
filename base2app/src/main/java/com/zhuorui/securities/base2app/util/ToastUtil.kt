package com.zhuorui.securities.base2app.util

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.StringRes
import com.zhuorui.securities.base2app.BaseApplication

/**
 * Created by xieyingwu on 2018/5/8
 * Toast工具类
 */
class ToastUtil private constructor() {

    private var toast: Toast? = null

    private object Builder {
        val instance = ToastUtil()
    }

    @SuppressLint("ShowToast")
    fun toast(@StringRes res: Int, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context, res, duration)
        }
        toast?.setText(res)
        toast?.show()
    }

    @SuppressLint("ShowToast")
    fun toast(cs: CharSequence, duration: Int) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context, cs, duration)
        }
        toast?.setText(cs)
        toast?.show()
    }

    fun toast(@StringRes strRes: Int) {
        toast(strRes, Toast.LENGTH_SHORT)
    }

    fun toast(str: CharSequence) {
        toast(str, Toast.LENGTH_SHORT)
    }

    fun toastLong(@StringRes res: Int) {
        toast(res, Toast.LENGTH_LONG)
    }

    fun toastLong(cs: CharSequence) {
        toast(cs, Toast.LENGTH_LONG)
    }


    companion object {

        val instance: ToastUtil
            get() = Builder.instance
    }
}
