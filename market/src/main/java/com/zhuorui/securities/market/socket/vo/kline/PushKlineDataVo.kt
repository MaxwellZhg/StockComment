package com.zhuorui.securities.market.socket.vo.kline

/**
 * K线推送数据
 */
class PushKlineDataVo<T> {

    /**
     * 数据类型 2-1:K线-分时,2-2:K线-五日,2-3:K线-日K,2-4:K线-周K,2-5:K线-月K,2-6:K线-季K,2-7:K线-年K
     */
    var klineType: String? = null

    var klineData: T? = null

}
