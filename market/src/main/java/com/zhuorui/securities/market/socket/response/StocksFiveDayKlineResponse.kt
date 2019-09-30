package com.zhuorui.securities.market.socket.response

import com.zhuorui.securities.market.socket.vo.kline.FiveDayKlineData

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/23 15:49
 * desc   : 五日K数据返回对象
 */
class StocksFiveDayKlineResponse : SocketResponse() {

    var data: List<FiveDayKlineData>? = null
}