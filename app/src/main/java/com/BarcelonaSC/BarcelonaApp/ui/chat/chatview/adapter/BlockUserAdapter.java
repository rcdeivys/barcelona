package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.crashlytics.android.Crashlytics;

import java.util.List;

/**
 * Created by cesar on 11/4/2018.
 */

public class BlockUserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private List<Miembro> membersList;
    private OnItemClickListener onItemClickListener;

    public BlockUserAdapter(List<Miembro> friendsList, Context mContext) {
        this.mContext = mContext;
        this.membersList = friendsList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BlockUserViewHolder.getInstance(parent);
    }

    private void configureBlockViewHolder(final BlockUserViewHolder holder, final int position) {
        holder.setData(membersList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickFriend(membersList.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureBlockViewHolder((BlockUserViewHolder)holder,position);
    }

    public void updateAll(List<Miembro> update) {
        Crashlytics.log(Log.DEBUG,"AMIGO","---> TAM "+update.size());
        if (membersList.size() > 0)
            membersList.clear();
        membersList.addAll(membersList.size(), update);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickFriend(Miembro miembro);
    }
}
