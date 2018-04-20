package com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePost;

import org.apache.commons.lang3.StringEscapeUtils;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallPostModel {


    private static final String TAG = WallPostModel.class.getSimpleName();
    private WallApi api;

    public WallPostModel(WallApi api) {
        this.api = api;
    }

    public void sendPost(String message, String tipo_post, String photo, String thumbnail, final WallPostContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);
        api.createPost(new WallCreatePost(SessionManager.getInstance().getSession().getToken(), toServerUnicodeEncoded, tipo_post, photo, thumbnail)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallPostCreate();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else {
                    if (response.getError() != null)
                        result.onWallPostFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallPostFailed(errorMessage);
            }
        });
    }

    public void sendVideoPost(String message, String video, String tipo_post, String thumbnail, final WallPostContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);
        RequestBody token = RequestBody.create(MediaType.parse("text/plain"), SessionManager.getInstance().getSession().getToken());
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), tipo_post);
        RequestBody msg = RequestBody.create(MediaType.parse("text/plain"), toServerUnicodeEncoded);
        RequestBody foto = RequestBody.create(MediaType.parse("text/plain"), video);
        RequestBody thumb = RequestBody.create(MediaType.parse("text/plain"), thumbnail);


        api.createVideoPost(token, msg, type, foto, thumb).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallPostCreate();
                } else if (response.getStatus().equals("fallo")) {
                    if (response.getError() != null) {
                        result.onWallPostFailed(response.getError().get(0));
                    }
                } else if (response.getStatus().equals("limite_post")) {
                    result.onWallPostFailed(response.getError().get(0));
                } else {
                    result.onWallPostFailed(response.getStatus());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallPostFailed(errorMessage);
            }
        });
    }

    public void sendEdit(String idPost, String message, String tipo_post, String photo, String thumbnail, final WallPostContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);
        api.editPost(idPost, new WallCreatePost(SessionManager.getInstance().getSession().getToken(), toServerUnicodeEncoded, tipo_post, photo, thumbnail)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallPostCreate();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else {
                    if (response.getError() != null)
                        result.onWallPostFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallPostFailed(errorMessage);
            }
        });
    }
}