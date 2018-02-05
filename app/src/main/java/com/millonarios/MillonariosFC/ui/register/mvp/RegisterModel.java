package com.millonarios.MillonariosFC.ui.register.mvp;

import com.millonarios.MillonariosFC.app.api.AuthApi;
import com.millonarios.MillonariosFC.app.api.ProfileApi;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.response.AuthResponse;
import com.millonarios.MillonariosFC.models.response.GenericResponse;
import com.millonarios.MillonariosFC.models.response.UserResponse;


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