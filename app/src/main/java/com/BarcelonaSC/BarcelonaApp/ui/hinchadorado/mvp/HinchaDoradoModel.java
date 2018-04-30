package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.api.HinchaDoradoApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.SuscriptionStatusResponse;
import com.BarcelonaSC.BarcelonaApp.models.TiposSuscripcionResponse;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;

/**
 * Created by cesar on 24/2/2018.
 */

public class HinchaDoradoModel {

    private static final String TAG = HinchaDoradoModel.class.getSimpleName();

    private HinchaDoradoApi hinchaDoradoApi;
    private ProfileApi profileApi;

    public HinchaDoradoModel(HinchaDoradoApi hinchaDoradoApi, ProfileApi profileApi) {
        this.hinchaDoradoApi = hinchaDoradoApi;
        this.profileApi = profileApi;
    }

    public void loadSuscripcion(final HinchaDoradoContract.ModelResultListener result) {
        Log.i(TAG, "--->" + hinchaDoradoApi.getTiposSuscripcion().request().url());
        hinchaDoradoApi.getTiposSuscripcion().enqueue(new NetworkCallBack<TiposSuscripcionResponse>() {
            @Override
            public void onRequestSuccess(TiposSuscripcionResponse response) {
                if (response != null) {
                    result.onGetSuscripcionSuccess(response.getData());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetSuscripcionFailed();
            }
        });
    }

    public void getStatusSuscription(final HinchaDoradoContract.ModelResultListener result) {
        String token = SessionManager.getInstance().getSession().getToken();
        Log.i(TAG, "--->" + hinchaDoradoApi.getStatusSuscripcion(token).request().url());

        hinchaDoradoApi.getStatusSuscripcion(token).enqueue(new NetworkCallBack<SuscriptionStatusResponse>() {
            @Override
            public void onRequestSuccess(SuscriptionStatusResponse response) {
                if (response != null) {
                    Log.i(TAG, "--->onGetStatusSuscripcionSuccess" + response.getStatus());
                    result.onGetStatusSuscripcionSuccess(response.getStatus());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetStatusSuscripcionFailed();
            }
        });
    }


    public void updateUser(final User user, String token, final HinchaDoradoContract.ModelResultListener result) {
        profileApi.update(token, user).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                Log.i(TAG, "//--->onGetStatusSuscripcionSuccess" + response.getStatus());
                if ("exito".equals(response.getStatus()))
                    if (SessionManager.getInstance().getSession() != null && SessionManager.getInstance().getSession().getToken() != null)
                        App.get().component().profileApi()
                                .get(SessionManager.getInstance().getSession().getToken())
                                .enqueue(new NetworkCallBack<UserResponse>() {
                                    @Override
                                    public void onRequestSuccess(UserResponse response) {
                                        if ("exito".equals(response.getStatus()))
                                            SessionManager.getInstance().setUser(response.getData());


                                    }

                                    @Override
                                    public void onRequestFail(String errorMessage, int errorCode) {

                                    }
                                });
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i(TAG, "//--->onGetStatusSuscripcionSuccess");
            }
        });
    }
}
