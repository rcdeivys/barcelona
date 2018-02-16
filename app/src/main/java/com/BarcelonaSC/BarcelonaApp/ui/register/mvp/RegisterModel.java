package com.BarcelonaSC.BarcelonaApp.ui.register.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;


/**
 * Created by Amplex on 15/10/2017.
 */

public class RegisterModel {

    private static final String TAG = RegisterModel.class.getSimpleName();
    private AuthApi authApi;
    private ProfileApi profileApi;

    public RegisterModel(AuthApi authApi, ProfileApi profileApi) {
        this.profileApi = profileApi;
        this.authApi = authApi;
    }

    public void loadRegister(final User user, final RegisterContract.ModelResultListener result) {
        authApi.signUp(user).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onGetRegisterSuccess(response);
                } else
                    result.onGetRegisterFailed(response.getError().get(0));
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetRegisterFailed(errorMessage);
            }
        });
    }

    public void loadUser(final AuthResponse authResponse, final RegisterContract.ModelResultListener result) {

        profileApi.get(authResponse.getData().getToken()).enqueue(new NetworkCallBack<UserResponse>() {
            @Override
            public void onRequestSuccess(UserResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    SessionManager.getInstance().setUser(response.getData());
                    SessionManager.getInstance().setSession(authResponse.getData());
                }

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetRegisterFailed(errorMessage);
            }
        });
    }

}