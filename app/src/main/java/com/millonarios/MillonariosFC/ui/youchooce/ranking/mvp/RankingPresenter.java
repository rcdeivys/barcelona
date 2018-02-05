package com.millonarios.MillonariosFC.ui.youchooce.ranking.mvp;

import com.millonarios.MillonariosFC.models.PlayerVote;
import com.millonarios.MillonariosFC.models.RespuestaData;

import java.util.List;


/**
 * Created by Carlos on 14/10/2017.
 */

public class RankingPresenter implements RankingContract.Presenter, RankingContract.ModelResultListener {

    private static final String TAG = RankingPresenter.class.getSimpleName();
    private RankingContract.View view;
    private RankingModel model;
    private List<RespuestaData> mRespuestasData;

    public RankingPresenter(RankingContract.View view, RankingModel model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void onAttach(RankingContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getRanking() {
        model.getRanking(this,false);
    }

    @Override
    public void getRanking(boolean show) {
        model.getRanking(this,show);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onGetRankingVotesSuccess(List<RespuestaData> mRespuestasData) {
        this.mRespuestasData = mRespuestasData;
        if (isViewNull()) return;
        view.setVotesData(this.mRespuestasData);
    }

    @Override
    public void noShowVotes() {

        if (isViewNull()) return;
        view.noShowVotes();
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToastError(error);

    }
}
