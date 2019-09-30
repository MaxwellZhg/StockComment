package com.zhuorui.securities.market.model

/**
 * websocket返回的自选股行情信息
 */
class PushStockMarketData : BaseStockMarket() {

    var amount: Int = 0
    var avgPrice: Double? = null
    var closePrice: Double? = null
    var name: String? = null// 股票名称
    var date: String? = null
    var high: Double? = null
    var low: Double? = null
    var openPrice: Double? = null
    var price: Double? = null
    var time: String? = null
    var vol: Int = 0
    var dateTime: Long = 0
}