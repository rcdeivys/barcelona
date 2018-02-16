package com.BarcelonaSC.BarcelonaApp.ui.gallery;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.GalleryFragment;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.adapters.GalleryViewPagerAdapter;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.GalleryItem;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryActivity extends BaseActivity {

    private static String TAG = GalleryActivity.class.getSimpleName();
    public final static String URL = "url";
    public final static String GALLERY = "gallery";
    public final static String POSITION = "position";

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;

    List<GalleryFragment> galleryFragmentList;

    GalleryViewPagerAdapter galleryViewPagerAdapter;
    List<GalleryItem> galleryModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        tabs.setVisibility(View.GONE);
        galleryModelList = getIntent().getParcelableArrayListExtra(GALLERY);
        galleryFragmentList = new ArrayList<>();
        Log.d("GalleryActivity", " " + galleryModelList.size());
        for (GalleryItem item : galleryModelList) {

            galleryFragmentList.add(GalleryFragment.getInstance(item.getFoto()));
        }

        galleryViewPagerAdapter = new GalleryViewPagerAdapter(getSupportFragmentManager(), galleryFragmentList);
        pager.setAdapter(galleryViewPagerAdapter);

        pager.setCurrentItem(getIntent().getIntExtra(POSITION, 0));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
