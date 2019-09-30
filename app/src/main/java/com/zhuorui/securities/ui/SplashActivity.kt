package com.zhuorui.securities.ui

import android.os.Bundle
import com.zhuorui.securities.R
import com.zhuorui.securities.base2app.ui.activity.AbsActivity
import com.zhuorui.securities.base2app.util.CaughtRunnable
import com.zhuorui.securities.base2app.util.JumpUtil
import kotlinx.android.synthetic.main.activity_splash.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 13:55
 *    desc   : 开屏页
 */
class SplashActivity : AbsActivity() {

    override val layout: Int
        get() = R.layout.activity_splash

    override val acContentRootViewId: Int
        get() = R.id.root_layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        root_layout.postDelayed(object : CaughtRunnable() {
            override fun runSafe() {
                JumpUtil.jump(this@SplashActivity, MainActivity::class.java, true)
            }
        }, 500)
    }
}
