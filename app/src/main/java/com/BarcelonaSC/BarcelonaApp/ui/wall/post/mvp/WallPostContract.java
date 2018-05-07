package com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.BaseModelResultListener;
import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallPostContract {


    public interface ModelResultListener extends BaseModelResultListener {

        void onWallPostCreate();

        void onWallPostEdit();

        void onWallPostFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter<WallPostContract.View> {

        void sendPost(String message, String tipo_post, String photo, String thumbnail);

        void sendVideoPost(String message, String post_type, String video, String thumbnail);

        void editVideoPost(String message, String post_type, String video, String thumbnail);

        void editPost(String idPost, String message, String tipo_post, String photo, String thumbnail);

    }

    public interface View extends BaseView {

        void createPost();

        void editPost();

        void showProgress();

        void hideProgress();

        void showToastError(String error);

    }
}
