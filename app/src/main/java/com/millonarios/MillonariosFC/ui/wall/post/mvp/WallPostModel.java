package com.millonarios.MillonariosFC.ui.wall.post.mvp;

import com.millonarios.MillonariosFC.app.api.WallApi;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.ui.wall.GenericResponse;
import com.millonarios.MillonariosFC.ui.wall.post.WallCreatePost;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallPostModel {


    private static final String TAG = WallPostModel.class.getSimpleName();
    private WallApi api;

    public WallPostModel(WallApi api) {
        this.api = api;
    }

    public void sendPost(String message, String photo, final WallPostContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);
        api.createPost(new WallCreatePost(SessionManager.getInstance().getSession().getToken(), toServerUnicodeEncoded, photo)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallPostCreate();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallPostFailed(errorMessage);
            }
        });
    }

}
