package com.millonarios.MillonariosFC.ui.lineup.officiallineup.mvp;

import android.util.Log;

import com.millonarios.MillonariosFC.app.api.LineUpApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.PlaybyplayResponse;

/**
 * Created by Carlos on 13/11/2017.
 */

public class OLineUpModel {

    private static final String TAG = OLineUpModel.class.getSimpleName();
    private LineUpApi lineUpApi;

    public OLineUpModel(LineUpApi lineUpApi) {
        this.lineUpApi = lineUpApi;

    }


    public void getPlayByPlay( final OLineUpContract.ModelResultListener modelResultListener) {
        lineUpApi.getPlayByPlay().enqueue(new NetworkCallBack<PlaybyplayResponse>() {
            @Override
            public void onRequestSuccess(PlaybyplayResponse response) {
                Log.i(TAG, "--->getPlayByPlay onRequestSuccess");
                if (response.getData() != null) {
                    modelResultListener.onGetPlayByPlaySuccess(response.getData());
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i(TAG, "--->getPlayByPlay onRequestFail");
                modelResultListener.onError(errorMessage);
            }
        });
    }
}
