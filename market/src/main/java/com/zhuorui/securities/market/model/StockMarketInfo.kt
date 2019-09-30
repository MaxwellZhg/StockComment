package com.zhuorui.securities.market.model

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 14:08
 *    desc   : 自选股行情信息
 */
class StockMarketInfo : BaseStockMarket() {
    // 登录后拉取列表会返回id
    var id: String?= null
    // 名称
    var name: String? = null
    // 排序
    var sort: Int = 0
    // 股票代码 600004.SH
    var tsCode: String? = null
    // 当前价格：如13.75
    var price: Double = 0.0
    // 涨跌幅：如-0.0018（-0.18%）
    var diffRate: Double = 0.0
    // 创建时间
    var createTime: Long = 0
    // 长按
    var longClick :Boolean = false
}