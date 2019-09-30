package com.zhuorui.securities.market.socket.response

import com.zhuorui.securities.market.socket.vo.kline.DayKlineData

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/23 15:49
 * desc   : 日K数据返回对象
 */
class StocksDayKlineResponse : SocketResponse() {

    var data: List<DayKlineData>? = null
}