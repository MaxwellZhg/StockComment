package com.zhuorui.securities.base2app.infra

/**
 * Created by xieyingwu on 2018/3/24.
 * 全面屏配置信息
 */

class FullDisplayConfig : AbsConfig() {
    var isFullDisplay: Boolean = false
    var navigationHeight: Int = 0

    override fun initDefaultAttrs() {
        isFullDisplay = false
    }

    fun updateFullDisplay(navigationHeight: Int) {
        this.isFullDisplay = navigationHeight > 0
        this.navigationHeight = navigationHeight
        write()
    }

    companion object {

        fun read(): FullDisplayConfig {
            var fullDisplayConfig: FullDisplayConfig? = StorageInfra.get(FullDisplayConfig::class.java)
            if (fullDisplayConfig == null) {
                fullDisplayConfig = FullDisplayConfig()
                fullDisplayConfig.write()
            }
            return fullDisplayConfig
        }
    }
}
