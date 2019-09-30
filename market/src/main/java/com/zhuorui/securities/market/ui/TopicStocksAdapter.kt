package com.zhuorui.securities.market.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.zhuorui.securities.market.R
import com.zhuorui.securities.market.R2
import com.zhuorui.securities.market.model.StockMarketInfo
import com.zhuorui.securities.market.model.StockTsEnum
import com.zhuorui.securities.base2app.adapter.BaseListAdapter
import com.zhuorui.securities.base2app.util.ResUtil
import com.zhuorui.commonwidget.StateButton
import com.zhuorui.securities.market.util.MathUtil

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/7
 * Desc: 自选股列表适配器
 */
class TopicStocksAdapter : BaseListAdapter<StockMarketInfo>() {

    private val default = 0x00
    private val bottom = 0x01

    override fun getItemCount(): Int {
        if (items.isNullOrEmpty()) {
            return 1
        }
        return items.size + 1
    }

    override fun getItem(position: Int): StockMarketInfo? {
        if (items.isNullOrEmpty() || position > items.size || position == items.size) return null
        return super.getItem(position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            itemCount - 1 -> {
                bottom
            }
            else -> {
                default
            }
        }
    }

    override fun getLayout(viewType: Int): Int {
        return when (viewType) {
            default -> R.layout.item_topic_stock
            else -> {
                R.layout.item_add_topic_stock
            }
        }
    }

    override fun createViewHolder(v: View?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            default -> ViewHolderdefalt(v, true, true)
            else -> {
                ViewHolderBottom(v, true, false)
            }
        }
    }

    inner class ViewHolderdefalt(v: View?, needClick: Boolean, needLongClick: Boolean) :
        ListItemViewHolder<StockMarketInfo>(v, needClick, needLongClick) {

        @BindView(R2.id.tv_stock_tile)
        lateinit var tv_stock_tile: TextView
        @BindView(R2.id.iv_stock_ts)
        lateinit var iv_stock_ts: ImageView
        @BindView(R2.id.stock_code)
        lateinit var stock_code: TextView
        @BindView(R2.id.stock_up_down)
        lateinit var stock_up_down: StateButton
        @BindView(R2.id.tv_price)
        lateinit var tv_price: TextView

        @SuppressLint("SetTextI18n")
        override fun bind(item: StockMarketInfo?, position: Int) {
            if (item?.longClick != null && item.longClick) {
                itemView.setBackgroundResource(R.color.color_312E40_85)
            } else {
                itemView.setBackgroundColor(Color.TRANSPARENT)
            }

            tv_stock_tile.text = item?.name
            stock_code.text = item?.code
            when (item?.ts) {
                StockTsEnum.HK.name -> {
                    iv_stock_ts.setImageResource(R.mipmap.ic_ts_hk)
                }
                StockTsEnum.SH.name -> {
                    iv_stock_ts.setImageResource(R.mipmap.ic_ts_sh)
                }
                StockTsEnum.SZ.name -> {
                    iv_stock_ts.setImageResource(R.mipmap.ic_ts_sz)
                }
            }
            tv_price.text = item?.price?.let { MathUtil.rounded2(it).toString() }

            if (item?.diffRate!! > 0 || item.diffRate == 0.0) {
                tv_price.setTextColor(ResUtil.getColor(R.color.up_price_color)!!)

                stock_up_down.setUnableBackgroundColor(ResUtil.getColor(R.color.up_stock_color)!!)
                stock_up_down.text = "+" + item.diffRate + "%"
            } else {
                tv_price.setTextColor(ResUtil.getColor(R.color.down_price_color)!!)

                stock_up_down.setUnableBackgroundColor(ResUtil.getColor(R.color.down_stock_color)!!)
                stock_up_down.text = item.diffRate.toString() + "%"
            }
        }
    }

    inner class ViewHolderBottom(v: View?, needClick: Boolean, needLongClick: Boolean) :
        ListItemViewHolder<StockMarketInfo>(v, needClick, needLongClick) {
        @BindView(R2.id.ll_add_stock)
        lateinit var ll_add_stock: LinearLayout

        override fun bind(item: StockMarketInfo?, position: Int) {

        }

    }
}