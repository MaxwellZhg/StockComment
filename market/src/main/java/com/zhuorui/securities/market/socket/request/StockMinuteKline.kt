package com.zhuorui.securities.market.socket.request

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/6 16:31
 *    desc   : 拉取webSocket自选股K线分时数据
 */
class StockMinuteKline(val ts: String, val code: String, val type: Int)