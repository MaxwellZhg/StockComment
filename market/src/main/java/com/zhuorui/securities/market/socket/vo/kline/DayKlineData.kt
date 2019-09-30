package com.zhuorui.securities.market.socket.vo.kline

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/9 16:12
 *    desc   : 日K数据对象
 */
data class DayKlineData(
    val id: String,
    val amount: Double,// 成交额 （千元）
    val change: Double,// 涨跌额
    val closePrice: Double,// 收盘价格
    val high: Double,// 最高
    val low: Double,// 最低
    val openPrice: Double,// 开盘价格
    val preClosePrice: Double,// 昨收价
    val pctChg: Double,// 涨跌幅 （未复权）
    val tradeDate: Long,// 交易日期
    val tsCode: String,
    val vol: Double,// 成交量 （手）
    val ma5: Double// ma5数据
)