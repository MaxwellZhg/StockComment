package com.zhuorui.securities.market.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * 装载Fragment的通用适配器
 */
class KlinePagerAdapter(fm: FragmentManager, titles: Array<String>) : FragmentStatePagerAdapter(fm) {
    private var mTitles: List<String>? = null

    init {
        mTitles = listOf(*titles)
    }


    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> {
                return ChartOneDayFragment.newInstance(true)
            }
            1 -> {
                return ChartFiveDayFragment.newInstance(true)
            }
            2 -> {
                return ChartKLineFragment.newInstance(1, true)
            }
            3 -> {
                return ChartKLineFragment.newInstance(7, true)
            }
            4 -> {
                return ChartKLineFragment.newInstance(30, true)
            }
            5 -> {
                return ChartKLineFragment.newInstance(90, true)
            }
            6 -> {
                return ChartKLineFragment.newInstance(365, true)
            }
        }
        return null
    }

    override fun getCount(): Int {
        return mTitles!!.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (mTitles != null && mTitles!!.isNotEmpty()) {
            mTitles!![position]
        } else super.getPageTitle(position)
    }

}
