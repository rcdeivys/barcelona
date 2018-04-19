package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp;

import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;

import java.util.ArrayList;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class SinglePostPresenter implements SinglePostContract.Presenter, SinglePostContract.ModelResultListener {

    private static final String TAG = NewsPresenter.class.getSimpleName();
    private SinglePostContract.View view;
    private SinglePostModel model;

    private ArrayList<Object> listItems;

    boolean refreshing = false;

    public SinglePostPresenter(SinglePostContract.View view, SinglePostModel model) {
        this.view = view;
        this.model = model;
        listItems = new ArrayList<>();
    }

    @Override
    public void onAttach(SinglePostContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }


    @Override
    public void noDoradoErrorListener() {

    }

    @Override
    public void onWallCommentPost(WallItem item) {
        if (view != null)
            view.setLoad(item);
    }

    @Override
    public void onWallFailed(String error) {

    }

    @Override
    public void load(String id_post) {
        model.loadComment(id_post, 0, this);
    }
}
