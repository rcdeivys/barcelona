package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.NewsResponse;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalNewsModel {

    private static final String TAG = MonumentalNewsModel.class.getSimpleName();
    private MonumentalApi newsApi;

    public MonumentalNewsModel(MonumentalApi newsApi) {
        this.newsApi = newsApi;
    }

    public void loadMonumentalNews(int page, final MonumentalNewsContract.ModelResultListener result) {
        newsApi.getMonumentalNews(page).enqueue(new NetworkCallBack<NewsResponse>() {
            @Override
            public void onRequestSuccess(NewsResponse response) {
                result.onGetMNewsSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMNewsFailed();
            }
        });
    }

}