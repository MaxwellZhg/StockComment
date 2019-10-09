package com.zhuorui.securities.market.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.ui.fragment.AbsSwipeBackNetFragment
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.event.TestEvent
import com.zhuorui.securities.market.model.TestEnum
import com.zhuorui.securities.market.model.TestInfo
import com.zhuorui.securities.market.ui.presenter.TestTabPresenter
import com.zhuorui.securities.market.ui.view.TestTabView
import com.zhuorui.securities.market.ui.viewmodel.TestTabViewModel
import kotlinx.android.synthetic.main.fragment_test_tab.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

class TestTabFragment :AbsSwipeBackNetFragment<com.zhuorui.securities.market.databinding.FragmentTestTabBinding,TestTabViewModel,TestTabView,TestTabPresenter>(),TestTabView,TextWatcher{

    private var mfragment: ArrayList<TestInfo> = ArrayList()
    private var fragment :ArrayList<Fragment> = ArrayList()
    override val layout: Int
        get() = R.layout.fragment_test_tab
    override val viewModelId: Int
        get() = BR.viewModel
    override val createPresenter: TestTabPresenter
        get() = TestTabPresenter()
    override val createViewModel: TestTabViewModel?
        get() = ViewModelProviders.of(this).get(TestTabViewModel::class.java)
    override val getView: TestTabView
        get() =this
    override fun rootViewFitsSystemWindowsPadding(): Boolean {
        return true
    }


    companion object {
        fun newInstance(): TestTabFragment {

            return TestTabFragment()
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        et_serach.addTextChangedListener(this)
        mfragment.add(TestInfo(ResUtil.getString(R.string.all_stock)!!, TestEnum.ONE))
        mfragment.add(TestInfo(ResUtil.getString(R.string.hk_stock)!!, TestEnum.TWO))
        mfragment.add(TestInfo(ResUtil.getString(R.string.sh_stock)!!, TestEnum.THREE))
        fragment.add(TabOneFragment.newInstance(TestEnum.ONE))
        fragment.add(TabOneFragment.newInstance(TestEnum.TWO))
        fragment.add(TabOneFragment.newInstance(TestEnum.THREE))
        // 设置viewpager指示器
        val commonNavigator = CommonNavigator(requireContext())
        commonNavigator.adapter = object : CommonNavigatorAdapter() {

            override fun getCount(): Int {
                return mfragment!!.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val colorTransitionPagerTitleView = ColorTransitionPagerTitleView(context)
                colorTransitionPagerTitleView.normalColor = ResUtil.getColor(R.color.un_tab_select)!!
                colorTransitionPagerTitleView.selectedColor = ResUtil.getColor(R.color.tab_select)!!
                colorTransitionPagerTitleView.text = mfragment!![index].title
                colorTransitionPagerTitleView.textSize = 18f
                colorTransitionPagerTitleView.setOnClickListener {
                    viewpager.currentItem = index
                }
                return colorTransitionPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                indicator.setColors(ResUtil.getColor(R.color.tab_select))
                indicator.lineHeight = ResUtil.getDimensionDp2Px(2f).toFloat()
                indicator.lineWidth = ResUtil.getDimensionDp2Px(33f).toFloat()
                return indicator
            }
        }

        // 设置viewpager页面缓存数量
        viewpager.offscreenPageLimit = mfragment!!.size
        // 设置viewpager适配器
        viewpager.adapter = fragmentManager?.let { ViewPagerAdapter(it) }
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                magic_indicator.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                magic_indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                magic_indicator.onPageSelected(position)
                RxBus.getDefault().post(TestEvent(et_serach.text.toString()+"CLICK",mfragment[viewpager.currentItem].type))
            }
        })

        // 指示器绑定viewpager
        magic_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(magic_indicator, viewpager)
    }
    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): TabOneFragment {
            return TabOneFragment.newInstance(mfragment[position].type)
        }

        override fun getCount(): Int {
            return mfragment?.size!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mfragment?.get(position)?.title
        }
    }
    override fun afterTextChanged(p0: Editable?) {
        if (p0.toString().isNotEmpty()) {
              magic_indicator.visibility=View.VISIBLE
              viewpager.visibility=View.VISIBLE
             RxBus.getDefault().post(TestEvent(p0.toString(),mfragment[viewpager.currentItem].type))
        }else{
            magic_indicator.visibility=View.INVISIBLE
            viewpager.visibility=View.INVISIBLE
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }
}