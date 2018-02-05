package com.millonarios.MillonariosFC.ui.home.menu.team.players.mvp;


import com.millonarios.MillonariosFC.models.GameSummonedData;
import com.millonarios.MillonariosFC.models.NominaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerPresenter implements PlayerContract.Presenter, PlayerContract.ModelResultListener {

    private static final String TAG = PlayerPresenter.class.getSimpleName();
    private PlayerContract.View view;
    private PlayerModel model;
    String type;

    private List<NominaItem> playoffDatas;
    private GameSummonedData gameSummonedData;
    private int i = 0;


    public PlayerPresenter(PlayerContract.View view, PlayerModel playerModel, String type) {
        this.view = view;
        this.model = playerModel;
        this.type = type;
        playoffDatas = new ArrayList<>();
        gameSummonedData = new GameSummonedData();
    }


    @Override
    public void onAttach(PlayerContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getPlayeroff() {
        model.getPlayeroff(this);
    }

    @Override
    public void getGameSummoned() {
        model.getGameSummoned(this);
    }

    public void getGameFB() {
        model.getGameFB(this);
    }

    @Override
    public void onGetPlayerOff(List<NominaItem> players) {

        playoffDatas = players;

        if (isViewNull()) return;
        view.setPlayer(playoffDatas);

    }

    @Override
    public void onGetGameSummoned(GameSummonedData gameSummoned) {

        this.gameSummonedData = gameSummoned;
        this.playoffDatas = gameSummonedData.getJugadores();
        if (isViewNull()) return;
        view.setPlayerWithHeader(this.gameSummonedData);

    }


    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToastError(error);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onClickItem(int position) {
        if (isViewNull()) return;
        view.navigateToPlayerActivity(playoffDatas.get(position).getIdJugador(), type);
    }

    @Override
    public void onClickHeader() {
        if (isViewNull()) return;
        view.navigateToLineUp(String.valueOf(gameSummonedData.getIdpartido()));
    }
}
