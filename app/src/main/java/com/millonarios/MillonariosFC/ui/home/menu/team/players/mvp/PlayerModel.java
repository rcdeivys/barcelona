package com.millonarios.MillonariosFC.ui.home.menu.team.players.mvp;

import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.models.response.GameSummonedResponse;
import com.millonarios.MillonariosFC.models.response.PlayoffResponse;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerModel {

    private TeamApi teamApi;

    public PlayerModel(TeamApi teamApi) {
        this.teamApi = teamApi;
    }

    public void getPlayeroff(final PlayerContract.ModelResultListener modelResultListener) {
        teamApi.getPlayoffSummoned().enqueue(new NetworkCallBack<PlayoffResponse>() {
            @Override
            public void onRequestSuccess(PlayoffResponse response) {
                if (response.getStatus().equals(Constant.Key.SUCCESS)) {
                    modelResultListener.onGetPlayerOff(response.getData());
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                modelResultListener.onError(errorMessage);
            }
        });
    }

    public void getGameSummoned(final PlayerContract.ModelResultListener modelResultListener) {
        teamApi.getGameSummoned().enqueue(new NetworkCallBack<GameSummonedResponse>() {
            @Override
            public void onRequestSuccess(GameSummonedResponse response) {
                if (response.getData() != null) {
                    modelResultListener.onGetGameSummoned(response.getData());
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                modelResultListener.onError(errorMessage);
            }
        });
    }

    public void getGameFB(final PlayerContract.ModelResultListener modelResultListener) {
        teamApi.getPlayoffFB().enqueue(new NetworkCallBack<PlayoffResponse>() {
            @Override
            public void onRequestSuccess(PlayoffResponse response) {
                if (response.getStatus().equals(Constant.Key.SUCCESS)) {
                    modelResultListener.onGetPlayerOff(response.getData());
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                modelResultListener.onError(errorMessage);
            }
        });
    }

}
