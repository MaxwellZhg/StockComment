package com.zhuorui.securities.personal.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/21
 * Desc:
 */
class RestLoginPswRequest (val phone:String?,val newLoginPassword: String, val verificationCode: String?, transaction: String):BaseRequest(transaction){
    init {
        generateSign()
    }
}