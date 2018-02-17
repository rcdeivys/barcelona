package com.BarcelonaSC.BarcelonaApp.ui.news.mvp;


import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.NewsResponse;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.NewsFragment;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class NewsModel {

    private static final String TAG = NewsModel.class.getSimpleName();
    private NewsApi newsApi;

    public NewsModel(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    public void loadNewsProffessional(int page, final NewsContract.ModelResultListener result) {

        newsApi.getNewsProfessional(SessionManager.getInstance().getSession().getToken(), page).enqueue(new NetworkCallBack<NewsResponse>() {
            @Override
            public void onRequestSuccess(NewsResponse response) {
                result.onGetNewsSuccess(response.getData(), NewsFragment.NEWS_PROFESSIONAL);
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetNewsFailed();
            }
        });

    }

    public void loadNewsFootballBase(int page, final NewsContract.ModelResultListener result) {

        newsApi.getNewsFootballBase(page).enqueue(new NetworkCallBack<NewsResponse>() {
            @Override
            public void onRequestSuccess(NewsResponse response) {
                result.onGetNewsSuccess(response.getData(), NewsFragment.NEWS_FOOTBALL_BASE);
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetNewsFailed();
            }
        });

    }

}