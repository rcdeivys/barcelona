package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalPollResponse;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalModel {

    private static final String TAG = MonumentalModel.class.getSimpleName();
    private MonumentalApi api;

    public MonumentalModel(MonumentalApi api) {
        this.api = api;
    }

    public void loadMonumental(final MonumentalContract.ModelResultListener result) {
        api.getPollMonumental().enqueue(new NetworkCallBack<MonumentalPollResponse>() {
            @Override
            public void onRequestSuccess(MonumentalPollResponse response) {
                result.onGetMonumentalSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMonumentalFailed();
            }
        });
    }

}