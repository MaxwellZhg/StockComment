package com.zhuorui.securities.openaccount.manager

import com.zhuorui.securities.base2app.infra.AbsConfig
import com.zhuorui.securities.openaccount.constants.OpenAccountInfo
import com.zhuorui.securities.openaccount.net.response.OpenInfoResponse

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/29
 * Desc:
 */
open class OpenInfoManager {
    var info: OpenAccountInfo? = null

    companion object {
        private var instance: OpenInfoManager? = null

        fun getInstance(): OpenInfoManager? {//使用同步锁
            if (instance == null) {
                synchronized(OpenInfoManager::class.java) {
                    if (instance == null) {
                        instance = OpenInfoManager()
                    }
                }
            }
            return instance
        }

    }

    fun destroy() {
        instance = null
    }




}