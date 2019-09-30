package com.zhuorui.securities.market.ui

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.zhuorui.securities.base2app.adapter.BaseListAdapter
import com.zhuorui.securities.base2app.ui.fragment.AbsFragment
import com.zhuorui.securities.personal.config.LocalAccountConfig
import com.zhuorui.securities.personal.ui.LoginRegisterFragment
import com.zhuorui.securities.market.BR
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.custom.StockPopupWindow
import com.zhuorui.securities.market.databinding.FragmentAllChooseStockBinding
import com.zhuorui.securities.market.model.StockMarketInfo
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.market.ui.detail.StockDetailLandActivity
import com.zhuorui.securities.market.ui.presenter.TopicStockListPresenter
import com.zhuorui.securities.market.ui.view.TopicStockListView
import com.zhuorui.securities.market.ui.viewmodel.TopicStockListViewModel
import kotlinx.android.synthetic.main.fragment_all_choose_stock.*
import kotlinx.android.synthetic.main.layout_guide_open_accout.*

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/7
 * Desc: 自选股列表界面
 */
class TopicStockListFragment :
    AbsFragment<FragmentAllChooseStockBinding, TopicStockListViewModel, TopicStockListView, TopicStockListPresenter>(),
    BaseListAdapter.OnClickItemCallback<StockMarketInfo>, View.OnClickListener,
    TopicStockListView, BaseListAdapter.onLongClickItemCallback<StockMarketInfo> {

    private var mAdapter: TopicStocksAdapter? = null
    private var currentPage = 0
    private var pageSize = 20

    companion object {
        fun newInstance(type: StockTsEnum?): TopicStockListFragment {
            val fragment = TopicStockListFragment()
            if (type != null) {
                val bundle = Bundle()
                bundle.putSerializable("type", type)
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override val layout: Int
        get() = R.layout.fragment_all_choose_stock

    override val viewModelId: Int
        get() = BR.viewModel

    override val createPresenter: TopicStockListPresenter
        get() = TopicStockListPresenter()

    override val createViewModel: TopicStockListViewModel?
        get() = ViewModelProviders.of(this).get(TopicStockListViewModel::class.java)

    override val getView: TopicStockListView
        get() = this

    override fun init() {
        val type = arguments?.getSerializable("type") as StockTsEnum?
        if (type == null && !LocalAccountConfig.read().isLogin()) {
            guide_open_accout.inflate()
            tv_opne_account.setOnClickListener(this)
        }
        if (type == StockTsEnum.HS) {
            root_view.addView(View.inflate(context, R.layout.layout_trans_index, null), 0)
        }
        presenter?.setType(type)
        presenter?.setLifecycleOwner(this)

        // 设置列表数据适配器
        (rv_stock.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        rv_stock.layoutManager = LinearLayoutManager(context)
        mAdapter = TopicStocksAdapter()

        mAdapter?.setClickItemCallback(this)
        mAdapter?.setLongClickItemCallback(this)
        rv_stock.adapter = mAdapter

        requestStocks()
    }

    override fun onClickItem(pos: Int, item: StockMarketInfo?, v: View?) {
        if (item != null) {
            // 跳转到详情页
            startActivity(Intent(context, StockDetailLandActivity::class.java))
        } else {
            // 跳转到搜索
            (parentFragment as AbsFragment<*, *, *, *>).start(StockSearchFragment.newInstance(1))
        }
    }

    override fun onLongClickItem(pos: Int, item: StockMarketInfo?, view: View?) {
        item?.longClick = true
        mAdapter?.notifyItemChanged(pos)

        //获取需要在其上方显示的控件的位置信息
        val location = IntArray(2)
        view?.getLocationOnScreen(location)
        // 显示更多操作
        context?.let {
            StockPopupWindow.create(it, object : StockPopupWindow.CallBack {
                override fun onStickyOnTop() {
                    presenter?.onStickyOnTop(item)
                }

                override fun onRemind() {
                    (parentFragment as AbsFragment<*, *, *, *>).start(RemindSettingFragment.newInstance())
                }

                override fun onDelete() {
                    presenter?.onDelete(item)
                }

                override fun onDismiss() {
                    item?.longClick = false
                    mAdapter?.notifyItemChanged(pos)
                }
            }).showAtLocation(view, Gravity.TOP, location[0], location[1])
        }
    }

    override fun notifyDataSetChanged(list: List<StockMarketInfo>?) {
        if (mAdapter?.items == null) {
            mAdapter?.items = list
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun notifyItemChanged(index: Int) {
        _mActivity?.runOnUiThread { mAdapter?.notifyItemChanged(index) }
    }

    /**
     * 加载推荐自选股列表
     */
    override fun requestStocks() {
        presenter?.requestStocks(currentPage, pageSize)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_opne_account -> {
                //TODO 开户
                (parentFragment as AbsFragment<*, *, *, *>).start(LoginRegisterFragment.newInstance())
            }
        }
    }
}