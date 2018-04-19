package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.BaseModelResultListener;
import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class SinglePostContract {

    public interface ModelResultListener extends BaseModelResultListener {

        void onWallCommentPost(WallItem list);

        void onWallFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter<SinglePostContract.View> {

        void load(String id_post);

    }

    public interface View extends BaseView {

        void setLoad(WallItem wallItem);

    }
}
