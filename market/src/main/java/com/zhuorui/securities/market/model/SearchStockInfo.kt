package com.zhuorui.securities.market.model

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/25 18:36
 *    desc   : 搜索返回的自选股信息
 */
class SearchStockInfo : BaseStockMarket() {
    var id: String? = null
    var tsCode: String? = null
    var name: String? = null
}