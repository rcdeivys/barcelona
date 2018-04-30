package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.models.BeneficioData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

public class BeneficiosViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.iv_beneficio)
    ImageView imgBeneficio;

    @BindView(R.id.tv_title_beneficio)
    TextView textTitle;

    Context context;

    public static BeneficiosViewHolder getInstance(ViewGroup parent) {
        return new BeneficiosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beneficios, parent, false));
    }

    public BeneficiosViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(BeneficioData item) {
        Log.i("BENEFICIO","---> "+item.getUrl());
        try{
            Glide.with(context)
                    .load(item.getUrl())
                    .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                    .into(imgBeneficio);
        }catch (Exception e){
            e.printStackTrace();
        }
        /*if (item.getTitulo() != null)
            textTitle.setText(item.getTitulo());*/
    }

}
