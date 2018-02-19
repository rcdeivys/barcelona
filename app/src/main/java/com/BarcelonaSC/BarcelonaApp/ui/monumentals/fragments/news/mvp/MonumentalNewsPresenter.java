package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp;

import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalNewsPresenter implements MonumentalNewsContract.Presenter, MonumentalNewsContract.ModelResultListener {

    private static final String TAG = MonumentalNewsPresenter.class.getSimpleName();
    private MonumentalNewsContract.View view;
    private MonumentalNewsModel newsModel;

    private ArrayList<News> newsList;

    public MonumentalNewsPresenter(MonumentalNewsContract.View newsContract, MonumentalNewsModel newsModel) {
        this.view = newsContract;
        this.newsModel = newsModel;
        newsList = new ArrayList<>();
    }

    @Override
    public void onAttach(MonumentalNewsContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetMNewsSuccess(List<News> news) {
        if (isViewNull()) return;
        for (News newss : news) {
            newsList.add(newss);
            view.setMonumentalNews(news);
        }
        view.hideProgress();
    }

    @Override
    public void onGetMNewsFailed() {
        if (isViewNull()) return;
        view.showToastError();
        view.hideProgress();
    }

    @Override
    public void loadNews() {
        if (view == null) return;
        newsList.clear();
        newsModel.loadMonumentalNews(1, this);
        view.showProgress();
    }

    @Override
    public void loadNewsPage(int page) {
        newsModel.loadMonumentalNews(page, this);
    }

    @Override
    public void onClickNewsItem(News news) {
        if (isViewNull()) return;
        if (news.getTipo().matches(Constant.NewsType.GALERY)) {
            view.navigateToGalleryActivity(news);
        } else if (news.getTipo().matches(Constant.NewsType.VIDEO)) {
            view.navigateToVideoNewsActivity(news);
        } else if (news.getTipo().matches(Constant.NewsType.INFOGRAFY) || news.getTipo().matches(Constant.NewsType.STAT)) {
            view.navigateToInfografiaActivity(news);
        } else {
            view.navigateToNewsDetailsActivity(news);
        }
    }

    private boolean isViewNull() {
        return view == null;
    }

}