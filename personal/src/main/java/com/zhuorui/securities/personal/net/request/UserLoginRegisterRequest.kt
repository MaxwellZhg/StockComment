package com.zhuorui.securities.personal.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/21
 * Desc:
 */
class UserLoginRegisterRequest (val loginPassword:String,val verificationCode: String, val phone: String, val phoneArea:String, transaction: String): BaseRequest(transaction) {
    init {
        generateSign()
    }
}