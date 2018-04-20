package com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.BaseModelResultListener;
import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;

import java.util.List;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallContract {

    public interface ModelResultListener extends BaseModelResultListener {

        void onWallPost(List<WallItem> list, boolean pagination);

        void onWallDeletePost(String id_post);

        void onWallReportarPost();

        void onWallPostFailed();

        void onWallProfile(List<Object> list, boolean pagination);
    }

    public interface Presenter extends MVPContract.Presenter<WallContract.View> {

        void load();

        void loadProfile(String id);

        void clap(String id_post, WallAdapter.CommentListener commentListener);

        void loadPage(int page);

        void loadPageProfile(int page, String id);

        void delete(String post);

        void sendReportarPost(WallReportarPost wallReportarPost);

    }

    public interface View extends BaseView {

        void setLoad(List<Object> list, boolean pagination);

        void showProgress();

        void hideProgress();

        void showToastError();

        void reportarPost();

    }
}
