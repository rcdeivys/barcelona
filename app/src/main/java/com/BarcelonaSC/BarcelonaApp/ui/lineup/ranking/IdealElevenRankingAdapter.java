package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.AlineacionOficialData;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.models.OnceIdealData;
import com.BarcelonaSC.BarcelonaApp.models.Ranking;

import java.util.List;

public class IdealElevenRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int ITEM_GAME = 1001;
    private static final int ITEM_RANKING = 1002;
    Context context;
    List<Object> list;

    public IdealElevenRankingAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_GAME:
                return HeaderGameVH.getInstance(parent);
            case ITEM_RANKING:
                return IdealElevenRankingVH.getInstance(parent);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IdealElevenRankingVH) {
            configRankingVH((IdealElevenRankingVH) holder, position);
        } else {
            ((HeaderGameVH) holder).setData((GameSummonedData) list.get(position));
        }
    }

    public void configRankingVH(IdealElevenRankingVH vh, int position) {

        if (position == 1) {
            vh.setLineup((List<AlineacionOficialData>) list.get(position));
        }

        if (position == 2) {
            vh.setIdealEleven((List<OnceIdealData>) list.get(position));
        }

        if (position == 3) {
            vh.setRanking(((Ranking) list.get(position)), position, true);
        }

        if (position > 3) {
            vh.setRanking(((Ranking) list.get(position)), position, false);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof GameSummonedData) {
            return ITEM_GAME;
        } else {
            return ITEM_RANKING;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateAll(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
