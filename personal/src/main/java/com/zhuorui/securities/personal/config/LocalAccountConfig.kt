package com.zhuorui.securities.personal.config

import com.zhuorui.securities.base2app.infra.AbsConfig
import com.zhuorui.securities.base2app.infra.StorageInfra
import com.zhuorui.securities.personal.model.AccountInfo

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/20
 * Desc:保存账号信息
 */
class LocalAccountConfig : AbsConfig() {

    private var accountInfo: AccountInfo = AccountInfo()

    fun getAccountInfo(): AccountInfo {
        return accountInfo
    }

    /**
     * 是否登录
     */
    fun isLogin():Boolean{
        return accountInfo.token?.isNotEmpty()?:false
    }

    /**
     * 更新登录信息
     */
    fun saveLogin(userId: String, phone: String, token: String): Boolean {
        accountInfo.userId = userId
        accountInfo.token = token
        accountInfo.phone = phone
        write()
        return true
    }

    /**
     * 更新用户信息
     */
    fun savaUserInfo() {

        write()
    }

    override fun write() {
        StorageInfra.put(LocalAccountConfig::class.java.simpleName, this)
    }

    companion object {
        fun read(): LocalAccountConfig {
            var config: LocalAccountConfig? =
                StorageInfra.get(LocalAccountConfig::class.java.simpleName, LocalAccountConfig::class.java)
            if (config == null) {
                config = LocalAccountConfig()
                config.write()
            }
            return config
        }
    }
}