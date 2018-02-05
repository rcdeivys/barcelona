package com.millonarios.MillonariosFC.utils;

import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.UserPhotoResponse;

import de.hdodenhof.circleimageview.CircleImageView;

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
