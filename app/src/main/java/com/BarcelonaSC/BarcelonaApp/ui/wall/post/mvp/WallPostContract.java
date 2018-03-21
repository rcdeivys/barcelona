package com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallPostContract {


    public interface ModelResultListener {

        void onWallPostCreate();

        void onWallPostFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter<WallPostContract.View> {

        void sendPost(String message, String photo);

    }

    public interface View {

        void createPost();

        void showProgress();

        void hideProgress();

        void showToastError(String error);

    }
}
