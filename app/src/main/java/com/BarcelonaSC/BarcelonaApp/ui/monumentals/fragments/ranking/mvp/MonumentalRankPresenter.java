package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp;

import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;

import java.util.List;

/**
 * Created by RYA-Laptop on 18/02/2018.
 */

public class MonumentalRankPresenter implements MonumentalRankContract.Presenter, MonumentalRankContract.ModelResultListener {

    private static final String TAG = MonumentalRankPresenter.class.getSimpleName();
    private MonumentalRankContract.View view;
    private MonumentalRankModel model;

    public MonumentalRankPresenter(MonumentalRankContract.View view, MonumentalRankModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(MonumentalRankContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetMRankSuccess(List<MonumentalRankingItem> rankingItemList) {
        view.showRankingList(rankingItemList);
    }

    @Override
    public void onGetMRankFailed() {

    }

    @Override
    public void loadMonumentalRank() {
        model.loadMonumentalRank(this);
    }

    @Override
    public void cancel() {
        model.cancel();
    }

    @Override
    public void reset() {

    }

    private boolean isViewNull() {
        return view == null;
    }

}