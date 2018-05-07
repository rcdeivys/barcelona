package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.crashlytics.android.Crashlytics;

import java.util.List;

/**
 * Created by Cesar on 05/04/2018.
 */

public class GroupCommonsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<GroupModelView> groupList;
    private OnItemClickListener onItemClickListener;

    public GroupCommonsAdapter(List<GroupModelView> groupList, Context mContext) {
        this.mContext = mContext;
        this.groupList = groupList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return GroupCommonsHolder.getInstance(parent);
    }

    private void configureGroupsViewHolder(final GroupCommonsHolder holder, final int position) {
        holder.setData(groupList.get(position));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((GroupCommonsHolder) holder,position);
    }

    public void updateAll(List<GroupModelView> update) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> TAM "+update.size());
        if(groupList.size()>0)
            groupList.clear();
        groupList.addAll(groupList.size(), update);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(groupList!=null) {
            return groupList.size();
        }else{
            return 0;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickSelectedFriend(GroupModelView group);
    }
}