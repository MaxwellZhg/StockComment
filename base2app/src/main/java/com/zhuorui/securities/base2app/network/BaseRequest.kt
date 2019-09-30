package com.zhuorui.securities.base2app.network

import com.alibaba.fastjson.JSON
import com.zhuorui.securities.base2app.BaseApplication
import com.zhuorui.securities.base2app.Transaction
import com.zhuorui.securities.base2app.infra.LogInfra
import com.zhuorui.securities.base2app.util.JsonUtil
import com.zhuorui.securities.base2app.util.SignUtil

/**
 * 请求的基类
 */
open class BaseRequest : Transaction {
    /**
     * 所有接口的公共参数，客户端发起请求时客户端的当前时间戳
     */
    var timeStamp = System.currentTimeMillis()

    /**
     * android，ios客户端发起的所有post请求均需要签名校验
     * 生成规则：将所有的字段进行升序排列，再使用私钥进行加密
     */
    var sign: String? = null

    /**
     * view 用来区分请求类型
     */
    var view: Int = 0

    /**
     * key 用来区分某次请求
     */
    var key = "none"

    constructor(transaction: String) : super(transaction)

    constructor(transaction: String, view: Int) : super(transaction) {
        this.view = view
    }

    constructor(transaction: String, key: String) : super(transaction) {
        this.key = key
    }

    constructor(transaction: String, view: Int, key: String) : super(transaction) {
        this.view = view
        this.key = key
    }

    /**
     * 根据参数生成签名
     */
    fun generateSign() {
        try {
            // 拿到所有的参数进行升序排列
            val json = JSON.toJSONString(this)
            LogInfra.Log.d("Retrofit", "待签名：" + json)
            // 根据新产生的json进行加密
            sign = SignUtil.createSHA1Sign(json, BaseApplication.baseApplication.config?.privateKey()!!)
            LogInfra.Log.d("Retrofit", "签名后：" + sign)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {

        fun instance(transaction: String): BaseRequest {
            return BaseRequest(transaction)
        }

        fun instance(transaction: String, view: Int): BaseRequest {
            return BaseRequest(transaction, view)
        }
    }
}
