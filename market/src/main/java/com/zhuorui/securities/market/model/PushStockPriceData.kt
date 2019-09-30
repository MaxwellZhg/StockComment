package com.zhuorui.securities.market.model

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/6 11:18
 *    desc   : 推送股价数据
 */
class PushStockPriceData  : BaseStockMarket() {
    /**
     * 开盘价格
     */
    var openPrice: Double? = null
    /**
     * 昨日收盘价格
     */
    var closePrice: Double? = null
    /**
     * 当前价格
     */
    var price: Double? = null
    /**
     * 日期时间
     */
    var dateTime: Long? = null
}