package com.zhuorui.securities.openaccount.net.response

import com.zhuorui.securities.base2app.network.BaseResponse

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/29 18:06
 *    desc   : 上传电子签名
 */
class SubSignatureResponse(val data: Data) : BaseResponse() {

    data class Data(
        val id: Int, // 开户id
        val openStatus: Int, // 开户状态
        val signaturePhoto: String // 签名照片url
    )
}