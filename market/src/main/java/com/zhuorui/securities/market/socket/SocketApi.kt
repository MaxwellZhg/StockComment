package com.zhuorui.securities.market.socket

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/22 15:20
 * desc   : 定义常量
 */
object SocketApi {

    /**
     * 定义socket中常量
     */
    const val SOCKET_URL = "ws://192.168.1.213:1949" // 服务器地址
    const val SOCKET_AUTH_SIGNATURE = "069de7990c0c4b8d87f516b7478e9f4a" // 链接认证签名

    const val AUTH = "auth.auth" // 链接认证
    const val TOPIC_BIND = "topic.bind" // 订阅
    const val TOPIC_UNBIND = "topic.unBind" // 取消订阅
    const val TOPIC_UNBIND_ALL = "topic.unBindAll" // 取消所有订阅
    const val PUSH_STOCK_DATA = "push.stock.data" //  推送股票数据

    const val PUSH_STOCK_INFO = "push.stock.info" //  推送股票行情
    const val PUSH_STOCK_KLINE = "push.stock.kline" // 推送股票K线
    const val PUSH_STOCK_KLINE_GET_FIVE_DAY = "kline.getFiveDay" // 获取五日K
    const val PUSH_STOCK_KLINE_GET_DAILY = "kline.getDaily" // 获取日K
    const val PUSH_STOCK_KLINE_GET_MINUTE = "kline.getMinute" // 获取分时
    const val PUSH_STOCK_KLINE_COMPENSATION_DATA = "push.stock.compensationData.kline" // 推送股票K线补偿数据
    const val PUSH_STOCK_TRANS = "push.stock.trans" // 推送股票盘口
    const val PUSH_STOCK_PRICE = "push.stock.price" // 推送股票价格

}
