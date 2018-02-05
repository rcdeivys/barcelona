package com.millonarios.MillonariosFC.ui.lineup.idealeleven.mvp;

import android.util.Log;

import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.api.LineUpApi;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.GameSummonedResponse;
import com.millonarios.MillonariosFC.models.response.MyIdealElevenResponse;
import com.millonarios.MillonariosFC.models.response.SendMyIdealElevenData;
import com.google.gson.JsonObject;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenModel {

    private static final String TAG = IdealElevenModel.class.getSimpleName();
    private LineUpApi lineUpApi;

    public IdealElevenModel(LineUpApi lineUpApi) {
        this.lineUpApi = lineUpApi;

    }


    public void getPlayByPlay(final IdealElevenContract.ModelResultListener modelResultListener) {
        lineUpApi.getGameSummoned().enqueue(new NetworkCallBack<GameSummonedResponse>() {
            @Override
            public void onRequestSuccess(GameSummonedResponse response) {
                if (response.getData() != null) {
                    modelResultListener.onGetGameSummonedSuccess(response.getData());
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

    public void setIdealEleven(SendMyIdealElevenData sendMyIdealElevenData, final IdealElevenContract.ModelResultListener modelResultListener) {
        lineUpApi.setOnceIdeal(sendMyIdealElevenData).enqueue(new NetworkCallBack<JsonObject>() {
            @Override
            public void onRequestSuccess(JsonObject response) {
                Log.i(TAG, "--->PROBAR " + response.toString());
                String status = response.get("status").getAsString();
                if ("exito".equals(status)) {
                    String url = response.getAsJsonObject("data").get("url").getAsString();
                    new SessionManager(App.getAppContext()).setUrlLineUpShare(url);
                    modelResultListener.onSetIdealElevenSuccess();
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

    public void getIdealEleven(String token, final IdealElevenContract.ModelResultListener modelResultListener) {
        lineUpApi.getOnceIdeal(token).enqueue(new NetworkCallBack<MyIdealElevenResponse>() {
            @Override
            public void onRequestSuccess(MyIdealElevenResponse response) {
                if (response != null && "exito".equals(response.getStatus())) {

                    modelResultListener.onGetIdealElevenSuccess(response.getData());
                } else {
                    if (response != null && response.getError() != null && response.getError().size() > 0) {
                        modelResultListener.onError(response.getError().get(0));
                    } else {
                        modelResultListener.onError("");
                    }

                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i(TAG, "--->getIdealEleven 5 ");
                modelResultListener.onError(errorMessage);
            }
        });
    }


}
