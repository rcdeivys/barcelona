package com.BarcelonaSC.BarcelonaApp.utils;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.UserPhotoResponse;

/**
 * Created by Carlos on 05/02/2018.
 */

public class PhotoUpload {


    public static void uploadFoto(Long id, final PhotoListener photoListener) {

        App.get().component().userPhotoApi().getPhoto(id).enqueue(new NetworkCallBack<UserPhotoResponse>() {
            @Override
            public void onRequestSuccess(UserPhotoResponse response) {
                if (response.getData() != null) {
                    photoListener.onPhotoSucces(response.getData().getFoto());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i("", "*/*--->");
            }
        });
    }

    public interface PhotoListener {
        void onPhotoSucces(String foto);

        void onError();
    }
}
