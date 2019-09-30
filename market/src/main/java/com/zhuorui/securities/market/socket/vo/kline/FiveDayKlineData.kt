package com.zhuorui.securities.market.socket.vo.kline

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/9 16:12
 *    desc   : 五日K数据对象
 */
data class FiveDayKlineData(
    val amount: Double, // 交易额
    val avgPrice: Double,// 均价
    val dateTime: Long,// 交易时间
    val openPrice: Double,// 开盘加
    val price: Double, // 当前价
    val vol: Int // 交易量
)