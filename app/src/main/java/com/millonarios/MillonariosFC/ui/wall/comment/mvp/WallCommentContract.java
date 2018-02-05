package com.millonarios.MillonariosFC.ui.wall.comment.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.WallCommentItem;

import java.util.List;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentContract {

    public interface ModelResultListener {

        void onWallCommentPost(List<WallCommentItem> list);

        void onWallCommentCreate();

        void onWallCommentFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter<WallCommentContract.View> {

        void load(String id_post, boolean refreshing);

        void load(String id_post, int page, boolean refreshing);

        void createComment(String id_post, String comment, String photo);

        void clapComment(String idcomment);

        void loadPage(String id_post ,int page);

    }

    public interface View {

        void setLoad(List<WallCommentItem> list);

        void createComment();

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

    }
}
