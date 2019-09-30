package com.zhuorui.securities.market.net.response

import com.zhuorui.securities.market.model.StockMarketInfo
import com.zhuorui.securities.base2app.network.BaseResponse

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 13:55
 *    desc   : 获取自选股列表推荐数据
 */
class RecommendStocklistResponse : BaseResponse() {
    var data: Data? = null

    data class Data(
        val currentPage: Int,
        val datas: MutableList<StockMarketInfo>,
        val pageSize: Int,
        val totalPage: Int,
        val totalRecord: Int
    )
}