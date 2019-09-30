package com.zhuorui.securities.net.header

import com.zhuorui.securities.base2app.BaseApplication
import com.zhuorui.securities.base2app.util.AppUtil
import com.zhuorui.securities.base2app.util.DeviceUtil
import com.zhuorui.securities.personal.config.LocalAccountConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * author : Pengxianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019-05-20 14:13
 * desc   : 在网络请求拦截器中添加Header
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = LocalAccountConfig.read().getAccountInfo().token
        val lang = DeviceUtil.getSystemLanguage()
        val osType = "android"
        val appVersion = BaseApplication.context?.let { AppUtil.getVersionName(it) }
        val osVersion = DeviceUtil.getSystemVersion()
        val deviceId = DeviceUtil.getDeviceUuid()

        val builder = chain.request().newBuilder()
            .addHeader("token", if (token.isNullOrEmpty()) "" else token)
            .addHeader("lang", lang)
            .addHeader("osType", osType)
            .addHeader("osVersion", osVersion)
            .addHeader("appVersion", appVersion)
            .addHeader("deviceId", deviceId)

        /*加入自定义的header*/
        val request = builder.build()
        return chain.proceed(request)
    }
}