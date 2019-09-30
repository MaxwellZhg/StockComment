package com.zhuorui.securities.ui

import android.os.Bundle
import com.zhuorui.securities.R
import com.zhuorui.securities.base2app.ui.activity.AbsActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/8/8 13:55
 *    desc   : 主界面
 */
class MainActivity : AbsActivity() {

    override val acContentRootViewId: Int
        get() = R.id.root_view

    override val layout: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (findFragment(MainFragment::class.java) == null) {
            loadRootFragment(R.id.root_view, MainFragment())
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置Fragment切换时默认动画为横向滑动，类似微信
        return DefaultHorizontalAnimator()
    }

    override fun statusBarLightMode(): Boolean {
        return false
    }
}
