package com.BarcelonaSC.BarcelonaApp.utils.giphy.mvp;

import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyUtil;
import com.giphy.sdk.core.models.Media;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 15/03/2018.
 */

public class GiphyPresenter implements GiphyContract.Presenter, GiphyUtil.GiphyListener {


    GiphyUtil giphyUtil;
    GiphyContract.View view;
    String seach;
    List<Media> gifs;

    public GiphyPresenter() {
        giphyUtil = new GiphyUtil(this);
    }

    @Override
    public void onAttach(GiphyContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onGetTrendingGif(List<Media> gifs) {
        this.gifs.addAll(gifs);
        if (isViewNull()) return;

        view.onGetTrendingGif(this.gifs);
    }

    @Override
    public void onGetSeachGif(List<Media> gifs) {
        this.gifs.addAll(gifs);
        if (isViewNull()) return;
        view.onGetSeachGif(this.gifs);
    }

    @Override
    public void onError() {
        if (isViewNull()) return;

        view.hideProgress();
    }

    public void getTrendingGif() {
        gifs = new ArrayList<>();
        seach = "";
        if (isViewNull()) return;
        view.showProgress();
        giphyUtil.getTrendingGif();
    }


    public void seachGif(String s) {
        gifs = new ArrayList<>();
        seach = s;
        if (isViewNull()) return;
        view.showProgress();
        giphyUtil.seachGif(s);
    }

    public void getPaginationGif(int page) {
        if (seach.equals("")) {
            giphyUtil.getTrendingGif(page);
        } else {
            giphyUtil.seachGif(seach, page);
        }
    }

    private boolean isViewNull() {
        return view == null;
    }

}
