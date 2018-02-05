
package com.millonarios.MillonariosFC.ui.wall.list.mvp;

import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.WallItem;
import com.millonarios.MillonariosFC.ui.news.mvp.NewsPresenter;

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
    public void onWallPostFailed() {
        if (isViewNull()) return;
        view.showToastError();
    }

    @Override
    public void load() {
        model.loadWall(0, this);
    }

    @Override
    public void clap(String id_post) {
        model.clap(id_post, this);
    }

    @Override
    public void loadPage(int page) {
        model.loadWall(page, this);
    }

    @Override
    public void delete(String post) {
        model.delete(post, this);
    }

    private boolean isViewNull() {
        return view == null;
    }
}
