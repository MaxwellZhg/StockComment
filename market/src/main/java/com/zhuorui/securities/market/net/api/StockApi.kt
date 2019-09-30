package com.zhuorui.securities.market.net.api

/**
 * 测试
 */
interface StockApi {
    companion object {
        /**
         * 自选股股票列表（含大盘指数）
         */
        const val LIST = "as_market/api/stock/selected/v1/view/list"

        /**
         * 同步自选股
         */
        const val SYN = "as_market/api/stock/selected/v1/view/syn"

        /**
         * 搜索股票
         */
        const val SEARCH = "as_market/api/stock/selected/v1/view/list"

        /**
         * 添加自选股
         */
        const val ADD = "as_market/api/stock/selected/v1/view/add"

        /**
         * 删除自选股
         */
        const val DEL = "as_market/api/stock/selected/v1/view/del"

        /**
         * 置顶自选股
         */
        const val TOP = "as_market/api/stock/selected/v1/view/top"

        /**
         * 行情（即K线实时数据）
         */
        const val MARKET = "stockmarket/api/stock/market/v1"

        /**
         * 关键字搜索
         */
        const val SEARCH_TOPIC ="as_market/api/stock/view/v1/search"

    }
}