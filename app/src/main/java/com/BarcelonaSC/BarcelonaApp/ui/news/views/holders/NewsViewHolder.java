package com.BarcelonaSC.BarcelonaApp.ui.news.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/31/17.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_header)
    ImageView imgHeader;

    @BindView(R.id.tv_date)
    TextView textDate;

    @BindView(R.id.tv_title)
    TextView textTitle;

    @BindView(R.id.content_news_item)
    LinearLayout contentNewsItem;

    @BindView(R.id.texture_video_view)
    public CustomVideoView videoView;

    @BindView(R.id.iv_share)
    public ImageView ivShare;

    Context context;

    News news;

    public static NewsViewHolder getInstance(ViewGroup parent) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setNews(News item) {
        this.news = item;
        imgHeader.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        ivShare.setVisibility(View.VISIBLE);
        if (item.getFoto() != null) {
            imgHeader.setAlpha((float) 1.0);
            Glide.with(context)
                    .load(item.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm).override(Commons.dpToPx(220)))
                    .into(imgHeader);
        }

        if (item.getFecha() != null) {
            String date = Commons.getStringDate2(item.getFecha()).replace(".", "");
            textDate.setText(date.toUpperCase());
        }

        if (item.getTitulo() != null)
            textTitle.setText(item.getTitulo());
    }

    public void onClickContentNewsItem(View.OnClickListener onClickListener) {
        contentNewsItem.setOnClickListener(onClickListener);
    }

    public void setNewsVideo(News news, View.OnClickListener onClickListener) {
        imgHeader.setVisibility(View.GONE);
        videoView.setVisibility(View.VISIBLE);
        videoView.setImage(context, news.getFoto());
        videoView.setVideoUrl(news.getLink(), 0, news.isDorado());
        contentNewsItem.setOnClickListener(null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        videoView.setImage(context, news.getFoto());
        videoView.imgNews.setAdjustViewBounds(true);
        ivShare.setVisibility(View.VISIBLE);
        videoView.setButtonFullScreen(R.drawable.ic_media_fullscreen, Commons.getColor(R.color.white), onClickListener);
    }

    public int getVideoCurrentPosition() {
        return videoView.getCurrentPosition();
    }

    private void scaleVideo(int h) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
        videoView.setLayoutParams(layoutParams);
    }

}
