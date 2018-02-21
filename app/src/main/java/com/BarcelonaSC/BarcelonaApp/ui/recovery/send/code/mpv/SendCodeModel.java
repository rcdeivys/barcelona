package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv;

import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.models.SendCode;
import com.BarcelonaSC.BarcelonaApp.models.response.SendCodeResponse;
import com.BarcelonaSC.BarcelonaApp.app.api.RecoveryPasswordApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendCodeModel {

    private static final String TAG = RecoveryPasswordApi.class.getSimpleName();
    private RecoveryPasswordApi recoveryPasswordApi;
    private ProfileApi profileApi;
    private AuthItem authItem;

    public SendCodeModel(RecoveryPasswordApi recoveryPasswordApi) {
        this.recoveryPasswordApi = recoveryPasswordApi;
        this.profileApi = profileApi;
    }

    public void sendCode(final String email, final String code, final SendCodeContract.ModelResultListener result) {
        SendCode sendCodeBody = new SendCode(email, code);

        recoveryPasswordApi.sendCode(sendCodeBody).enqueue(new NetworkCallBack<SendCodeResponse>() {
            @Override
            public void onRequestSuccess(SendCodeResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    authItem = response.getData();
                    loadUser(result);
                } else {
                    result.onFailed("Codigo incorrecto");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }

    public void loadUser(final SendCodeContract.ModelResultListener result) {
        profileApi.get(authItem.getToken()).enqueue(new NetworkCallBack<UserResponse>() {
            @Override
            public void onRequestSuccess(UserResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onSuccess(authItem);
                    SessionManager.getInstance().setUser(response.getData());
                    SessionManager.getInstance().setSession(authItem);
                }

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }

}