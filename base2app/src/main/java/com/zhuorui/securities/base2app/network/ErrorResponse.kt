package com.zhuorui.securities.base2app.network

import android.text.TextUtils


/**
 * 统一请求错误处理
 */
class ErrorResponse(request: BaseRequest?, code: String?, message: String?,errordata:ErrorData?) : BaseResponse() {

    val isNetworkBroken: Boolean
        get() = this.code == NETWORK_BROKEN_STATUS

    init {
        this.request = request
//        this.status = status
        this.code = code
        this.msg = message
        this.errordata=errordata
        /*由于服务器返回的错误信息在code字段中，因此暂时赋值message为code字段值*/
        if (TextUtils.isEmpty(this.msg)) {
            this.msg = code
        }
    }
    companion object {

        private val NETWORK_BROKEN_STATUS = "1000"

        fun networkBroken(request: BaseRequest?): ErrorResponse {
//            return ErrorResponse(request, NETWORK_BROKEN_STATUS, null, null)
            return ErrorResponse(request, NETWORK_BROKEN_STATUS, null,null)
        }
    }
}
