package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalPoll;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalPresenter implements MonumentalContract.Presenter, MonumentalContract.ModelResultListener {

    private static final String TAG = MonumentalPresenter.class.getSimpleName();
    private MonumentalContract.View view;
    private MonumentalModel model;
    private MonumentalPoll poll;

    public MonumentalPresenter(MonumentalContract.View view, MonumentalModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(MonumentalContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetMonumentalSuccess(MonumentalPoll poll) {
        if (isViewNull()) return;
        this.poll = poll;
        view.setMonumentals(poll);
        view.hideProgress();
        view.setRefreshing(false);
    }

    @Override
    public void onGetMonumentalFailed() {
        if (isViewNull()) return;
        view.showToastError();
        view.hideProgress();
        view.setRefreshing(false);
    }

    @Override
    public void getMonumentals() {
        if (isViewNull()) return;
        model.loadMonumental(this);
        view.showProgress();
        view.setRefreshing(false);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onClickItem(int position) {
        if (isViewNull()) return;
        view.navigateToMonumentalProfile(String.valueOf(poll.getMonumentales().get(position).getIdmonumental()), String.valueOf(poll.getIdencuesta()));
    }

}