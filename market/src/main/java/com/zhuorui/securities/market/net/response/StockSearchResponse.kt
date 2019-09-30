package com.zhuorui.securities.market.net.response

import com.zhuorui.securities.market.model.SearchStockInfo
import com.zhuorui.securities.base2app.network.BaseResponse

class StockSearchResponse(val data: Data) : BaseResponse() {
    data class Data(
        val currentPage: Int,
        val datas: List<SearchStockInfo>,
        val pageSize: Int,
        val totalPage: Int,
        val totalRecord: Int
    )
}