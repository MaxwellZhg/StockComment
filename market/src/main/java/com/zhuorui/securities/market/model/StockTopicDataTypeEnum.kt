package com.zhuorui.securities.market.model

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/31 17:47
 *    desc   : 订阅股票的数据类型
 *    （1:行情）,（2-1:K线-分时,2-2:K线-五日,2-3:K线-日K,2-4:K线-周K,2-5:K线-月K,2-6:K线-季K,2-7:K线-年K）,（3:盘口）,（4:股价）
 */
enum class StockTopicDataTypeEnum(var value: String) {
    market("1"),
    kminute("2-1"), k5day("2-2"), kday("2-3"), kweek("2-4"), kmonth("2-5"), k3month("2-6"), kyear("2-7"),
    trans("3"),
    price("4")
}