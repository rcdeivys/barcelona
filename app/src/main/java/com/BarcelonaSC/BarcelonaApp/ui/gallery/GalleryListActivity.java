package com.BarcelonaSC.BarcelonaApp.ui.gallery;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.mvp.GalleryPresenter;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.GalleryItem;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.di.DaggerGalleryComponent;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.di.GalleryModule;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.mvp.GalleryContract;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.adapters.GalleryListAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leonardojpr on 10/12/17.
 */

public class GalleryListActivity extends BaseActivity implements GalleryListAdapter.ImgGalleryClickListener, GalleryContract.View {

    @BindView(R.id.gallery_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    GalleryListAdapter galleryListAdapter;
    List<GalleryItem> itemList;

    @Inject
    GalleryPresenter presenter;

    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_list);
        ButterKnife.bind(this);
        initComponent();
        id = getIntent().getIntExtra(Constant.Key.ID, 0);

        // Send data to Google Analytics for gallery
        App.get().registerCustomTrackScreen(Constant.Analytics.NEWS + "." + Constant.NewsTags.Gallery, Integer.toString(id), 1);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                presenter.loadGallery(id);
                progressBar.setVisibility(View.VISIBLE);

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initRecyclerView();
        presenter.loadGallery(id);
    }

    public void initComponent() {
        DaggerGalleryComponent.builder()
                .appComponent(App.get().component())
                .galleryModule(new GalleryModule(this))
                .build().inject(GalleryListActivity.this);
    }

    @OnClick(R.id.ib_sub_header_share)
    void onShareNews() {
        ShareSection.shareIndividual(Constant.Key.SHARE_NEWS, String.valueOf(id));
    }

    public void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        itemList = new ArrayList<>();
        galleryListAdapter = new GalleryListAdapter(itemList);
        galleryListAdapter.setImgGalleryClickListener(this);
        recyclerView.setAdapter(galleryListAdapter);
    }

    @Override
    public void onImgClickListener(String url, int position) {
        
        // Google Analytics screen click photo
        try {
            App.get().registerCustomTrackScreen(Constant.Analytics.NEWS + "." + Constant.NewsTags.GalleryPhoto, id + "." + Integer.toString(position), 1);
        } catch (Exception e) {
            App.get().registerTrackEvent(Constant.Analytics.ERROR, Constant.ActionTags.Clicked, Constant.Analytics.NEWS + "." + Constant.NewsTags.GalleryPhoto, 0);
        }

        Intent intent = new Intent(this, GalleryActivity.class);
        intent.putParcelableArrayListExtra(GalleryActivity.GALLERY, (ArrayList<? extends Parcelable>) itemList);
        intent.putExtra(GalleryActivity.POSITION, position);
        startActivity(intent);
    }

    @Override
    public void setGallery(List<GalleryItem> news) {
        itemList.clear();
        itemList = news;
        galleryListAdapter.updateAll(itemList);
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToGalleryActivity(List<GalleryItem> news, int position) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToastError() {

    }
}
