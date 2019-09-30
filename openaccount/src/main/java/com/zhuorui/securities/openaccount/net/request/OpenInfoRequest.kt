package com.zhuorui.securities.openaccount.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/29
 * Desc:
 */
class OpenInfoRequest (transaction: String):BaseRequest(transaction){
    init {
        generateSign()
    }
}