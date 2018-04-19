package com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.models.response.WallProfileResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.WallResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreateClapPost;

import java.util.ArrayList;
import java.util.List;

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

    public void clap(String id_post, final WallContract.ModelResultListener result, final WallAdapter.CommentListener commentListener) {
        api.createClapPost(new WallCreateClapPost(SessionManager.getInstance().getSession().getToken(), id_post)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    commentListener.onSuccessComment();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else if (!response.getStatus().equals("exito")) {
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

    public void reportarPost(WallReportarPost wallReportarPost, final WallContract.ModelResultListener result) {
        api.reportarPost(wallReportarPost).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("exito")) {
                    result.onWallPostFailed();
                } else {
                    result.onWallReportarPost();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onWallPostFailed();
            }
        });
    }

    public void profileWal(final int page, String id, final WallContract.ModelResultListener resultListener) {
        api.profileWall(id, page).enqueue(new NetworkCallBack<WallProfileResponse>() {
            @Override
            public void onRequestSuccess(WallProfileResponse response) {
                pagination = page > 1 ? true : false;
                List<Object> list = new ArrayList<>();
                if (!pagination)
                    list.add(response);
                Log.d("TSG", "probando " + response.getPost().size());
                list.addAll(response.getPost());
                resultListener.onWallProfile(list, pagination);
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                resultListener.onWallPostFailed();
            }
        });
    }

}
