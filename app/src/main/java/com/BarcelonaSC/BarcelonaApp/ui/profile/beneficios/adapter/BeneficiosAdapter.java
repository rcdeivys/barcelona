package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;

import java.util.List;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

public class BeneficiosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int BENEFICIOS_ITEM = 1001;

    private static final String TAG = BeneficiosAdapter.class.getSimpleName();
    private Context context;
    private List<BeneficioData> newsList;
    private int height;
    private OnItemClickListener onItemClickListener;

    public BeneficiosAdapter(List<BeneficioData> newsList, Context context) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BeneficiosViewHolder.getInstance(parent);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        configurBeneficiosViewHolder(((BeneficiosViewHolder) holder), position);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private void configurBeneficiosViewHolder(final BeneficiosViewHolder holder, final int position) {
        holder.setData(newsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(newsList.get(position),0);
            }
        });
    }

    public void updateAll(List<BeneficioData> update) {
        newsList.clear();
        newsList.addAll(newsList.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(BeneficioData beneficio, int currentVideo);
    }
}
