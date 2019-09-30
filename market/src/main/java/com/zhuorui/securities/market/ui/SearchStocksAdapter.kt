package com.zhuorui.securities.market.ui

import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.R2
import com.zhuorui.securities.market.model.SearchStockInfo
import com.zhuorui.securities.base2app.adapter.BaseListAdapter
import com.zhuorui.securities.base2app.util.ResUtil

/**
 *    author : PengXianglin
 *    e-mail : peng_xianglin@163.com
 *    date   : 2019/7/26 14:12
 *    desc   : 搜索股票结果列表适配器
 */
class SearchStocksAdapter : BaseListAdapter<SearchStockInfo>() {
    private val default = 0x00
    private val bottom = 0x01
    var onAddTopicClickItemCallback: OnAddTopicClickItemCallback? = null

    override fun getItemCount(): Int {
        return when {
            items == null -> return 0
            items.size > 5 -> items.size
            items.size == 5 -> items.size + 1
            else -> items.size
        }
    }

    override fun getLayout(viewType: Int): Int {
        return if (items.size > 5) {
            R.layout.item_search_topic_stock
        } else if (items.size == 5) {
            when (viewType) {
                default -> R.layout.item_search_topic_stock
                else -> {
                    R.layout.item_add_more
                }
            }
        } else {
            R.layout.item_search_topic_stock
        }
    }

    override fun createViewHolder(v: View?, viewType: Int): RecyclerView.ViewHolder {
        return if (items.size > 5) {
            ViewHolder(v, false, false)
        } else if (items.size == 5) {
            return when (viewType) {
                default -> ViewHolder(v, false, false)
                else -> {
                    ViewHolderBottom(v, true, false)
                }
            }
        } else {
            ViewHolder(v, false, false)
        }
    }

    interface OnAddTopicClickItemCallback {
        fun onAddTopicClickItem(pos: Int, item: SearchStockInfo?, view: View)
    }

    inner class ViewHolder(v: View?, needClick: Boolean, needLongClick: Boolean) :
        ListItemViewHolder<SearchStockInfo>(v, needClick, needLongClick) {

        @BindView(R2.id.iv_tag)
        lateinit var iv_tag: ImageView

        @BindView(R2.id.tv_stock_name)
        lateinit var tv_stock_name: TextView

        @BindView(R2.id.tv_stock_code)
        lateinit var tv_stock_code: TextView

        @BindView(R2.id.iv_add)
        lateinit var iv_add: ImageView

        init {
            iv_add.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (v == iv_add) {
                onAddTopicClickItemCallback?.onAddTopicClickItem(position, getItem(position), v)
            } else {
                super.onClick(v)
            }
        }

        override fun bind(item: SearchStockInfo?, position: Int) {
            // item_ts.text = item?.ts
            when (item?.ts) {
                "SH" -> {
                    iv_tag.background = ResUtil.getDrawable(R.mipmap.ic_ts_sh)
                }
                "SZ" -> {
                    iv_tag.background = ResUtil.getDrawable(R.mipmap.ic_ts_sz)
                }
                "HK" -> {
                    iv_tag.background = ResUtil.getDrawable(R.mipmap.ic_ts_hk)
                }
            }
            tv_stock_code.text = item?.code
            tv_stock_name.text = item?.name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.size > 5) {
            default
        } else if (items.size == 5) {
            when (position) {
                itemCount - 1 -> {
                    bottom
                }
                else -> {
                    default
                }
            }
        } else {
            default
        }
    }

    override fun getItem(position: Int): SearchStockInfo? {
        if (position > items.size || position == items.size) return null
        return super.getItem(position)
    }

    inner class ViewHolderBottom(v: View?, needClick: Boolean, needLongClick: Boolean) :
        ListItemViewHolder<SearchStockInfo>(v, needClick, needLongClick) {
        @BindView(R2.id.rl_add_more)
        lateinit var rl_add_more: RelativeLayout

        override fun bind(item: SearchStockInfo?, position: Int) {

        }
    }
}