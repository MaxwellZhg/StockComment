package com.zhuorui.securities.openaccount.net.response

import com.zhuorui.securities.base2app.network.BaseResponse

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/28
 * Desc:
 */
class LiveRecognResponse(val data: Data) : BaseResponse() {
    data class Data(
        val id: String,
        val openStatus: Int,
        val video: String,
        val validateCode: String
    )
}