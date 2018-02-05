package com.millonarios.MillonariosFC.ui.wall.list.mvp;

import com.millonarios.MillonariosFC.app.api.WallApi;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.WallResponse;
import com.millonarios.MillonariosFC.ui.wall.GenericResponse;
import com.millonarios.MillonariosFC.ui.wall.post.WallCreateClapPost;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallModel {

    private static final String TAG = WallModel.class.getSimpleName();
    private WallApi api;
    private WallContract.ModelResultListener resultListener;
    private boolean pagination = false;

    public WallModel(WallApi api) {
        this.api = api;
    }

    public void loadWall(int page, final WallContract.ModelResultListener result) {
        this.resultListener = result;
        pagination = page > 1 ? true : false;
        api.getWall(SessionManager.getInstance().getSession().getToken(), page).enqueue(new NetworkCallBack<WallResponse>() {
            @Override
            public void onRequestSuccess(WallResponse response) {
                if (response.getStatus().equals("exito")) {
                    resultListener.onWallPost(response.getWallItemList(), pagination);
                } else {
                    resultListener.onWallPostFailed();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onWallPostFailed();
            }
        });
    }

    public void clap(String id_post, final WallContract.ModelResultListener result) {
        api.createClapPost(new WallCreateClapPost(SessionManager.getInstance().getSession().getToken(), id_post)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("exito")) {
                    resultListener.onWallPostFailed();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onWallPostFailed();
            }
        });
    }

    public void delete(String id_post, final WallContract.ModelResultListener result) {
        api.detelePost(id_post, SessionManager.getInstance().getSession().getToken()).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("exito")) {
                    resultListener.onWallPostFailed();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onWallPostFailed();
            }
        });
    }


}
