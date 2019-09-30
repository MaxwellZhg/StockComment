package com.zhuorui.securities.market.socket.vo.kline

import com.zhuorui.securities.market.model.BaseStockMarket

/**
 * 分时数据
 */
class StockMinuteVo : BaseStockMarket() {

    var data: List<MinuteKlineData>? = null

}
