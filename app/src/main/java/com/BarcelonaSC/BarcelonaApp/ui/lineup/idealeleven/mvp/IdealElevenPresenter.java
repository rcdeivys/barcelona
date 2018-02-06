package com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.IdealElevenData;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;
import com.BarcelonaSC.BarcelonaApp.models.response.SendMyIdealElevenData;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.UtilDragAnDrop;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenPresenter implements IdealElevenContract.Presenter, IdealElevenContract.ModelResultListener {

    public static final String TAG = IdealElevenPresenter.class.getSimpleName();
    private IdealElevenContract.View view;
    private IdealElevenModel model;
    private GameSummonedData gameSummonedData;


    public IdealElevenPresenter(IdealElevenContract.View view, IdealElevenModel model) {
        this.view = view;
        this.model = model;
    }


    public void getPlayByPlay() {
        model.getPlayByPlay(this);

    }

    public void getIdealEleven() {
        model.getIdealEleven(new SessionManager(App.getAppContext()).getSession().getToken(), this);
    }

    @Override
    public void onAttach(IdealElevenContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    @Override
    public void onSetIdealElevenSuccess() {
        view.showShareDialog(App.get().getString(R.string.success_ideal_selection));
        getIdealEleven();
    }

    @Override
    public void onGetIdealElevenSuccess(SendMyIdealElevenData sendMyIdealElevenData) {
        new SessionManager(App.getAppContext()).setUrlLineUpShare(sendMyIdealElevenData.getUrl());
        view.setIdealEleven(UtilDragAnDrop.generateIdealElevenData(sendMyIdealElevenData.getJugadores(), getPlayerSummonedData()));
    }

    @Override
    public void onGetGameSummonedSuccess(GameSummonedData data) {
        Log.i(TAG, "--->onGetPlayByPlaySuccess " + data.toString());
        if (isViewNull()) return;
        gameSummonedData = data;

        view.setPlayByPlayData(gameSummonedData);
    }


    public void setIdealEleven(List<IdealElevenData> idealElevenData, String imageEncode) {
        Log.i(TAG, "--->TOKEN:" + new SessionManager(App.getAppContext()).getSession().getToken());
        model.setIdealEleven(UtilDragAnDrop.llenardata(idealElevenData, imageEncode), this);
    }


    public List<NominaItem> getPlayerSummonedData() {

        return gameSummonedData.getJugadores();
    }


    @Override
    public void onError(String error) {
        Log.i(TAG, "--->onError");
        if (isViewNull()) return;
        view.showToast(error);

    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onClickItem(int position) {
        if (isViewNull()) return;

        view.navigateToProfilePlayerActivity(String.valueOf(gameSummonedData.getJugadores().get(position).getIdJugador()));

    }

    @Override
    public void onClickHeader() {
        if (isViewNull()) return;

    }


}
