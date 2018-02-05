package com.millonarios.MillonariosFC.ui.news.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.models.News;
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

    Context context;

    public static NewsViewHolder getInstance(ViewGroup parent) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
    }

    public NewsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(News item) {
        if (item.getFoto() != null) {
          /*  RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            imgHeader.setLayoutParams(params); */
            imgHeader.setAlpha((float) 1.0);

            Glide.with(context)
                    .load(item.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                    .into(imgHeader);
        }

        if (item.getFecha() != null)
            textDate.setText(Commons.getStringDate2(item.getFecha()));

        if (item.getTitulo() != null)
            textTitle.setText(item.getTitulo());
    }


}
