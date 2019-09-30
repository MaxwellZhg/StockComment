package com.zhuorui.securities.base2app.network

/**
 * 默认响应
 */
open class BaseResponse {

    var request: BaseRequest? = null
    var msg: String? = null
    // Response只返回code并不返回status,故而code即表示http的请求状态又表示接口业务是否成功
    var code: String? = null
    // 接口业务是否成功状态
    //  var status: Int = 0
    var errordata:ErrorData?=null
    fun isSuccess(): Boolean {
        return code.equals(RES_OK)
    }

    fun toError(): ErrorResponse {
        return ErrorResponse(request, code, msg,errordata)
    }

    override fun toString(): String {
//        return this.javaClass.simpleName + ": status = " + status + ", message = " + message + ", code = " + code
        return this.javaClass.simpleName + ": message = " + msg + ", code = " + code
    }

    companion object {
        val RES_OK = "000000"
    }
    data class ErrorData(
        val loginCount: Int
    )
}
