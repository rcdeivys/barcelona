package com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;

import java.util.List;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallContract {

    public interface ModelResultListener {

        void onWallPost(List<WallItem> list,  boolean pagination);

        void onWallDeletePost(String id_post);

        void onWallPostFailed();
    }

    public interface Presenter extends MVPContract.Presenter<WallContract.View> {

        void load();

        void clap(String id_post);

        void loadPage(int page);

        void delete(String post);

    }

    public interface View {

        void setLoad(List<Object> list, boolean pagination);

        void showProgress();

        void hideProgress();

        void showToastError();

    }
}
