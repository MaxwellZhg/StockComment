package com.zhuorui.securities.market.model

import java.math.BigDecimal

/**
 * 盘口数据
 */
class PushStockTransData : BaseStockMarket() {

    /**
     * 卖1价
     */
    var ask1: BigDecimal? = null
    
    /**
     * 卖1量
     */
    var ask1Volume: BigDecimal? = null

    /**
     * 卖2价
     */
    var ask2: BigDecimal? = null
    
    /**
     * 卖2量
     */
    var ask2Volume: BigDecimal? = null

    /**
     * 卖3价
     */
    var ask3: BigDecimal? = null
    
    /**
     * 卖3量
     */
    var ask3Volume: BigDecimal? = null

    /**
     * 卖4价
     */
    var ask4: BigDecimal? = null
    /**
     * 卖4量
     */
    var ask4Volume: BigDecimal? = null

    /**
     * 卖5价
     */
    var ask5: BigDecimal? = null
    
    /**
     * 卖5量
     */
    var ask5Volume: BigDecimal? = null
    
    /**
     * 买1价
     */
    var bid1: BigDecimal? = null
    
    /**
     * 买1量
     */
    var bid1Volume: BigDecimal? = null

    /**
     * 买2价
     */
    var bid2: BigDecimal? = null
    
    /**
     * 买2量
     */
    var bid2Volume: BigDecimal? = null

    /**
     * 买3价
     */
    var bid3: BigDecimal? = null
    
    /**
     * 买3量
     */
    var bid3Volume: BigDecimal? = null

    /**
     * 买4价
     */
    var bid4: BigDecimal? = null
    /**
     * 买4量
     */
    var bid4Volume: BigDecimal? = null

    /**
     * 买5价
     */
    var bid5: BigDecimal? = null
    
    /**
     * 买5量
     */
    var bid5Volume: BigDecimal? = null

    /**
     * 日期时间
     */
    var dateTime: Long? = null
}
