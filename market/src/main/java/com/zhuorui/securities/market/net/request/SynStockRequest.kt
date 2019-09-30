package com.zhuorui.securities.market.net.request

import com.zhuorui.securities.base2app.network.BaseRequest
import com.zhuorui.securities.market.model.StockMarketInfo

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/23 16:19
 *    desc   : 同步自选股
 */
class SynStockRequest(val stockList: MutableList<StockMarketInfo>, transaction: String) : BaseRequest(transaction) {
    init {
        generateSign()
    }
}