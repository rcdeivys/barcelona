package com.BarcelonaSC.BarcelonaApp.ui.wall.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;

import java.util.List;

/**
 * Created by Pedro Gomez on 26/01/2018.
 */

public class WallSearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<WallSearchItem> wallSearchItems;
    private OnItemClickListener onItemClickListener;

    public WallSearchAdapter(List<WallSearchItem> wallSearchItems, Context mContext) {
        this.mContext = mContext;
        this.wallSearchItems = wallSearchItems;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WallSearchViewHolder.getInstance(parent);
    }

    private void configureGroupsViewHolder(final WallSearchViewHolder holder, final int position) {
        holder.setData(wallSearchItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickProfile(wallSearchItems.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((WallSearchViewHolder) holder, position);
    }

    public void updateAll(List<WallSearchItem> update) {
        wallSearchItems.addAll(update);
        notifyDataSetChanged();
    }

    public void clearAll() {
        wallSearchItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return wallSearchItems.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickProfile(WallSearchItem wallSearchItem);
    }

}
