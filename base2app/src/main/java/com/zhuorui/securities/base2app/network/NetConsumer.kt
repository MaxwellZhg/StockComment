package com.zhuorui.securities.base2app.network

import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.util.JsonUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import io.reactivex.functions.Consumer

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-06-19 18:07
 *    desc   : 抽象的一个网络请求成功的Consumer
 */
abstract class NetConsumer<T : BaseResponse> : Consumer<T> {

    override fun accept(t: T) {
        if (t.isSuccess()) {
            LogInfra.Log.w(Network.TAG, "Response: " + JsonUtil.toJson(t))
            onResponse(t)
        } else {
            t.msg?.let { ToastUtil.instance.toast(it) }
        }
    }

    abstract fun onResponse(response: T)
}