package com.zhuorui.securities.base2app.network

import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.util.JsonUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 *    author : Pengxianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019-05-20 14:13
 *    desc   : 定义Retrofit网络配置
 */
object Network {
    val TAG = "Retrofit"
    /**
     * 返回一个普通的Retrofit
     * @return Retrofit
     */
    var retrofit: Retrofit? = null
        private set
    /**
     * 返回一个请求不超时的Retrofit，适用于上传
     * @return Retrofit
     */
    var noTimeoutRetrofit: Retrofit? = null
        private set

    private/*域名验证器*/ val unsafeOkHttpClient: OkHttpClient
        get() {
            try {
                val builder = OkHttpClient().newBuilder()
                val allHostnameVerifier = HostnameVerifier { hostname, session -> true }
                builder.hostnameVerifier(allHostnameVerifier)
                return builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }

    /**
     * 初始化Retrofit
     *
     * @param baseUrl             主域名
     * @param debug               是否处于debug模式
     * @param writeTimeout_secs   writeTimeout
     * @param readTimeout_secs    readTimeout
     * @param connectTimeout_secs connectTimeout
     */
    fun initRetrofit(
        baseUrl: String?, debug: Boolean?, writeTimeout_secs: Long?, readTimeout_secs: Long?,
        connectTimeout_secs: Long?, header: Interceptor?
    ) {
        /*配置OkHttp属性*/
        val builder = unsafeOkHttpClient.newBuilder()
            .writeTimeout(writeTimeout_secs!!, TimeUnit.SECONDS)
            .readTimeout(readTimeout_secs!!, TimeUnit.SECONDS)
            .connectTimeout(connectTimeout_secs!!, TimeUnit.SECONDS)
            .followRedirects(true)
        if (debug!!) {
            /*若是debug；则添加Http日志打印的拦截器进行打印请求信息*/
            val logger = HttpLoggingInterceptor.Logger { message -> LogInfra.Log.w(TAG, message) }
            val interceptorLog = HttpLoggingInterceptor(logger)
            interceptorLog.level = HttpLoggingInterceptor.Level.BASIC/*请求日志打印信息；基本信息*/
            builder.addInterceptor(interceptorLog)
        }
        /*添加Header头信息*/
        builder.addInterceptor(header)
        /*添加动态修改BaseUrl*/
//        builder.addInterceptor(BaseUrlInterceptor())
        val client = builder.build()
        /*设置同一个端口最大请求并发数*/
        client.dispatcher().maxRequestsPerHost = 4
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            // 添加GSON解析：返回数据转换成GSON类型
            .addConverterFactory(GsonConverterFactory.create())
            // 添加Rxjava支持
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        // 设置超时为120秒
        builder.writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS)
        val clientNoTimeout = builder.build()
        clientNoTimeout.dispatcher().maxRequestsPerHost = 4// 设置同一个端口，最大请求并发数
        noTimeoutRetrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientNoTimeout)
            .build()
    }

    class IHCallBack<T : BaseResponse>(private val request: BaseRequest) : Callback<T> {

        init {
            LogInfra.Log.w(TAG, " Request: " + JsonUtil.toJson(request))
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            if (!response.isSuccessful) {
                LogInfra.Log.e(TAG, " Response Failure $code")
                RxBus.getDefault().post(ErrorResponse(request, code.toString(), "服务器开小差了，请稍后再试",null))
                return
            }
            val t = response.body()
            if (t == null) {
                LogInfra.Log.e(TAG, "Response Failure Body is empty")
                RxBus.getDefault().post(ErrorResponse(request, code.toString(), "服务器开小差了，请稍后再试",null))
                return
            }
            t.request = request
            LogInfra.Log.w(TAG, "Response: " + JsonUtil.toJson(t))
            if (!t.isSuccess()) {
                RxBus.getDefault().post(t.toError())
                return
            }
            RxBus.getDefault().post(t)
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            LogInfra.Log.e(TAG, " Response Failure onFailure()", t)
            RxBus.getDefault().post(ErrorResponse.networkBroken(request))
        }
    }
}
