package com.zhuorui.securities.personal.event

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/23
 * Desc:
 */
class DisctCodeSelectEvent (str: String?,code:String?) {
     var str:String?=null
    var code:String?=null
    init {
        this.str=str
        this.code=code
    }
}