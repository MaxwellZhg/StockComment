//package com.dycm.base2app.network
//
//import android.text.TextUtils
//import com.dycm.base2app.BaseApplication
//import okhttp3.Interceptor
//import okhttp3.Response
//import java.io.IOException
//
///**
// * author : Pengxianglin
// * e-mail : peng_xianglin@163.com
// * date   : 2019-05-2111:44
// * desc   : Retrofit动态切换BaseUrl的拦截器
// */
//class BaseUrlInterceptor : Interceptor {
//
//    @Throws(IOException::class)
//    override fun intercept(chain: Interceptor.Chain): Response {
//Response
//        // 取出当前请求的url
//        val oldRequest = chain.request()
//        val requestUrl = oldRequest.url()
//        val oldUrl = requestUrl.toString()
//
//        // Retrofit初始化时候的BaseUrl
//        var originalBaseUrl: String? = null
//        // Retrofit需要更改的BaseUrl
//        var newBaseUrl: String? = null
//        // 当前接口
//        var api: String? = null
//
//        // 取出域名配置
//        val runConfig = BaseApplication.baseApplication.config?.runConfig
//        if (runConfig != null) {
//            val userApi = runConfig.user_api
//            val iMApi = runConfig.im_api
//            val payApi = runConfig.pay_api
//            val trendApi = runConfig.trend_api
//
//            // 判断当前的域名
//            if (oldUrl.startsWith(userApi)) {
//                originalBaseUrl = userApi
//            } else if (oldUrl.startsWith(iMApi)) {
//                originalBaseUrl = iMApi
//            } else if (oldUrl.startsWith(payApi)) {
//                originalBaseUrl = payApi
//            } else if (oldUrl.startsWith(trendApi)) {
//                originalBaseUrl = trendApi
//            }
//
//            // 根据接口，判断新域名
//            api = oldUrl.substring(originalBaseUrl!!.length, oldUrl.length)
//            if (api.startsWith("/user")) {
//                newBaseUrl = userApi
//            } else if (api.startsWith("/v2")) {
//                newBaseUrl = iMApi
//            } else if (api.startsWith("")) {
//                newBaseUrl = payApi
//            } else if (api.startsWith("")) {
//                newBaseUrl = trendApi
//            }
//        }
//
//        if (TextUtils.isEmpty(originalBaseUrl)
//            || TextUtils.isEmpty(newBaseUrl)
//            || TextUtils.equals(originalBaseUrl, newBaseUrl)
//            || !oldUrl.startsWith(originalBaseUrl!!)
//        ) {
//            return chain.proceed(chain.request())
//        }
//
//        // 根据接口改变Baseurl
//        val newUrl = newBaseUrl!! + api
//        val newRequest = oldRequest
//            .newBuilder()
//            .url(newUrl)
//            .build()
//        return chain.proceed(newRequest)
//    }
//}