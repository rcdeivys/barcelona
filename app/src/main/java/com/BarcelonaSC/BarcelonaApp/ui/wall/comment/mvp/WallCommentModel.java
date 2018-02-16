package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.WallCommentApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.WallCommentResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentClap;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentCreate;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentModel extends NetworkCallBack<WallCommentResponse> {
    private static final String TAG = WallCommentModel.class.getSimpleName();
    private WallCommentApi api;

    WallCommentContract.ModelResultListener result;

    public WallCommentModel(WallCommentApi api) {
        this.api = api;
    }

    public void loadComment(String id_post, int page, WallCommentContract.ModelResultListener result) {
        this.result = result;
        api.getWallComment(id_post, SessionManager.getInstance().getSession().getToken(), page).enqueue(this);
    }

    @Override
    public void onRequestSuccess(WallCommentResponse response) {
        if (response.getStatus().equals("exito")) {
            result.onWallCommentPost(response.getWallCommentItemList());
        }
    }

    @Override
    public void onRequestFail(String errorMessage, int errorCode) {
        result.onWallCommentFailed(errorMessage);
    }

    public void createComment(String id_post, String comment, String photo, final WallCommentContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(comment);
        api.createComment(new WallCommentCreate(SessionManager.getInstance().getSession().getToken(), id_post, toServerUnicodeEncoded, photo)).enqueue(new NetworkCallBack<GenericResponse>() {

            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallCommentCreate();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallCommentFailed(errorMessage);
            }
        });
    }

    public void clapComment(String id_comment, final WallCommentContract.ModelResultListener result) {
        api.clapComment(new WallCommentClap(SessionManager.getInstance().getSession().getToken(), id_comment)).enqueue(new NetworkCallBack<GenericResponse>() {

            @Override
            public void onRequestSuccess(GenericResponse response) {

            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallCommentFailed(errorMessage);
            }
        });
    }
}
