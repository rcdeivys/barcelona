package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.TeamApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.MostApplaudedPlayerResponse;


/**
 * Created by Carlos on 14/10/2017.
 */

public class ApplaudedModel {

    public static final String TAG = ApplaudedModel.class.getSimpleName();
    private TeamApi teamApi;

    public ApplaudedModel(TeamApi teamApi) {
        this.teamApi = teamApi;
    }

    public void getPlayersApplause(final ApplaudedContract.ModelResultListener listener) {
        teamApi.getMosApplaudedPlayer().enqueue(new NetworkCallBack<MostApplaudedPlayerResponse>() {
            @Override
            public void onRequestSuccess(MostApplaudedPlayerResponse response) {
                if (response.getData() != null) {
                    listener.onGetPlayerApplauseSuccess(response.getData());
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
}
