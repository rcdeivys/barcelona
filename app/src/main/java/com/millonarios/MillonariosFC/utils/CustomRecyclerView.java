package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Leonardojpr on 9/15/17.
 */

public abstract class CustomRecyclerView<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_FOOTER = -99;
    private LoadingFooter loadingFooter;

    public CustomRecyclerView(Context context) {
        loadingFooter = new LoadingFooter(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            return new FooterViewHolder(loadingFooter.getLoading());
        } else {
            return onCurrentCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() != TYPE_FOOTER) {
            onCurrentBindViewHolder((VH) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getCurrentItemCount() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLastItem(position)) {
            return TYPE_FOOTER;
        } else {
            return getCurrentItemViewType(position);
        }
    }

    public abstract int getCurrentItemCount();

    public abstract void onCurrentBindViewHolder(VH holder, int position);

    public abstract VH onCurrentCreateViewHolder(ViewGroup parent, int viewType);

    public abstract int getCurrentItemViewType(int position);

    private boolean isLastItem(int position) {
        return getCurrentItemCount() == position;
    }

    public void showLoading() {
        loadingFooter.showLoading();
    }

    public void hideLoading() {
        loadingFooter.hide();
    }

    public void showNoMoreData() {
        loadingFooter.showNoMoreData();
    }

    public void setTextEmptyListColor(int color) {
        loadingFooter.txtEmptyList.setTextColor(color);
    }

    public void showNoMoreDataToDisplay() {
        loadingFooter.showNoMoreDataToDisplay();
    }

    public void showEmpty() {
        loadingFooter.showEmpty();
    }

    public void showError(View.OnClickListener click) {
        loadingFooter.showError(click);
    }

    public LoadingFooter getLoadingFooter() {
        return loadingFooter;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
