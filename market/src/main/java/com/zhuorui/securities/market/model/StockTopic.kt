package com.zhuorui.securities.market.model

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/25 15:51
 * desc   : 订阅webSocket自选股信息
 */
open class StockTopic(
    dataType: StockTopicDataTypeEnum,
    val ts: String,// 属于的股票市场(SZ-深圳,SH-上海,HK-港股,US-美股)
    val code: String, // 股票代码
    val type: Int // 类型 1:指数,2:股票
) {
    var dataType = dataType.value
}