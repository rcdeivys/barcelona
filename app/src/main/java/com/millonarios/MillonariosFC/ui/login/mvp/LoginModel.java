package com.millonarios.MillonariosFC.ui.login.mvp;

import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.api.AuthApi;
import com.millonarios.MillonariosFC.app.api.ProfileApi;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.AuthItem;
import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.response.AuthResponse;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.UserResponse;

/**
 * Created by Amplex on 12/10/2017.
 */

public class LoginModel {

    private static final String TAG = LoginModel.class.getSimpleName();
    private AuthApi authApi;
    private ProfileApi profileApi;
    private AuthResponse authResponse;
    private SessionManager sessionManager;

    public LoginModel(AuthApi authApi, ProfileApi profileApi) {
        this.authApi = authApi;
        this.profileApi = profileApi;
        sessionManager = new SessionManager(App.get().getBaseContext());
    }

    public void loadLogin(User user, final LoginContract.ModelResultListener result) {
        authApi.signIn(user).enqueue(new NetworkCallBack<AuthResponse>() {
            @Override
            public void onRequestSuccess(AuthResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    authResponse = response;
                    loadUser(response.getData().getToken(), result);
                } else
                    result.onGetLoginFailed(response.getError().get(0));
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetLoginFailed(errorMessage);
            }
        });
    }

    public void loadLoginSocial(User user, final LoginContract.ModelResultListener result) {
        authApi.signInSocial(user).enqueue(new NetworkCallBack<AuthResponse>() {
            @Override
            public void onRequestSuccess(AuthResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    authResponse = response;
                    loadUser(response.getData().getToken(), result);
                } else
                    result.onGetLoginFailed("Ocurrio un problema, vuelva a intentarlo");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetLoginFailed(errorMessage);
            }
        });
    }

    public void loadUser(final String token, final LoginContract.ModelResultListener result) {
        AuthItem authItem = new AuthItem();
        authItem.setToken(token);
        profileApi.get(token).enqueue(new NetworkCallBack<UserResponse>() {
            @Override
            public void onRequestSuccess(UserResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onGetLoginSuccess(authResponse.getData());
                    sessionManager.setUser(response.getData());
                }

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetLoginFailed(errorMessage);
            }
        });
    }


}