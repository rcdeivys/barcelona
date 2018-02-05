package com.millonarios.MillonariosFC.ui.home.menu.team.mossapplauded.mvp;

import com.millonarios.MillonariosFC.models.MostApplaudedPlayerData;


/**
 * Created by Carlos on 14/10/2017.
 */

public class ApplaudedPresenter implements ApplaudedContract.Presenter, ApplaudedContract.ModelResultListener {

    private static final String TAG = ApplaudedPresenter.class.getSimpleName();
    private ApplaudedContract.View view;
    private ApplaudedModel model;
    private MostApplaudedPlayerData mostApplaudedPlayer;

    public ApplaudedPresenter(ApplaudedContract.View view, ApplaudedModel model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void onAttach(ApplaudedContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getPlayerApplause() {
        model.getPlayersApplause(this);
    }

    @Override
    public String getAcumulatePlayer(int position) {
        return mostApplaudedPlayer.getAcumulado().get(position).getIdjugador();
    }

    @Override
    public String getActualPlayer(int position) {
        return mostApplaudedPlayer.getPartido_actual().get(position).getIdjugador();
    }


    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onGetPlayerApplauseSuccess(MostApplaudedPlayerData mostApplaudedPlayer) {
        this.mostApplaudedPlayer = mostApplaudedPlayer;
        if (isViewNull()) return;
        view.setApplauseData(this.mostApplaudedPlayer);


    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToastError(error);

    }
}
