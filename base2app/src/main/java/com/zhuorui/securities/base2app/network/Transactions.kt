package com.zhuorui.securities.base2app.network

import android.text.TextUtils

import com.zhuorui.securities.base2app.infra.LogInfra

import java.util.ArrayList

/**
 * 请求事件筛选器
 */
class Transactions {
    private val transactionList: MutableList<String>

    init {
        transactionList = ArrayList()
    }

    /***
     * 发送event之前，创建一个标识符
     */
    @Synchronized
    fun createTransaction(): String {
        val transaction = System.currentTimeMillis().toString()
        transactionList.add(transaction)
        return transaction
    }

    @Synchronized
    fun isMyTransaction(vo: BaseResponse?): Boolean {
        if (vo == null) return true
        val transaction = vo.request!!.transaction
        val flag = transaction == null || transaction == "" || transactionList.remove(transaction)
        if (flag) {
            LogInfra.Log.i(TAG, vo)
        }
        return flag
    }

    @Synchronized
    fun isMyTransactionWithoutLog(vo: BaseResponse?): Boolean {
        if (vo == null) return false
        val transaction = vo.request!!.transaction
        return !TextUtils.isEmpty(transaction) && transactionList.remove(transaction)
    }

    @Synchronized
    fun isMyTransaction(transaction: String): Boolean {
        return TextUtils.isEmpty(transaction) || transactionList.remove(transaction)
    }

    /**
     * 清空transaction
     */
    @Synchronized
    fun clear() {
        transactionList.clear()
    }

    companion object {
        private val TAG = Transactions::class.java.simpleName
    }
}