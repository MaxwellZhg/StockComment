package com.zhuorui.securities.market.socket.response

import com.zhuorui.securities.market.socket.vo.kline.MinuteKlineData

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/23 15:49
 * desc   : websocket拉取自选股K线补偿数据
 */
class GetStocksMinuteKlineResponse : SocketResponse() {
    var data: List<MinuteKlineData>? = null
}