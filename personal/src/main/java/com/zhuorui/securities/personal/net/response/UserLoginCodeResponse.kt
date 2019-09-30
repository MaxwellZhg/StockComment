package com.zhuorui.securities.personal.net.response

import com.zhuorui.securities.base2app.network.BaseResponse

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/16
 * Desc:
 */
class UserLoginCodeResponse(val data:Data) : BaseResponse(){
    data class Data(
        val userId: String,
        val token :String,
        val phone:String,
        val loginCount: Int
    )


}