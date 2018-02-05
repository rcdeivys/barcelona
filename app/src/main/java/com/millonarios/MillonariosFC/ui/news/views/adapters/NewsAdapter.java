package com.millonarios.MillonariosFC.ui.news.views.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.ui.news.views.holders.NewsVideoViewHolder;
import com.millonarios.MillonariosFC.ui.news.views.holders.NewsViewHolder;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter personalizado para el ListView de noticias
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NEWS_ITEM = 1001;
    private static final int NEWS_VIDEO_ITEM = 1002;

    private static final String TAG = NewsAdapter.class.getSimpleName();
    private Context context;
    private List<News> newsList;
    private int height;
    private OnItemClickListener onItemClickListener;
    SimpleDateFormat formatOut;
    SimpleDateFormat formatIn;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;

        formatOut = new SimpleDateFormat("yyyy-MM-dd");
        formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NEWS_ITEM:
                return NewsViewHolder.getInstance(parent);
            case NEWS_VIDEO_ITEM:
                return NewsVideoViewHolder.getInstance(parent);
            default:
                return NewsViewHolder.getInstance(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position).getTipo().equals(Constant.NewsType.VIDEO)) {
            return NEWS_VIDEO_ITEM;
        } else {
            return NEWS_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof NewsViewHolder) {
            configurNewsViewHolder(((NewsViewHolder) holder), position);
        } else {
            configureNesVideosViewHolder(((NewsVideoViewHolder) holder), position);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private void configurNewsViewHolder(final NewsViewHolder holder, final int position) {
        holder.setData(newsList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(newsList.get(position));
            }
        });
    }

    private void configureNesVideosViewHolder(final NewsVideoViewHolder holder, final int position) {
        holder.setData(newsList.get(position));
        holder.imgHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(newsList.get(position));
            }
        });
    }

    public void updateAll(List<News> update) {
        newsList.addAll(newsList.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(News news);

    }
}
