package com.BarcelonaSC.BarcelonaApp.ui.news.mvp;


import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class NewsPresenter implements NewsContract.Presenter, NewsContract.ModelResultListener {

    private static final String TAG = NewsPresenter.class.getSimpleName();
    private NewsContract.View view;
    private NewsModel newsModel;

    private ArrayList<News> newsList;

    public NewsPresenter(NewsContract.View newsContract, NewsModel newsModel) {
        this.view = newsContract;
        this.newsModel = newsModel;
        newsList = new ArrayList<>();
    }


    @Override
    public void onGetNewsSuccess(List<News> news, String category) {
        if (isViewNull()) return;

        for (News newss : news)
            newsList.add(newss);
        if (category.equals(NewsFragment.NEWS_PROFESSIONAL))
            view.setNewsProfessional(news);
        else
            view.setNewsFootballBase(news);

        view.hideProgress();

    }

    @Override
    public void onGetNewsFailed() {
        if (isViewNull()) return;
        view.showToastError();
        view.hideProgress();
    }


    @Override
    public void onAttach(NewsContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void loadNews(String category) {
        if (view == null) return;
        newsList.clear();
        if (category.equals(NewsFragment.NEWS_PROFESSIONAL)) {
            newsModel.loadNewsProffessional(1, this);
        } else {
            newsModel.loadNewsFootballBase(1, this);
        }

        view.showProgress();

    }

    @Override
    public void loadNewsPage(int page, String category) {
        if (category.equals(NewsFragment.NEWS_PROFESSIONAL)) {
            newsModel.loadNewsProffessional(page, this);
        } else {
            newsModel.loadNewsFootballBase(page, this);
        }
    }

    @Override
    public void onclickNewsItem(News news) {
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