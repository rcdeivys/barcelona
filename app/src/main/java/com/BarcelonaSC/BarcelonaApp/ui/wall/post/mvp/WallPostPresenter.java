package com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallPostPresenter implements WallPostContract.Presenter, WallPostContract.ModelResultListener {

    private static final String TAG = WallPostPresenter.class.getSimpleName();
    private WallPostContract.View view;
    private WallPostModel model;

    public WallPostPresenter(WallPostContract.View view, WallPostModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(WallPostContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void sendPost(String message, String tipo_post, String photo, String thumbnail) {
        if (isViewNull()) return;
        view.showProgress();
        if (!message.equals("") || photo != null) {
            model.sendPost(message, tipo_post, photo, thumbnail, this);
        } else {
            onWallPostFailed("Para crear una publicación necesitas introducir un texto, imagen o vídeo");
        }
    }

    @Override
    public void sendVideoPost(String message, String post_type, String video, String thumbnail) {
        if (isViewNull()) return;
        view.showProgress();
        if (!message.equals("") || !post_type.equals("video") || video != null) {
            model.sendVideoPost(message, video, post_type, thumbnail,this);
        } else {
            onWallPostFailed("Para crear una publicación necesitas introducir un texto, imagen o vídeo");
        }
    }

    @Override
    public void editVideoPost(String message, String post_type, String video, String thumbnail) {
        if (isViewNull()) return;
        view.showProgress();
        if (!message.equals("") || !post_type.equals("video") || video != null) {
            model.sendVideoPost(message, video, post_type, thumbnail, this);
        } else {
            onWallPostFailed("Para crear una publicación necesitas introducir un texto, imagen o vídeo");
        }
    }

    @Override
    public void editPost(String idpost, String message, String tipo_post, String photo, String thumbnail) {
        if (isViewNull()) return;
        view.showProgress();
        if (!message.equals("") || photo != null) {
            model.sendEdit(idpost, message, tipo_post, photo, thumbnail,this);
        } else {
            onWallPostFailed("Para editar una publicación necesitas introducir un texto, imagen o vídeo");
        }
    }

    @Override
    public void onWallPostCreate() {
        if (isViewNull()) return;
        view.createPost();
        view.hideProgress();
    }

    @Override
    public void onWallPostEdit() {

    }

    @Override
    public void onWallPostFailed(String error) {
        if (isViewNull()) return;
        view.hideProgress();
        view.showToastError(error);
    }

    private boolean isViewNull() {
        return view == null;
    }

    @Override
    public void noDoradoErrorListener() {
        view.hideProgress();
        view.showDialogDorado();
    }

}