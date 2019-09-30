package com.zhuorui.securities

import com.zhuorui.securities.base2app.BaseApplication
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.net.header.HeaderInterceptor
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Interceptor

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-05-20 14:13
 *    desc   :
 */
class Application : BaseApplication() {

    override val logTag: String
        get() = this.javaClass.simpleName

    override val defaultSpName: String
        get() = packageName

    override val header: Interceptor
        get() = HeaderInterceptor()

    override fun beforeInit() {
        // TODO 在App需要初始化的东西写在这里
        // 初始化RxBus
        RxBus.setMainScheduler(AndroidSchedulers.mainThread())
    }
}