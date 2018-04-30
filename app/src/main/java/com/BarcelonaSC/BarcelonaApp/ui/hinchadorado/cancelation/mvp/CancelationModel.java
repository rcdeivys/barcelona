package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp;

import com.google.gson.JsonObject;
import com.BarcelonaSC.BarcelonaApp.app.api.HinchaDoradoApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.SendReasonCancelation;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.response.ReasonResponse;

/**
 * Created by Carlos on 13/11/2017.
 */

public class CancelationModel {

    private static final String TAG = CancelationModel.class.getSimpleName();
    private HinchaDoradoApi hinchaDoradoApi;

    public CancelationModel(HinchaDoradoApi hinchaDoradoApi) {
        this.hinchaDoradoApi = hinchaDoradoApi;

    }

    public void getReason(final CancelationContract.ModelResultListener modelResultListener) {

        hinchaDoradoApi.getReason().enqueue(new NetworkCallBack<ReasonResponse>() {
            @Override
            public void onRequestSuccess(ReasonResponse response) {
                if (response.getData() != null) {
                    modelResultListener.onGetReasonSuccess(response.getData());
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

    public void cancelSubscription(SendReasonCancelation sendReasonCancelation
            , final CancelationContract.ModelResultListener modelResultListener) {
        hinchaDoradoApi.cancelSubscription(sendReasonCancelation).enqueue(new NetworkCallBack<JsonObject>() {
            @Override
            public void onRequestSuccess(JsonObject response) {
                String status = response.get("status").getAsString();
                if ("exito".equals(status)) {
                    UserItem userItem = SessionManager.getInstance().getUser();
                    userItem.setDorado(false);
                    SessionManager.getInstance().setUser(userItem);
                    modelResultListener.cancelSuccess();
                } else {
                    modelResultListener.onError("Error cancelando Suscripción");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                modelResultListener.onError("Error cancelando Suscripción");
            }
        });
    }


}
