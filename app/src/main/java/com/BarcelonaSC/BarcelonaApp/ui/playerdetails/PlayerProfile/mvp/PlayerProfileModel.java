package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.api.TeamApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.PlayerApplause;
import com.BarcelonaSC.BarcelonaApp.models.response.ApplauseResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.PlayerResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.SetApplauseResponse;

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

        teamApi.getPlayerData(playerId, new SessionManager(App.getAppContext()).getSession().getToken()).enqueue(new NetworkCallBack<PlayerResponse>() {
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

    public void setPlayerApplause(final PlayerApplause playerApplause, final PlayerProfileContract.ModelResultListener listener) {
        playerApplause.setToken(new SessionManager(App.getAppContext()).getSession().getToken());
        teamApi.setApplause(playerApplause).enqueue(new NetworkCallBack<SetApplauseResponse>() {
            @Override
            public void onRequestSuccess(SetApplauseResponse response) {
                List<String> error = response.getError();
                if ("exito".equals(response.getStatus())) {
                    listener.onSetPlayerApplauseSuccess(playerApplause.getIdjugador(), response.getAplauso());

                } else if ("no_dorado".equals(response.getStatus())) {
                    listener.noDoradoErrorListener();
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
