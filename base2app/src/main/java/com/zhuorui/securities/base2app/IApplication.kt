package com.zhuorui.securities.base2app

/**
 * Create by xieyingwu on 2018/7/4
 * 定义Application的周期
 */
interface IApplication {
    fun create()

    fun close()

    fun app2Foreground()

    fun app2Background()

    fun onDevicesList()

    fun onLogout()
}
