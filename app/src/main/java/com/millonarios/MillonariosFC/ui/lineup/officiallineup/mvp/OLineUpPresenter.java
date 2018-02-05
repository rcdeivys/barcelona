package com.millonarios.MillonariosFC.ui.lineup.officiallineup.mvp;

import android.util.Log;

import com.millonarios.MillonariosFC.models.PlayByPlay;

/**
 * Created by Carlos on 13/11/2017.
 */

public class OLineUpPresenter implements OLineUpContract.Presenter, OLineUpContract.ModelResultListener {

    public static final String TAG = OLineUpPresenter.class.getSimpleName();
    private OLineUpContract.View view;
    private OLineUpModel model;

    private PlayByPlay playByPlay;


    public OLineUpPresenter(OLineUpContract.View view, OLineUpModel model) {
        this.view = view;
        this.model = model;
    }

    public void getPlayByPlay() {
        model.getPlayByPlay(this);

    }


    @Override
    public void onAttach(OLineUpContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetPlayByPlaySuccess(PlayByPlay data) {
        if (isViewNull()) return;
        playByPlay = data;

        if (data.getVideo() != null)
            view.setVideo(data.getVideo(), data.getInfo());
        else
            view.setVideo("", "");

        view.setPlayByPlayData(playByPlay);
    }


    @Override
    public void onError(String error) {
        Log.i(TAG, "--->onError");
        if (isViewNull()) return;

    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onClickItem(int position, boolean isTitular) {
        if (isViewNull()) return;

        if (isTitular) {
            view.navigateToProfilePlayerActivity(String.valueOf(playByPlay.getTitulares().get(position).getIdjugador()));
        } else {
            view.navigateToProfilePlayerActivity(String.valueOf(playByPlay.getSuplentes().get(position).getIdjugador()));
        }

    }

    @Override
    public void onClickHeader() {
        if (isViewNull()) return;

    }
}
