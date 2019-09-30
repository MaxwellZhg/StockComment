package com.zhuorui.securities.market.socket.push

import com.zhuorui.securities.market.socket.request.SocketHeader
import com.zhuorui.securities.market.socket.vo.kline.PushKlineDataVo
import com.zhuorui.securities.market.socket.vo.kline.StockMinuteVo

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/23 15:49
 * desc   : websocket订阅自选股K线推送返回的信息
 */
class StocksTopicMinuteKlineResponse {
    var header: SocketHeader? = null
    var body: PushKlineDataVo<StockMinuteVo>? = null
}