package com.zhuorui.securities.base2app.network

import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import io.reactivex.functions.Consumer
import java.net.ConnectException
import java.net.UnknownHostException

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-06-19 17:03
 *    desc   : 抽象的一个网络请求失败的Consumer
 */
open class NetErrorConsumer<T : Throwable> : Consumer<T> {

    override fun accept(t: T) {
        LogInfra.Log.e("NetWork", t.message)
        // 处理失败
        if (t is ConnectException || t is UnknownHostException) {
            ResUtil.getString(R.string.network_anomaly)?.let { ToastUtil.instance.toast(it) }
        } else {
            ToastUtil.instance.toast("服务器开小差了，请稍后再试")
        }
    }
}