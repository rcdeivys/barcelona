package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendSelectionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<FriendsModelView> friendsList;
    private OnItemClickListener onItemClickListener;

    public FriendSelectionAdapter(List<FriendsModelView> friendsList, Context mContext) {
        this.mContext = mContext;
        this.friendsList = friendsList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FriendSelectionViewHolder.getInstance(parent);
    }

    private void configureGroupsViewHolder(final FriendSelectionViewHolder holder, final int position) {
        holder.setData(friendsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickFriend(friendsList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((FriendSelectionViewHolder)holder,position);
    }

    public void updateAll(List<FriendsModelView> update) {


        friendsList.clear();
        friendsList.addAll(friendsList.size(), update);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickFriend(FriendsModelView friend);
    }
}
