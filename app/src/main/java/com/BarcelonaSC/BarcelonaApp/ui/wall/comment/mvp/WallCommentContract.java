package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.BaseModelResultListener;
import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;

import java.util.List;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentContract {

    public interface ModelResultListener extends BaseModelResultListener {

        void onWallCommentPost(List<WallCommentItem> list);

        void onWallCommentCreate();

        void onWallCommentEdit();

        void onWallCommentDelete();

        void onWallFailed(String error);

        void onWallDeletePost(String msg);

        void onWallLikePost(String msg);

        void onWallReportarPost();

        void onWallReportarComment();
    }

    public interface Presenter extends MVPContract.Presenter<WallCommentContract.View> {

        void load(String id_post, boolean refreshing);

        void load(String id_post, int page, boolean refreshing);

        void deletePost(String id);

        void clapPost(String id, final WallAdapter.CommentListener commentListener);

        void createComment(String id_post, String comment, String photo);

        void clapComment(String idcomment, WallCommentAdapter.CommentLikeListener commentLikeListener);

        void loadPage(String id_post, int page);

        void deleteComment(String idpost, WallCommentItem wallItem, WallCommentAdapter.CommentDeleteListener commentDeleteListener);

        void editComment(String id_post, String idcomentario, String comment, String photo);

        void sendReportarPost(WallReportarPost wallReportarPost);

        void sendReportarComment(WallReportarPost wallReportarPost);

    }

    public interface View extends BaseView {

        void setLoad(List<WallCommentItem> list);

        void deletePost(String msg);

        void deleteComment(String msg);

        void setLike();

        void createComment();

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void editComment();

        void reportarPost();

        void reportarComment();

    }
}
