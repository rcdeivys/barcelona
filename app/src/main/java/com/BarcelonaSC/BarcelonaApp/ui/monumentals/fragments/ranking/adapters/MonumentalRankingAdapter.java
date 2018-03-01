package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;

import java.util.List;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public class MonumentalRankingAdapter extends RecyclerView.Adapter<MonumentalRankingViewHolder> {

    private List<MonumentalRankingItem> rankingItemList;
    private OnMonumentalRankingClickListener onMonumentalRankingClickListener;

    public MonumentalRankingAdapter(List<MonumentalRankingItem> rankingItemList) {
        this.rankingItemList = rankingItemList;
    }

    @Override
    public MonumentalRankingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return MonumentalRankingViewHolder.getInstance(viewGroup);
    }

    @Override
    public void onBindViewHolder(MonumentalRankingViewHolder monumentalRankingViewHolder, int i) {
        configurationRanking(monumentalRankingViewHolder, rankingItemList.get(i), i);
    }

    @Override
    public int getItemCount() {
        return rankingItemList.size();
    }

    public void configurationRanking(MonumentalRankingViewHolder holder, MonumentalRankingItem rankingItem, int position) {
        holder.setData(rankingItem, position);
    }

    public void update(List<MonumentalRankingItem> rankingItemList) {
        this.rankingItemList.clear();
        this.rankingItemList = rankingItemList;
        notifyDataSetChanged();
    }

    public void setOnMonumentalRankingClickListener(OnMonumentalRankingClickListener onMonumentalRankingClickListener) {
        this.onMonumentalRankingClickListener = onMonumentalRankingClickListener;
    }

    public interface OnMonumentalRankingClickListener {
        void onClickListener(String id);
    }
}