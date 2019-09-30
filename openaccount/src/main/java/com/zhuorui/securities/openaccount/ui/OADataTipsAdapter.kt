package com.zhuorui.securities.openaccount.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.zhuorui.securities.base2app.adapter.BaseListAdapter
import com.zhuorui.securities.openaccount.R
import com.zhuorui.securities.openaccount.R2
import com.zhuorui.securities.openaccount.model.OADataTips

/**
 *    author : liuwei
 *    e-mail : vsanliu@foxmail.com
 *    date   : 2019-08-21 14:58
 *    desc   :
 */
class OADataTipsAdapter : BaseListAdapter<OADataTips>() {

    override fun getLayout(viewType: Int): Int {
        return R.layout.item_oa_data_tips
    }

    override fun getItemCount(): Int {
        return if(items == null) 0 else super.getItemCount()
    }

    override fun createViewHolder(v: View?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(v, false, false);
    }

    inner class ViewHolder(v: View?, needClick: Boolean, needLongClick: Boolean) :
        ListItemViewHolder<OADataTips>(v, needClick, needLongClick) {

        @BindView(R2.id.tv_title)
        lateinit var tv_title: TextView
        @BindView(R2.id.tv_desc)
        lateinit var tv_desc: TextView
        @BindView(R2.id.iv_icon)
        lateinit var iv_icon: ImageView

        override fun bind(item: OADataTips?, position: Int) {
            tv_title.text = item?.title
            tv_desc.text = item?.desc
            item?.icon?.let { iv_icon.setImageResource(it) }
        }

    }
}