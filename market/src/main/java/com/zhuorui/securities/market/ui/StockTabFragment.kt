package com.zhuorui.securities.market.ui

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.zhuorui.securities.base2app.rxbus.RxBus
import com.zhuorui.securities.base2app.ui.fragment.AbsBackFinishFragment
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.databinding.FragmentStockTabBinding
import com.zhuorui.securities.market.event.TabEvent
import com.zhuorui.securities.market.ui.presenter.StockTabPresenter
import com.zhuorui.securities.market.ui.view.StockTabView
import com.zhuorui.securities.market.ui.viewmodel.StockTabViewModel
import kotlinx.android.synthetic.main.fragment_stock_tab.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView
import java.util.*

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/18 10:32
 *    desc   : 主页中的自选股Tab页面
 */
class StockTabFragment :
    AbsBackFinishFragment<FragmentStockTabBinding, StockTabViewModel, StockTabView, StockTabPresenter>(),
    View.OnClickListener, StockTabView {

    private var mfragment: ArrayList<StockTabViewModel.PageInfo>? = null
    private var toggleStockTabShowing = false

    companion object {
        fun newInstance(): StockTabFragment {
            return StockTabFragment()
        }
    }

    override val layout: Int
        get() = R.layout.fragment_stock_tab

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: StockTabPresenter
        get() = StockTabPresenter()

    override val createViewModel: StockTabViewModel?
        get() = ViewModelProviders.of(this).get(StockTabViewModel::class.java)

    override val getView: StockTabView
        get() = this

    override fun rootViewFitsSystemWindowsPadding(): Boolean {
        return true
    }

    override fun init(fragments: ArrayList<StockTabViewModel.PageInfo>) {
        mfragment = fragments

        iv_serach.setOnClickListener(this)
        iv_list.setOnClickListener(this)
        tv_select_all.setOnClickListener(this)
        tv_select_hk.setOnClickListener(this)
        tv_select_hs.setOnClickListener(this)

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
              RxBus.getDefault().post(TabEvent(mfragment!![viewpager.currentItem].type))
                if (toggleStockTabShowing) {
                    presenter?.toggleStockTab()
                }
            }
        })

        // 指示器绑定viewpager
        magic_indicator.navigator = commonNavigator
        ViewPagerHelper.bind(magic_indicator, viewpager)
    }

    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): TopicStockListFragment {
            return TopicStockListFragment.newInstance(mfragment?.get(position)?.type)
        }

        override fun getCount(): Int {
            return mfragment?.size!!
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mfragment?.get(position)?.title
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.iv_serach -> {
                (parentFragment as AbsFragment<*, *, *, *>).start(TestTabFragment.newInstance())
            }
            R.id.iv_list -> {
                presenter?.toggleStockTab()
            }
            R.id.tv_select_all -> {
                presenter?.toggleStockTab()
                viewpager.currentItem = 0
            }
            R.id.tv_select_hk -> {
                presenter?.toggleStockTab()
                viewpager.currentItem = 1
            }
            R.id.tv_select_hs -> {
                presenter?.toggleStockTab()
                viewpager.currentItem = 2
            }
        }
    }

    override fun toggleStockTab(show: Boolean) {
        toggleStockTabShowing = show
        val values = IntArray(2)
        if (show) {
            values[0] = 0
            values[1] = ResUtil.getDimensionDp2Px(80f)

            when (viewpager.currentItem) {
                0 -> {
                    ResUtil.getColor(R.color.tab_select)?.let { tv_select_all.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_hk.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_hs.setTextColor(it) }
                }
                1 -> {
                    ResUtil.getColor(R.color.tab_select)?.let { tv_select_hk.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_all.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_hs.setTextColor(it) }
                }
                else -> {
                    ResUtil.getColor(R.color.tab_select)?.let { tv_select_hs.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_hk.setTextColor(it) }
                    ResUtil.getColor(R.color.un_tab_select)?.let { tv_select_all.setTextColor(it) }
                }
            }
        } else {
            values[0] = ResUtil.getDimensionDp2Px(80f)
            values[1] = 0
        }
        val valueAnimator = ValueAnimator.ofInt(values[0], values[1])
        valueAnimator.duration = 150
        valueAnimator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            val layoutParams = rl_filter.layoutParams
            layoutParams.height = value
            rl_filter?.layoutParams = layoutParams
        }
        valueAnimator.start()
    }
}