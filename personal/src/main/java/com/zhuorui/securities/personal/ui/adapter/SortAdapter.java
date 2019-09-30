package com.zhuorui.securities.personal.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.zhuorui.securities.personal.R;
import com.zhuorui.securities.personal.ui.model.JsonBean;

import java.util.LinkedList;

/**
 * Created by Maxwell.
 * E-mail: maxwell_smith@163.com
 * Date: 2019/8/19
 * Desc:
 */
public class SortAdapter extends BaseAdapter {
    private Context context;

    private LinkedList<JsonBean> list=new LinkedList<>();

    public SortAdapter(Context context) {
        this.context = context;
    }
    public void addItems(LinkedList<JsonBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    public void clearItems(){
        this.list.clear();
        notifyDataSetChanged();
    }

    public LinkedList<JsonBean> getList(){
        return  list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_common_contry_dicts, null);
        }
        ViewHolder holder = ViewHolder.getHolder(convertView);

        //设置数据
         JsonBean jsonBean = list.get(position);
        holder.tv_contry.setText(jsonBean.getCn());
        holder.tv_contry_code.setText(jsonBean.getNumber());
        String currentWord = jsonBean.getSortLetters() + "";
        //获取上一个item的首字母
        if (position > 0) {
            String lastWord = list.get(position - 1).getSortLetters() + "";
            if (currentWord.equals(lastWord)) {
                //首字母相同
                holder.tv_frist_tips.setVisibility(View.GONE);
                holder.rl_tips.setVisibility(View.GONE);
            } else {
                //首字母不同
                //由于布局是复用的，所以需要设置可见
                holder.tv_frist_tips.setVisibility(View.VISIBLE);
                holder.rl_tips.setVisibility(View.VISIBLE);
                holder.tv_frist_tips.setText(currentWord);
            }
        } else {
            //第一个
            holder.rl_tips.setVisibility(View.VISIBLE);
            holder.tv_frist_tips.setVisibility(View.VISIBLE);
            holder.tv_frist_tips.setText(currentWord);
        }
        holder.rl_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        TextView tv_contry, tv_frist_tips,tv_contry_code;
        RelativeLayout rl_tips;

        //构造方法
        public ViewHolder(View convertView) {
            tv_contry = (TextView) convertView.findViewById(R.id.tv_contry);
            tv_frist_tips = (TextView) convertView.findViewById(R.id.tv_frist_tips);
            tv_contry_code = (TextView) convertView.findViewById(R.id.tv_contry_code);
            rl_tips=(RelativeLayout) convertView.findViewById(R.id.rl_tips);
        }

        //对ViewHolder的封装
        public static ViewHolder getHolder(View convertView) {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            if (holder == null) {
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            }
            return holder;
        }
    }
}