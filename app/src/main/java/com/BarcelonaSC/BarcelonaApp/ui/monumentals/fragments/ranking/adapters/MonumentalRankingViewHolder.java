package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.adapters;

import android.content.Context;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/19/17.
 */

public class MonumentalRankingViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text_position)
    TextView tvPosition;
    @BindView(R.id.text_name)
    TextView tvName;
    @BindView(R.id.tv_last_name)
    TextView tvLastName;
    @BindView(R.id.img_monumental)
    ImageView imgMonumental;
    @BindView(R.id.text_votes)
    TextView tvVotes;
    @BindView(R.id.Bar)
    View bar;
    @BindView(R.id.ll_applause)
    LinearLayout llApplause;

    Context context;

    public static MonumentalRankingViewHolder getInstance(ViewGroup parent) {
        return new MonumentalRankingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monumental_ranking, parent, false));
    }

    public MonumentalRankingViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(MonumentalRankingItem rankingItem, int position) {

        tvPosition.setText(""+ (position + 1));


        if (rankingItem.getNombre() != null) {
            tvName.setText(rankingItem.getNombre());
        }


        if (rankingItem.getMiniatura() != null) {
            Glide.with(context).load(rankingItem.getMiniatura()).into(imgMonumental);
        }

        if (rankingItem.getTotalVotos() != null) {
            tvVotes.setText(String.valueOf(rankingItem.getTotalVotos()));
        }

        if (rankingItem.getPorcentaje() != null) {
            getPercentage(rankingItem.getPorcentaje());
        }
    }

    private void getPercentage(Double percentage) {
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) bar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = (float) (percentage / 100);
        bar.requestLayout();
    }
}
