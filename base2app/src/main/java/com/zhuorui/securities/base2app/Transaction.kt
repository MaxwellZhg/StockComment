package com.zhuorui.securities.base2app

/**
 * 异步事务：Event、Req、Resp、Vo
 * Created by wuwenlong on 3/10/16.
 */
open class Transaction {
    var transaction: String? = null

    constructor() {}

    constructor(transaction: String) {
        this.transaction = transaction
    }
}
