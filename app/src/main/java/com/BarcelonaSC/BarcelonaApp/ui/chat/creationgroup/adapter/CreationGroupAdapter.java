package com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;

import java.util.List;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */

public class CreationGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Amigos> groupsList;
    private OnItemClickListener onItemClickListener;

    public CreationGroupAdapter(List<Amigos> groupsList, Context mContext) {
        this.mContext = mContext;
        this.groupsList = groupsList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CreationGroupViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((CreationGroupViewHolder)holder,position);
    }

    private void configureGroupsViewHolder(final CreationGroupViewHolder holder, final int position) {
        holder.setData(groupsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(groupsList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupsList.size();
    }

    public void updateAll(List<Amigos> update) {
        if(groupsList.size()>0)
            groupsList.clear();
        groupsList.addAll(groupsList.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(Amigos friend);
    }

}
