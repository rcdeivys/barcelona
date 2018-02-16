package com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.models.ValidateAccItem;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;

/**
 * Created by Leonardojpr on 12/6/17.
 */

public class ValidateAccModel {

    private static final String TAG = ValidateAccModel.class.getSimpleName();
    private AuthApi authApi;
    private ProfileApi profileApi;
    private AuthResponse authResponse;

    public ValidateAccModel(AuthApi authApi, ProfileApi profileApi) {
        this.authApi = authApi;
        this.profileApi = profileApi;
    }

    public void sendCode(ValidateAccItem validateAccItem, final ValidateAccContract.ModelResultListener result) {
        authApi.validateAccount(validateAccItem).enqueue(new NetworkCallBack<AuthResponse>() {
            @Override
            public void onRequestSuccess(AuthResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    authResponse = response;
                    loadUser(response.getData().getToken(), result);
                } else {
                    result.onFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }

    public void resendCode(ValidateAccItem validateAccItem, final ValidateAccContract.ModelResultListener result) {

        authApi.resendCode(validateAccItem.getEmail()).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.resendCodeSuccess(response.getData().getMensaje_pin());
                } else {
                    result.onFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }

    public void loadUser(final String token, final ValidateAccContract.ModelResultListener result) {
        AuthItem authItem = new AuthItem();
        authItem.setToken(token);
        profileApi.get(token).enqueue(new NetworkCallBack<UserResponse>() {
            @Override
            public void onRequestSuccess(UserResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onSuccess(authResponse);
                    SessionManager.getInstance().setUser(response.getData());
                    SessionManager.getInstance().setSession(authResponse.getData());
                }

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }
}
