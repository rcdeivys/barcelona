package com.millonarios.MillonariosFC.ui.wall.post.mvp;

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
    public void sendPost(String message, String photo) {
        if (isViewNull()) return;
        view.showProgress();
        if (!message.equals("") || photo != null) {
            model.sendPost(message, photo, this);
        } else {
            onWallPostFailed("Para crear una publicacion necesita introducir un texto o una imagen");
        }
    }

    @Override
    public void onWallPostCreate() {
        if (isViewNull()) return;
        view.createPost();
        view.hideProgress();
    }

    @Override
    public void onWallPostFailed(String error) {
        if (isViewNull()) return;
        view.showToastError(error);
        view.hideProgress();
    }

    private boolean isViewNull() {
        return view == null;
    }
}
