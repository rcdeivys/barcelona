package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp;

import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.models.PlayerApplause;
import com.millonarios.MillonariosFC.models.response.ApplauseResponse;
import com.millonarios.MillonariosFC.models.response.SetApplauseResponse;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.PlayerResponse;

import java.util.List;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfileModel {

    private static final String TAG = PlayerProfileModel.class.getSimpleName();
    private TeamApi teamApi;

    public PlayerProfileModel(TeamApi teamApi) {
        this.teamApi = teamApi;
    }

    public void getPlayerData(String playerId, final PlayerProfileContract.ModelResultListener listener) {

        teamApi.getPlayerData(playerId).enqueue(new NetworkCallBack<PlayerResponse>() {
            @Override
            public void onRequestSuccess(PlayerResponse response) {
                if (response.getData() != null) {

                    listener.onGetPlayerSuccess(response.getData());
                } else
                    listener.onError("");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });

    }

    public void getPlayerDataFB(String playerId, final PlayerProfileContract.ModelResultListener listener) {
        teamApi.getPlayerDataFB(playerId).enqueue(new NetworkCallBack<PlayerResponse>() {
            @Override
            public void onRequestSuccess(PlayerResponse response) {
                if (response.getData() != null) {
                    listener.onGetPlayerSuccess(response.getData());
                } else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }

    public void setPlayerApplause(PlayerApplause playerApplause, final PlayerProfileContract.ModelResultListener listener) {
        teamApi.setApplause(playerApplause).enqueue(new NetworkCallBack<SetApplauseResponse>() {
            @Override
            public void onRequestSuccess(SetApplauseResponse response) {
                List<String> error = response.getError();
                if ("exito".equals(response.getStatus())) {
                    listener.onSetPlayerApplauseSuccess();
                } else if (error != null) {
                    listener.onError(error.get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }


    public void getPlayerApplause(String playerId) {

        teamApi.getApplause(playerId).enqueue(new NetworkCallBack<ApplauseResponse>() {
            @Override
            public void onRequestSuccess(ApplauseResponse response) {

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });
    }
}
