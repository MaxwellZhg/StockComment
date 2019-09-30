package com.zhuorui.securities.base2app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * 基础的RecyclerView的Adapter
 */
public abstract class BaseListAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected String TAG;
    private List<T> items;
    private OnClickItemCallback<T> clickItemCallback;
    private onLongClickItemCallback<T> longClickItemCallback;

    public BaseListAdapter() {
        TAG = this.getClass().getName();
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        if (items == null) return;
        this.items = items;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        if (item == null) return;
        this.items.add(item);
        notifyDataSetChanged();
    }

    public void addItems(List<T> items) {
        if (items == null) return;
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clearItems(){
        if (items == null) return;
        this.items.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return items.get(position);
    }

    public void setClickItemCallback(OnClickItemCallback<T> clickItemCallback) {
        this.clickItemCallback = clickItemCallback;
    }

    public void setLongClickItemCallback(onLongClickItemCallback<T> longClickItemCallback) {
        this.longClickItemCallback = longClickItemCallback;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ListItemViewHolder) {
            ((ListItemViewHolder) holder).bindView(getItem(position), position);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            ((ListItemViewHolder) holder).bindViewPart(getItem(position), position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false);
        return createViewHolder(v, viewType);
    }

    protected abstract int getLayout(int viewType);

    protected abstract RecyclerView.ViewHolder createViewHolder(View v, int viewType);

    public interface OnClickItemCallback<T> {
        void onClickItem(int pos, T item, View v);
    }

    public interface onLongClickItemCallback<T> {
        void onLongClickItem(int pos, T item, View view);
    }

    private void onClickItem(int pos, T item, View v) {
        if (clickItemCallback == null) return;
        clickItemCallback.onClickItem(pos, item, v);
    }

    private void onLongClickItem(int pos, T item, View v) {
        if (longClickItemCallback == null) return;
        longClickItemCallback.onLongClickItem(pos, item, v);
    }

    private void notifyPart() {
        int itemCount = getItemCount();
        if (itemCount <= 0) return;
        notifyItemRangeChanged(0, itemCount, "payload");
    }

    private void notifyPart(int position) {
        notifyItemChanged(position, "payload");
    }

    /**
     * 基础的ViewHolder实现
     * 默认实现单击；长按
     *
     * @param <T>
     */
    public abstract class ListItemViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        protected int position;

        public ListItemViewHolder(View v, boolean needClick, boolean needLongClick) {
            super(v);
            ButterKnife.bind(this, v);
            if (needClick) {
                v.setOnClickListener(this);
            }
            if (needLongClick) {
                v.setOnLongClickListener(this);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (position >= getItemCount() || position < 0) return false;
            onLongClickItem(position, getItem(position), v);
            return true;
        }

        @Override
        public void onClick(@NonNull View v) {
            if (position >= getItemCount() || position < 0) {
                return;
            }
            onClickItem(position, getItem(position), v);
        }

        private void bindView(T item, int position) {
            this.position = position;
            bind(item, position);
        }

        protected abstract void bind(T item, int position);

        /**
         * 局部绑定更新;
         * 具体局部更新的区域由编码决定
         *
         * @param item     item
         * @param position position
         */
        protected void bindViewPart(T item, int position) {
        }
    }
}
