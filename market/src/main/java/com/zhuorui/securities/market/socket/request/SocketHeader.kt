package com.zhuorui.securities.market.socket.request

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/17 17:54
 * desc   :  定义 websocket 发送请求的Header
 */
class SocketHeader {
    var devId: String? = null
    var language: String? = null
    var reqId: String? = null
    var version: String? = null
    var path: String? = null
}
