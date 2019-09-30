package com.zhuorui.securities.base2app.rxbus

/**
 * Create by xieyingwu on 2018.7.3
 * 定义RxBus的注册和反注册
 */
interface IRxBusEvent {

    fun registerRxBus()

    fun unregisterRxBus()
}
