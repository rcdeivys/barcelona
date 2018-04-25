package com.BarcelonaSC.BarcelonaApp.ui.home.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.HomeApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.SetTokenToFirebaseIdRequest;
import com.google.gson.JsonObject;

/**
 * Created by Carlos on 01/11/2017.
 */

public class HomeModel {

    private static final String TAG = HomeModel.class.getSimpleName();
    private HomeApi homeApi;

    public HomeModel(HomeApi homeApi) {
        this.homeApi = homeApi;
    }

    public void sentFirebaseInstanceIdTokenToServer(String token, final HomeContract.ModelResultListener resultListener) {
        SetTokenToFirebaseIdRequest setTokenToFirebaseIdRequest=new SetTokenToFirebaseIdRequest(SessionManager.getInstance().getSession().getToken(),token);
        Log.i(TAG, "--->Token" + SessionManager.getInstance().getSession().getToken()+"\n TokenFirebaseIDmessage: "+token);
        Log.i(TAG, "--->" + homeApi.setTokenToFirebaseId(setTokenToFirebaseIdRequest).request().url());
        homeApi.setTokenToFirebaseId(setTokenToFirebaseIdRequest).enqueue(new NetworkCallBack<JsonObject>() {
            @Override
            public void onRequestSuccess(JsonObject response) {
                if (response != null) {
                    Log.i(TAG,"--->sentFirebaseInstanceIdTokenToServer() success");
                    resultListener.onSuccessSetFirebaseToken();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i(TAG,"--->sentFirebaseInstanceIdTokenToServer() failed");
                resultListener.onFailedSetFirebaseToken();
            }
        });
    }

}
