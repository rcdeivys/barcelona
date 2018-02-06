package com.BarcelonaSC.BarcelonaApp.ui.gallery.views.holders;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_gallery)
    public ImageView imgGallery;

    Context context;

    public static GalleryViewHolder getInstance(ViewGroup parent) {
        return new GalleryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery, parent, false));
    }

    public GalleryViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setImgGallery(String img, int position) {
        imgGallery.setVisibility(View.VISIBLE);
        if (position == 0)
            Glide.with(context).load(img)
                    .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                    .into(imgGallery);
        else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Commons.getWidthDisplay() / 2, Commons.getWidthDisplay() / 2);

            imgGallery.setLayoutParams(params);
            imgGallery.setScaleType(ImageView.ScaleType.FIT_XY);
            imgGallery.setAdjustViewBounds(true);
            Glide.with(context).load(img)
                    .apply(new RequestOptions().centerCrop().override(Commons.getWidthDisplay() / 2, Commons.getWidthDisplay() / 2).placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                    .into(imgGallery);
        }
    }


}
