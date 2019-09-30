package com.zhuorui.securities.openaccount.net.response

import com.zhuorui.securities.base2app.network.BaseResponse

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/28
 * Desc:
 */
class LiveCodeResponse (val data:Data):BaseResponse(){
    data class Data(
        val validateCode:String
    )
}