package com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp;

import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class WallSearchPresenter implements WallSearchContract.Presenter, WallSearchContract.ModelResultListener {


    private static final String TAG = WallSearchPresenter.class.getSimpleName();
    private WallSearchContract.View view;
    private WallSearchModel wallSearchModel;

    public WallSearchPresenter(WallSearchContract.View view, WallSearchModel wallSearchModel) {
        this.view = view;
        this.wallSearchModel = wallSearchModel;
    }

    @Override
    public void onAttach(WallSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    @Override
    public void search(String name) {
        view.showProgress();
        wallSearchModel.search(name, "1", this);
    }

    @Override
    public void searchPage(String name, String page) {
        view.showProgress();
        wallSearchModel.search(name, page, this);
    }

    @Override
    public void onGetSearchSuccess(List<WallSearchItem> searchItems, boolean page) {
        if (view == null) return;
        view.hideProgress();
        if (!page)
            view.search(searchItems);
        else
            view.searchPage(searchItems);
    }

    @Override
    public void onSearchFailed() {
        if (view == null) return;
        view.hideProgress();
        view.showToastError("");
    }
}
