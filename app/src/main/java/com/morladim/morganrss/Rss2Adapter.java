package com.morladim.morganrss;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morladim.morganrss.database.entity.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>创建时间：2017/7/24.
 *
 * @author morladim
 */
public class Rss2Adapter extends RecyclerView.Adapter<Rss2Adapter.Rss2ViewHolder> {

    private volatile int offset;

    private int limit = 10;

    private List<Item> data;

    private boolean hasMore = true;

    public Rss2Adapter() {
        data = new ArrayList<>();
    }

    @Override
    public Rss2ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Rss2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Rss2ViewHolder holder, int position) {
        if (holder != null) {
            holder.tv1.setText(data.get(position).getTitle());
            holder.tv2.setText(data.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void refresh(List<Item> items) {
        offset = 0;
        data.clear();
        data.addAll(items);
        this.notifyDataSetChanged();
    }

    public void loadMore(List<Item> items) {
        data.addAll(items);
        this.notifyDataSetChanged();
//        offset += limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public static class Rss2ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv1, tv2;


        public Rss2ViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.title);
            tv2 = itemView.findViewById(R.id.description);
        }
    }
}
