package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MSProfilePresenter implements MSProfileContract.Presenter, MSProfileContract.ModelResultListener {

    private static final String TAG = MSProfilePresenter.class.getSimpleName();
    private MSProfileContract.View view;
    private MSProfileModel model;

    public MSProfilePresenter(MSProfileContract.View view, MSProfileModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(MSProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetMonumentalSuccess(MonumentalItem itemList) {
        if (isViewNull()) return;
        view.showMonumental(itemList);
    }

    @Override
    public void onGetMonumentalFailed() {
        if (isViewNull()) return;
    }

    @Override
    public void loadMonumental(String id) {
        if (isViewNull()) return;
        model.loadMonumental(id, this);
    }

    @Override
    public void voteMonumental(String idMonumental, String idEncuesta, String emei) {
        if (isViewNull()) return;
        model.voteMonumental(idMonumental, idEncuesta, emei, this);
    }

    @Override
    public void clickItem(News news) {
        if (news.getTipo().matches(Constant.NewsType.GALERY)) {
            view.navigateToGalleryActivity(news.getId());
        } else if (news.getTipo().matches(Constant.NewsType.VIDEO)) {
            view.navigateToVideoNewsActivity(news);
        } else if (news.getTipo().matches(Constant.NewsType.INFOGRAFY) || news.getTipo().matches(Constant.NewsType.STAT)) {
            view.navigateToInfografiaActivity(news);
        } else {
            view.navigateToNewsDetailsActivity(news);
        }
    }

    @Override
    public void cancel() {
        model.cancel();
    }

    @Override
    public void reset() {
        model.reset();
    }

    public boolean isViewNull() {
        return view == null;
    }

}