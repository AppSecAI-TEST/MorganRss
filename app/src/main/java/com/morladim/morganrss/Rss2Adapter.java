package com.morladim.morganrss;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.morladim.morganrss.database.entity.Item;

import java.util.List;

/**
 * <br>创建时间：2017/7/24.
 *
 * @author morladim
 */
public class Rss2Adapter extends RecyclerView.Adapter<Rss2Adapter.Rss2ViewHolder> {

    private List<Item> data;

    public Rss2Adapter(List<Item> items) {
        data = items;
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

    public static class Rss2ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv1, tv2;


        public Rss2ViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tx1);
            tv2 = itemView.findViewById(R.id.tx2);
        }
    }
}
