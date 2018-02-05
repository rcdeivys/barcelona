package com.millonarios.MillonariosFC.ui.chat.groups.adapter;

/**
 * Created by Pedro Gomez on 02/02/2018.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;

import java.util.List;

public class GroupsActivityAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<GroupModelView> groupsList;
    private OnItemClickListener onItemClickListener;
    private int type = -1;

    public GroupsActivityAdapter(List<GroupModelView> groupsList, Context mContext) {
        this.mContext = mContext;
        this.groupsList = groupsList;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("GROUP"," ---> tipo: "+viewType);
        return GroupsViewHolder.getInstance(parent,mContext);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        configureGroupsViewHolder((GroupsViewHolder)holder,position);
    }

    private void configureGroupsViewHolder(final GroupsViewHolder holder, final int position) {
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

    @Override
    public int getItemViewType(int position) {
        return type;
    }

    public void updateAll(List<GroupModelView> update) {
        groupsList.clear();
        groupsList.addAll(groupsList.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(GroupModelView group);
    }

}
