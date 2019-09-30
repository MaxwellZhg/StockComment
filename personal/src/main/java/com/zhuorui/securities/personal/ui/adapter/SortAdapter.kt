/*
package com.zhuorui.securities.infomation.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import com.zhuorui.securities.infomation.R
import com.zhuorui.securities.infomation.ui.model.JsonBean

*/
/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/19
 * Desc:
 *//*


class SortAdapter(private val context: Context, private var list: List<JsonBean>?) :
    BaseAdapter() {


    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): Any {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_common_contry_dicts, null)
        }
        val holder = ViewHolder.getHolder(convertView!!)

        //设置数据
        val friend = list!![position]
        holder.tv_contry.text = list!![position].hant

        val currentWord = friend.en[0] + ""
        //获取上一个item的首字母
        if (position > 0) {
            val lastWord = list!![position - 1].en[0] + ""
            if (currentWord == lastWord) {
                //首字母相同
                holder.rl_tips.visibility=View.GONE
                holder.tv_frist_tips.visibility = View.GONE
            } else {
                //首字母不同
                //由于布局是复用的，所以需要设置可见
                holder.rl_tips.visibility=View.VISIBLE
                holder.tv_frist_tips.visibility = View.VISIBLE
                holder.tv_frist_tips.text = currentWord
            }
        } else {
            //第一个
            holder.tv_frist_tips.visibility = View.VISIBLE
            holder.tv_frist_tips.setText(currentWord)
        }
        return convertView
    }

    class ViewHolder//构造方法
        (convertView: View) {
        var tv_frist_tips: TextView = convertView.findViewById<TextView>(R.id.tv_frist_tips)
        var tv_contry: TextView = convertView.findViewById<TextView>(R.id.tv_contry)
        var rl_tips: RelativeLayout = convertView.findViewById<RelativeLayout>(R.id.rl_tips)

        companion object {

            //对ViewHolder的封装
            fun getHolder(convertView: View): ViewHolder {
                var holder: ViewHolder? = convertView.tag as ViewHolder
                if (holder == null) {
                    holder = ViewHolder(convertView)
                    convertView.tag = holder
                }
                return holder
            }
        }
    }
}*/
