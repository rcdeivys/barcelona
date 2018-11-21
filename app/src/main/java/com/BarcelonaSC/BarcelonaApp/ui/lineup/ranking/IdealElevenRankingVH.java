package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking;

import android.content.Context;
import android.support.percent.PercentLayoutHelper;
import android.support.percent.PercentRelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.AlineacionOficialData;
import com.BarcelonaSC.BarcelonaApp.models.OnceIdealData;
import com.BarcelonaSC.BarcelonaApp.models.Ranking;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdealElevenRankingVH extends RecyclerView.ViewHolder {

    @BindView(R.id.content_progressbar)
    RelativeLayout contentProgressBar;

    @BindView(R.id.titulo)
    FCMillonariosTextView titulo;

    @BindView(R.id.tag_name)
    GridLayout layoutTagView;

    @BindView(R.id.progressbar)
    View progressbar;

    @BindView(R.id.total)
    FCMillonariosTextView total;

    @BindView(R.id.position)
    FCMillonariosTextView position;

    @BindView(R.id.percentage)
    FCMillonariosTextView percentage;

    @BindView(R.id.message)
    FCMillonariosTextView message;


    Context context;

    public static IdealElevenRankingVH getInstance(ViewGroup parent) {
        return new IdealElevenRankingVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ideal_eleven_ranking, parent, false));
    }

    public IdealElevenRankingVH(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setLineup(List<AlineacionOficialData> data) {
        layoutTagView.removeAllViews();
        contentProgressBar.setVisibility(View.GONE);

        titulo.setText("ALINEACIÓN OFICIAL");
        titulo.setVisibility(View.VISIBLE);
        if (!data.isEmpty()) {
            message.setVisibility(View.GONE);
            for (int i = 0; i < data.size(); i++) {
                View tagView = LayoutInflater.from(context).inflate(R.layout.item_player_tag, null, false);
                FCMillonariosTextView tagTextView = tagView.findViewById(R.id.tagTextView);
                tagTextView.setText(data.get(i).getNombre());
                tagTextView.setWidth(Commons.getWidthDisplay() / 4);
                layoutTagView.addView(tagView);
            }
        } else {
            message.setText("Aún no hay alineación oficial");
            message.setVisibility(View.VISIBLE);

        }
    }

    public void setIdealEleven(List<OnceIdealData> data) {
        contentProgressBar.setVisibility(View.GONE);
        layoutTagView.removeAllViews();
        titulo.setText("ONCE IDEAL");
        titulo.setVisibility(View.VISIBLE);
        if (!data.isEmpty()) {
            message.setVisibility(View.GONE);
            for (int i = 0; i < data.size(); i++) {
                View tagView = LayoutInflater.from(context).inflate(R.layout.item_player_tag, null, false);
                FCMillonariosTextView tagTextView = tagView.findViewById(R.id.tagTextView);
                tagTextView.setText(data.get(i).getNombre());
                tagTextView.setBackground(Commons.getDrawable(R.drawable.background_ranking_gray));
                tagTextView.setTextColor(Commons.getColor(R.color.white));
                tagTextView.setWidth(Commons.getWidthDisplay() / 4);
                if (data.get(i).getComun()) {
                    tagTextView.setBackground(Commons.getDrawable(R.drawable.background_border_ranking));
                }
                layoutTagView.addView(tagView);
            }
        } else {
            message.setText("Aún no tienes once ideal cargado");
            message.setVisibility(View.VISIBLE);

        }
    }

    public void setRanking(Ranking data, int pos, boolean showTitle) {
        contentProgressBar.setVisibility(View.VISIBLE);
        layoutTagView.removeAllViews();
        layoutTagView.setColumnCount(3);
        PercentRelativeLayout.LayoutParams params = (PercentRelativeLayout.LayoutParams) progressbar.getLayoutParams();
        PercentLayoutHelper.PercentLayoutInfo info = params.getPercentLayoutInfo();
        info.widthPercent = data.getPorcentaje().floatValue() / 100;
        progressbar.requestLayout();
        message.setVisibility(View.GONE);
        if (showTitle) {
            titulo.setVisibility(View.VISIBLE);
            titulo.setText("RANKING VOTACIÓN");
        } else {
            titulo.setVisibility(View.GONE);
        }
        position.setText("" + (pos - 2)+".");
        //total.setText(String.valueOf(data.getVotos()));
        total.setText("" + Math.round(data.getPorcentaje()) + " %");
        //percentage.setText("" + Math.round(data.getPorcentaje()) + " %");
        for (int i = 0; i < data.getJugadores().size(); i++) {
            View tagView = LayoutInflater.from(context).inflate(R.layout.item_player_tag, null, false);
            FCMillonariosTextView tagTextView = tagView.findViewById(R.id.tagTextView);
            tagTextView.setBackground(Commons.getDrawable(R.drawable.background_ranking_gray));
            tagTextView.setTextColor(Commons.getColor(R.color.white));
            tagTextView.setWidth(Commons.getWidthDisplay() / 4);
            if (data.getJugadores().get(i).getComun()) {
                tagTextView.setBackground(Commons.getDrawable(R.drawable.background_border_ranking));
            }
            tagTextView.setText(data.getJugadores().get(i).getNombre());
            layoutTagView.addView(tagView);

        }
    }
}
