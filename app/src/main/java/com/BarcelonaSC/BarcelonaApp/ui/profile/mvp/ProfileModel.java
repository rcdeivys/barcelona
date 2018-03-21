package com.BarcelonaSC.BarcelonaApp.ui.profile.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;

/**
 * Created by Leonardojpr on 11/10/17.
 */

public class ProfileModel {

    private static final String TAG = ProfileModel.class.getSimpleName();
    private ProfileApi profileApi;

    public ProfileModel(ProfileApi profileApi) {
        this.profileApi = profileApi;
    }

    SessionManager sessionManager;

    public void loadUser(final String token, final ProfileContract.ModelResultListener result) {
        AuthItem authItem = new AuthItem();
        authItem.setToken(token);
        profileApi.get(token).enqueue(new NetworkCallBack<UserResponse>() {
            @Override
            public void onRequestSuccess(UserResponse response) {
                if (!response.getStatus().equals("fallo"))
                    result.onGetUserSuccess(response.getData());
                else
                    result.onFailed("Ocurrió un error, vuelta a intentarlo.");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }

    public void updateUser(final User user, String token, final ProfileContract.ModelResultListener result) {
        profileApi.update(token, user).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("fallo"))
                    result.onPutUserSuccess();
                else
                    result.onFailed("Ocurrió un error, vuelta a intentarlo.");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }
}
