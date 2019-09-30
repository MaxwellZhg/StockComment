package com.zhuorui.securities.market.socket.push

import com.zhuorui.securities.market.model.PushStockPriceData
import com.zhuorui.securities.market.socket.request.SocketHeader

/**
 * author : PengXianglin
 * e-mail : peng_xianglin@163.com
 * date   : 2019/7/23 15:49
 * desc   : websocket订阅自选股行情推送返回的信息
 */
class StocksTopicPriceResponse(
    val header: SocketHeader,
    val body: List<PushStockPriceData>
)