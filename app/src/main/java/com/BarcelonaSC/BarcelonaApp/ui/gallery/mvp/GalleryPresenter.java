package com.BarcelonaSC.BarcelonaApp.ui.gallery.mvp;

import com.BarcelonaSC.BarcelonaApp.models.GalleryItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardojpr on 10/16/17.
 */

public class GalleryPresenter implements GalleryContract.Presenter, GalleryContract.ModelResultListener {

    private static final String TAG = GalleryPresenter.class.getSimpleName();
    private GalleryContract.View view;
    private GalleryModel GalleryModel;

    private ArrayList<GalleryItem> GalleryItemList;

    public GalleryPresenter(GalleryContract.View GalleryItemContract, GalleryModel GalleryModel) {
        this.view = GalleryItemContract;
        this.GalleryModel = GalleryModel;
        GalleryItemList = new ArrayList<>();
    }


    @Override
    public void onSuccess(List<GalleryItem> GalleryItem) {
        if (isViewNull()) return;

        for (GalleryItem GalleryItems : GalleryItem)
            GalleryItemList.add(GalleryItems);
        view.setGallery(GalleryItemList);

    }

    @Override
    public void onGetGalleryFailed() {
        if (isViewNull()) return;
        view.showToastError();

    }

    @Override
    public void onAttach(GalleryContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void loadGallery(int id) {
        if (view == null) return;
        GalleryItemList.clear();
        GalleryModel.loadGallery(id, this);
    }

    @Override
    public void onclickGalleryItem(int position) {
        if (isViewNull()) return;
    }

    private boolean isViewNull() {
        return view == null;
    }
}
