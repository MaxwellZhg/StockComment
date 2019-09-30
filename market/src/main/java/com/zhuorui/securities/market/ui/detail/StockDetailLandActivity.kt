package com.zhuorui.securities.market.ui.detail

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.zhuorui.securities.market.R
import com.zhuorui.securities.base2app.ui.activity.AbsActivity
import kotlinx.android.synthetic.main.fragment_stockdetail.*

/**
 * 股票详情页-横屏
 */
class StockDetailLandActivity : AbsActivity() {

    override val layout: Int
        get() = R.layout.fragment_stockdetail

    override val acContentRootViewId: Int
        get() = R.id.root_view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 全屏
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        // 设置夜间模式
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val titles = arrayOf("分时", "五日", "日K", "周K", "月K", "季K", "年K")
        view_pager!!.offscreenPageLimit = titles.size
        view_pager!!.adapter = KlinePagerAdapter(supportFragmentManager, titles)
        tab!!.setupWithViewPager(view_pager)
    }
}