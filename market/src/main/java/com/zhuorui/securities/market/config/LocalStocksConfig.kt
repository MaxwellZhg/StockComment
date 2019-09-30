package com.zhuorui.securities.market.config

import com.zhuorui.securities.base2app.infra.AbsConfig
import com.zhuorui.securities.base2app.infra.StorageInfra
import com.zhuorui.securities.market.model.StockMarketInfo

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/30 19:04
 *    desc   : 本地添加的自选股纪录
 */
class LocalStocksConfig : AbsConfig() {

    private var stocks: MutableList<StockMarketInfo> = ArrayList()

    fun getStocks(): MutableList<StockMarketInfo> {
        return ArrayList(stocks)
    }

    /**
     * 检查自选股列表中是否存在
     */
    private fun getStock(ts: String, code: String): StockMarketInfo? {
        for (stock in stocks) {
            if (stock.code.equals(code) && stock.ts.equals(ts)) {
                return stock
            }
        }
        return null
    }

    /**
     * 检查自选股列表中是否存在
     */
    fun isExist(ts: String, code: String): Boolean {
        return getStock(ts, code) != null
    }

    /**
     * 添加自选股
     */
    fun replaceAll(list: MutableList<StockMarketInfo>): Boolean {
        if (list.isNullOrEmpty()) {
            return false
        }
        stocks.clear()
        stocks.addAll(list)
        write()
        return true
    }

    /**
     * 添加自选股
     */
    fun add(stockInfo: StockMarketInfo): Boolean {
        if (isExist(stockInfo.ts!!, stockInfo.tsCode!!)) {
            return false
        }
        stocks.add(stockInfo)
        write()
        return true
    }

    /**
     * 删除自选股
     */
    fun remove(code: String, ts: String): Boolean {
        val stock = getStock(ts, code)
        if (stock != null) {
            stocks.remove(stock)
            write()
            return true
        }
        return false
    }

    override fun write() {
        StorageInfra.put(LocalStocksConfig::class.java.simpleName, this)
    }

    companion object {
        fun read(): LocalStocksConfig {
            var config: LocalStocksConfig? =
                StorageInfra.get(LocalStocksConfig::class.java.simpleName, LocalStocksConfig::class.java)
            if (config == null) {
                config = LocalStocksConfig()
                config.write()
            }
            return config
        }
    }
}