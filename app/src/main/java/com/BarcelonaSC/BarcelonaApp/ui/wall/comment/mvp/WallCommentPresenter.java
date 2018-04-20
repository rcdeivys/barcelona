package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp;

import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentPresenter implements WallCommentContract.Presenter, WallCommentContract.ModelResultListener {

    private static final String TAG = NewsPresenter.class.getSimpleName();
    private WallCommentContract.View view;
    private WallCommentModel model;

    private ArrayList<Object> listItems;

    boolean refreshing = false;

    public WallCommentPresenter(WallCommentContract.View view, WallCommentModel model) {
        this.view = view;
        this.model = model;
        listItems = new ArrayList<>();
    }

    @Override
    public void onAttach(WallCommentContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onWallCommentPost(List<WallCommentItem> list) {
        if (isViewNull()) return;
        view.setLoad(list);
        view.hideProgress();
        view.setRefreshing(refreshing);
    }

    @Override
    public void onWallCommentCreate() {
        if (isViewNull()) return;
        view.createComment();
        view.hideProgress();
    }

    @Override
    public void onWallCommentEdit() {
        if (isViewNull()) return;
        view.hideProgress();
        view.editComment();
    }

    @Override
    public void onWallCommentDelete() {
        if (isViewNull()) return;
        view.hideProgress();
        view.deleteComment("");
    }

    @Override
    public void onWallFailed(String error) {
        if (isViewNull()) return;
        view.hideProgress();
        view.showToastError(error);
    }

    @Override
    public void onWallDeletePost(String msg) {
        if (isViewNull()) return;
        view.hideProgress();
        view.deletePost("");
    }

    @Override
    public void onWallLikePost(String msg) {
        if (isViewNull()) return;
        view.hideProgress();
        view.setLike();
    }

    @Override
    public void onWallReportarPost() {
        view.reportarPost();
    }

    @Override
    public void load(String id_post, boolean refreshing) {
        model.loadComment(id_post, 1, this);
    }

    @Override
    public void load(String id_post, int page, boolean refreshing) {
        model.loadComment(id_post, page, this);
    }

    @Override
    public void deletePost(String id) {
        model.delete(id, this);
    }

    @Override
    public void clapPost(String id, final WallAdapter.CommentListener commentListener) {
        model.clap(id, this, commentListener);
    }

    @Override
    public void createComment(String id_post, String comment, String photo) {
        if (isViewNull()) return;
        view.showProgress();
        if (!comment.equals("") || photo != null) {
            model.createComment(id_post, comment, photo, this);
        } else {
            onWallFailed("Para enviar un comentario necesitas introducir un texto o una imagen");
        }
    }

    @Override
    public void clapComment(String idcomment, WallCommentAdapter.CommentLikeListener commentLikeListener) {
        model.clapComment(idcomment, this, commentLikeListener);
    }

    @Override
    public void loadPage(String id_post, int page) {
        model.loadComment(id_post, page, this);
    }

    @Override
    public void deleteComment(String idpost, WallCommentItem wallItem, WallCommentAdapter.CommentDeleteListener commentDeleteListener) {
        model.deleteComment(idpost, wallItem.getIdcomentario(), this, commentDeleteListener);
    }

    @Override
    public void editComment(String id_post, String idcomment, String comment, String photo) {
        view.showProgress();
        model.editComment(id_post, idcomment, comment, photo, this);
    }

    @Override
    public void sendReportarPost(WallReportarPost wallReportarPost) {
        model.reportarPost(wallReportarPost, this);
    }

    private boolean isViewNull() {
        return view == null;
    }

    @Override
    public void noDoradoErrorListener() {
        view.hideProgress();
        view.setRefreshing(false);
        view.showDialogDorado();
    }
}
