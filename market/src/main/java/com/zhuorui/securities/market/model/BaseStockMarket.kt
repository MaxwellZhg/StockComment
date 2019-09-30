package com.zhuorui.securities.market.model

/**
 * 股票基础信息VO
 */
open class BaseStockMarket {
    /**
     * 属于的股票市场(SZ-深圳,SH-上海,HK-港股,US-美股)
     */
    var ts: String? = null
    /**
     * 股票代码
     */
    var code: String? = null
    /**
     * 类型 1:指数,2:股票
     */
    var type: Int? = null
}