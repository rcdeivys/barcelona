package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.api.WallCommentApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.models.response.WallCommentResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.GenericResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentClap;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentCreate;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreateClapPost;

import org.apache.commons.lang3.StringEscapeUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentModel extends NetworkCallBack<WallCommentResponse> {
    private static final String TAG = WallCommentModel.class.getSimpleName();
    private WallCommentApi api;
    private WallApi wallApi;

    WallCommentContract.ModelResultListener result;

    public WallCommentModel(WallApi wallApi, WallCommentApi api) {
        this.api = api;
        this.wallApi = wallApi;
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
        result.onWallFailed(errorMessage);
    }

    public void createComment(String id_post, String comment, String photo, final WallCommentContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(comment);
        api.createComment(new WallCommentCreate(SessionManager.getInstance().getSession().getToken(), id_post, toServerUnicodeEncoded, photo)).enqueue(new NetworkCallBack<GenericResponse>() {

            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallCommentCreate();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else {
                    if (response.getError() != null)
                        result.onWallFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallFailed(errorMessage);
            }
        });
    }

    public void clapComment(String id_comment, final WallCommentContract.ModelResultListener result, final WallCommentAdapter.CommentLikeListener commentLikeListener) {
        api.clapComment(new WallCommentClap(SessionManager.getInstance().getSession().getToken(), id_comment)).enqueue(new NetworkCallBack<GenericResponse>() {

            @Override
            public void onRequestSuccess(GenericResponse response) {

                if ("exito".equals(response.getStatus())) {
                    commentLikeListener.onSuccessComment();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else {
                    if (response.getError() != null)
                        result.onWallFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallFailed(errorMessage);
            }
        });
    }

    public void clap(String id_post, final WallCommentContract.ModelResultListener result, final WallAdapter.CommentListener commentListener) {
        wallApi.createClapPost(new WallCreateClapPost(SessionManager.getInstance().getSession().getToken(), id_post)).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (response.getStatus().equals("exito")) {
                    result.onWallLikePost("");
                    commentListener.onSuccessComment();
                } else if ("no_dorado".equals(response.getStatus())) {
                    result.noDoradoErrorListener();
                } else if (!response.getStatus().equals("exito")) {
                    result.onWallFailed("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallFailed("");
            }
        });
    }

    public void delete(String id_post, final WallCommentContract.ModelResultListener result) {
        wallApi.detelePost(id_post, SessionManager.getInstance().getSession().getToken()).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("exito")) {
                    result.onWallFailed("");
                } else {
                    result.onWallDeletePost("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallFailed("");
            }
        });
    }

    public void deleteComment(String idpost, String idcomment, final WallCommentContract.ModelResultListener result, final WallCommentAdapter.CommentDeleteListener commentDeleteListener) {
        api.deleteComment(idpost, idcomment, SessionManager.getInstance().getSession().getToken()).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                result.onWallCommentDelete();
                commentDeleteListener.onSuccessDeleteListener();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    public void editComment(String idpost, String idcomment, String message, String foto, final WallCommentContract.ModelResultListener result) {
        String toServerUnicodeEncoded = StringEscapeUtils.escapeJava(message);
        api.editComment(idpost, idcomment, SessionManager.getInstance().getSession().getToken(), new WallCommentCreate(null, null, toServerUnicodeEncoded, null)).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                result.onWallCommentEdit();
                // commentEditListener.onSuccessEdit();
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {

            }
        });
    }

    public void reportarPost(WallReportarPost wallReportarPost, final WallCommentContract.ModelResultListener result) {
        wallApi.reportarPost(wallReportarPost).enqueue(new NetworkCallBack<GenericResponse>() {
            @Override
            public void onRequestSuccess(GenericResponse response) {
                if (!response.getStatus().equals("exito")) {
                    result.onWallFailed(response.getError().get(0));
                } else {
                    result.onWallReportarPost();
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onWallFailed(errorMessage);
            }
        });
    }

}
