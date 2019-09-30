package com.zhuorui.securities.base2app.network

import com.zhuorui.securities.base2app.R
import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.util.JsonUtil
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.base2app.util.ToastUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.UnknownHostException

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-06-18 16:34
 *    desc   : 抽象的一个网络请求Observer
 */
abstract class NetObserver<T : BaseResponse> : Observer<T> {

    private var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        disposable = d
    }

    override fun onNext(t: T) {
        LogInfra.Log.w(Network.TAG, "Response: " + JsonUtil.toJson(t))

        if (t.isSuccess()) {
            onResponse(t)
        } else {
            t.msg?.let { ToastUtil.instance.toast(it) }
        }
        disposable?.dispose()
    }

    override fun onError(e: Throwable) {
        LogInfra.Log.e("NetWork", e.message)
        // 处理失败
        if (e is ConnectException || e is UnknownHostException) {
            ResUtil.getString(R.string.network_anomaly)?.let { ToastUtil.instance.toast(it) }
        } else {
            ToastUtil.instance.toast("服务器开小差了，请稍后再试")
        }
        disposable?.dispose()
    }

    override fun onComplete() {

    }

    abstract fun onResponse(t: T)
}