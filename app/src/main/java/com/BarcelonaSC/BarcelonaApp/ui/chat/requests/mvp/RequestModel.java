package com.BarcelonaSC.BarcelonaApp.ui.chat.requests.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.RequestApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;

/**
 * Created by Cesar on 30/01/2018.
 */

public class RequestModel {
    private static final String TAG = RequestModel.class.getSimpleName();


    private RequestApi requestApi;

    public RequestModel(RequestApi requestApi) {
        this.requestApi = requestApi;
    }

    public void loadRequest(String id, final RequestContract.ModelResultListener result) {

        result.onGetRequestSuccess(FirebaseManager.getInstance().getUsuario().getSolicitudesPendientes());
    }


    public void loadSuggest(final RequestContract.ModelResultListener result) {
        result.onGetSuggestSuccess(FirebaseManager.getInstance().getListSugerencias());

    }

    public void sendInvitedToUser(String myID, String UserToInvite, final RequestContract.ModelResultListener result) {
        FirebaseManager.getInstance().enviarNuevaSolicitud(myID, UserToInvite, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                result.onInviteSuccess();
            }

            @Override
            public void onError() {
                result.onInviteFailed();
            }
        });
    }

    public void acceptUser(String myID, final String UserToInvite, final RequestContract.ModelResultListener result) {
        FirebaseManager.getInstance().aceptarSolicitud(UserToInvite, myID, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {

                result.onAcceptSuccess();
            }

            @Override
            public void onError() {
                Log.i(TAG, "--->acceptUser() onError ");
                result.onAcceptFailed();
            }
        });
    }

    public void rejectUser(String myID, String UserToInvite, final RequestContract.ModelResultListener result) {
        FirebaseManager.getInstance().rechazarSolicitud(UserToInvite, myID, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                Log.i(TAG, "--->rejectUser() onComplete ");
                result.onRejectSuccess();
            }

            @Override
            public void onError() {
                Log.i(TAG, "--->rejectUser() onError ");
                result.onRejectFailed();
            }
        });
    }
}
