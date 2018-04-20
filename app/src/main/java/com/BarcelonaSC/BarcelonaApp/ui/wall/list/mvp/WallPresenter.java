
package com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallPresenter implements WallContract.Presenter, WallContract.ModelResultListener {

    private static final String TAG = NewsPresenter.class.getSimpleName();
    private WallContract.View view;
    private WallModel model;

    private ArrayList<Object> listItems;

    public WallPresenter(WallContract.View view, WallModel model) {
        this.view = view;
        this.model = model;
        listItems = new ArrayList<>();
    }

    @Override
    public void onAttach(WallContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onWallPost(List<WallItem> list, boolean pagination) {
        if (isViewNull()) return;
        listItems.clear();
        if (!pagination) {
            listItems.add(SessionManager.getInstance().getUser());
        }
        listItems.addAll(list);
        view.setLoad(listItems, pagination);
    }

    @Override
    public void onWallDeletePost(String id_post) {
        if (isViewNull()) return;
    }

    @Override
    public void onWallReportarPost() {
        view.reportarPost();
    }

    @Override
    public void onWallPostFailed() {
        if (isViewNull()) return;
        view.showToastError();
    }

    @Override
    public void onWallProfile(List<Object> list, boolean pagination) {
        if (isViewNull()) return;
        listItems.clear();
        listItems.addAll(list);
        view.setLoad(listItems, pagination);
    }

    @Override
    public void load() {
        model.loadWall(0, this);
    }

    @Override
    public void loadProfile(String id) {
        model.profileWal(0, id, this);
    }

    @Override
    public void clap(String id_post, WallAdapter.CommentListener commentListener) {
        model.clap(id_post, this, commentListener);
    }

    @Override
    public void loadPage(int page) {
        model.loadWall(page, this);
    }

    @Override
    public void loadPageProfile(int page, String id) {
        model.profileWal(page, id, this);
    }

    @Override
    public void delete(String post) {
        model.delete(post, this);
    }

    @Override
    public void sendReportarPost(WallReportarPost wallReportarPost) {
        model.reportarPost(wallReportarPost, this);
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
