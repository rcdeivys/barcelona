package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.PlayByPlay;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenRankingPresenter implements IdealElevenRankingContract.Presenter, IdealElevenRankingContract.ModelResultListener {

    public static final String TAG = IdealElevenRankingPresenter.class.getSimpleName();
    private IdealElevenRankingContract.View view;
    private IdealElevenRankingModel model;

    private PlayByPlay playByPlay;


    public IdealElevenRankingPresenter(IdealElevenRankingContract.View view, IdealElevenRankingModel model) {
        this.view = view;
        this.model = model;
    }

    public void getPlayByPlay() {
        model.getPlayByPlay(this);

    }


    @Override
    public void onAttach(IdealElevenRankingContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onIdealElevenRankingData(List<Object> data) {
        if (isViewNull()) return;
        if (data != null)
            view.showIdealElevenRankingData(data);
    }


    @Override
    public void onError(String error) {
        Log.i(TAG, "--->onError");
        if (isViewNull()) return;

    }

    @Override
    public void getIdealElevenRankingData() {
        model.getPlayByPlay(this);
    }

    public boolean isViewNull() {
        if (view == null) {
            return true;
        } else {
            return false;
        }
    }
}
