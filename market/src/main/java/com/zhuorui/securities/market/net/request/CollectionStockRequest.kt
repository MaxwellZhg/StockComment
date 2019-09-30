package com.zhuorui.securities.market.net.request

import com.zhuorui.securities.base2app.network.BaseRequest

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/23 10:32
 *    desc   : 添加自选股
 */
class CollectionStockRequest(
    val custom: Any,
    val type: Int,
    val ts: String,
    val code: String,
    val sort: Int,
    transaction: String
) :
    BaseRequest(transaction) {

    init {
        generateSign()
    }
//
//    type	integer
//    必须
//    类型,1:大盘指数，2:股票
//    ts	string
//    必须
//    SZ-深圳，SH-上海，HK-港股，US-美股
//    code	string
//    必须
//    股票编码
//    sort	integer
//    必须
//    排序号
}