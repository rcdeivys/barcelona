package com.BarcelonaSC.BarcelonaApp.ui.login.mvp;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

/**
 * Created by Amplex on 12/10/2017.
 */

public class LoginModel {

    private static final String TAG = LoginModel.class.getSimpleName();
    private AuthApi authApi;
    private ProfileApi profileApi;
    private AuthResponse authResponse;
    private PreferenceManager preferenceManager;

    public LoginModel(AuthApi authApi, ProfileApi profileApi) {
        this.authApi = authApi;
        this.profileApi = profileApi;
        preferenceManager = new PreferenceManager(App.get());
    }

    public void loadLogin(User user, final LoginContract.ModelResultListener result) {
        authApi.signIn(user).enqueue(new NetworkCallBack<AuthResponse>() {
            @Override
            public void onRequestSuccess(AuthResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    authResponse = response;
                    preferenceManager.setBoolean(Constant.Key.IS_SOCIAL, false);
                    SessionManager.getInstance().setSession(response.getData());
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
                    preferenceManager.setBoolean(Constant.Key.IS_SOCIAL, true);
                    SessionManager.getInstance().setSession(response.getData());
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
                    SessionManager.getInstance().setUser(response.getData());
                    SessionManager.getInstance().setSession(authResponse.getData());
                }

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetLoginFailed(errorMessage);
            }
        });
    }

}