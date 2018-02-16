package com.BarcelonaSC.BarcelonaApp.ui.chat.friendselection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.crashlytics.android.Crashlytics;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;

import java.util.List;

/**
 * Created by Pedro Gomez on 23/01/2018.
 */

public class FriendSelectedAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<Amigos> friendsList;
    private FriendSelectedAdapter.OnItemClickListener onItemClickListener;

    public FriendSelectedAdapter(List<Amigos> friendsList, Context mContext) {
        this.mContext = mContext;
        this.friendsList = friendsList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FriendSelectedViewHolder.getInstance(parent);
    }

    private void configureGroupsViewHolder(final FriendSelectedViewHolder holder, final int position) {
        holder.setData(friendsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickSelectedFriend(friendsList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((FriendSelectedViewHolder)holder,position);
    }

    public void updateAll(List<Amigos> update) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> TAM "+update.size());
        if(friendsList.size()>0)
            friendsList.clear();
        friendsList.addAll(friendsList.size(), update);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }

    public void setOnItemClickListener(FriendSelectedAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickSelectedFriend(Amigos friend);
    }
}
