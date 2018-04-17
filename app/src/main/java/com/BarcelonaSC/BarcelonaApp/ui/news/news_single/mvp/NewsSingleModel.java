package com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.NewsApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.NewsSingleResponse;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

public class NewsSingleModel {

    private NewsApi api;

    public NewsSingleModel(NewsApi api) {
        this.api = api;
    }

    public void getNewsSingle(String id, final NewsSingleContract.ModelResultListener result) {
        api.getNewsSingle(id).enqueue(new NetworkCallBack<NewsSingleResponse>() {
            @Override
            public void onRequestSuccess(NewsSingleResponse response) {
                result.onGetSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onError(errorMessage);
            }
        });
    }

}