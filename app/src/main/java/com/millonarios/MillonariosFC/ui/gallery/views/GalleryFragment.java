package com.millonarios.MillonariosFC.ui.gallery.views;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.ui.gallery.GalleryActivity;
import com.millonarios.MillonariosFC.utils.TouchImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/13/17.
 */

public class GalleryFragment extends Fragment {

    private static final String TAG = GalleryFragment.class.getSimpleName();

    @BindView(R.id.img_touch_gallery)
    TouchImageView touchImageView;

    public static GalleryFragment getInstance(String url) {
        GalleryFragment galleryFragment = new GalleryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GalleryActivity.URL, url);
        galleryFragment.setArguments(bundle);
        return galleryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_gallery, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        if (getArguments().getString(GalleryActivity.URL) != null) {
            setTouchImageView(getArguments().getString(GalleryActivity.URL));
        }
    }

    public void setTouchImageView(String url) {
        touchImageView.setImageBitmap(null);
        touchImageView.setVisibility(View.VISIBLE);
        touchImageView.setZoom(1f);
        Log.d(TAG, "url " + url);
        Glide.with(this)
                .asBitmap()
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.logo_transparencia).error(R.drawable.logo_transparencia))
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        touchImageView.setImageBitmap(resource);
                    }
                });
    }
}
