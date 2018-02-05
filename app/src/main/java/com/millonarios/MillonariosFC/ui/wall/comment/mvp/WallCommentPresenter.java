package com.millonarios.MillonariosFC.ui.wall.comment.mvp;

import com.millonarios.MillonariosFC.models.WallCommentItem;
import com.millonarios.MillonariosFC.ui.news.mvp.NewsPresenter;

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
    public void onWallCommentFailed(String error) {
        if (isViewNull()) return;
        view.hideProgress();
        view.showToastError(error);
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
    public void createComment(String id_post, String comment, String photo) {
        if (isViewNull()) return;
        view.showProgress();
        if (!comment.equals("") || photo != null) {
            model.createComment(id_post, comment, photo, this);
        } else {
            onWallCommentFailed("Para enviar un comentario necesita introducir un texto o una imagen");
        }
    }

    @Override
    public void clapComment(String idcomment) {
        model.clapComment(idcomment, this);
    }

    @Override
    public void loadPage(String id_post, int page) {
        model.loadComment(id_post, page, this);
    }

    private boolean isViewNull() {
        return view == null;
    }
}
