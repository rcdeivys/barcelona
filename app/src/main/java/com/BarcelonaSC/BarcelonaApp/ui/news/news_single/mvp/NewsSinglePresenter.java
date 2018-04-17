package com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp;

import com.BarcelonaSC.BarcelonaApp.models.NewsSingleData;

import java.util.List;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

public class NewsSinglePresenter implements NewsSingleContract.Presenter, NewsSingleContract.ModelResultListener {

    private NewsSingleContract.View view;
    private NewsSingleModel model;
    private List<NewsSingleData> noticias;

    public NewsSinglePresenter(NewsSingleContract.View view, NewsSingleModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(NewsSingleContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getNewsSingle(String id) {
        model.getNewsSingle(id, this);
    }

    @Override
    public void onGetSuccess(List<NewsSingleData> noticias) {
        this.noticias = noticias;
        if (isViewNull()) return;
        view.setNewsSingle(this.noticias);
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

}