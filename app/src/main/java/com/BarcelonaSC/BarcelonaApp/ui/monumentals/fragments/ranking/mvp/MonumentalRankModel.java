package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalRankingResponse;

/**
 * Created by RYA-Laptop on 18/02/2018.
 */

public class MonumentalRankModel implements MonumentalRankContract.Presenter {

    private static final String TAG = MonumentalRankModel.class.getSimpleName();
    private MonumentalApi api;

    private boolean isCanceled = false;

    public MonumentalRankModel(MonumentalApi api) {
        this.api = api;
    }

    public void loadMonumentalRank(final MonumentalRankContract.ModelResultListener result) {
        reset();
        api.getRanking().enqueue(new NetworkCallBack<MonumentalRankingResponse>() {
            @Override
            public void onRequestSuccess(MonumentalRankingResponse response) {
                if (!isCanceled) {
                    result.onGetMRankSuccess(response.getData());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMRankFailed();
            }
        });
    }

    @Override
    public void loadMonumentalRank() {

    }

    @Override
    public void cancel() {
        this.isCanceled = true;
    }

    @Override
    public void reset() {
        this.isCanceled = false;
    }

    @Override
    public void onAttach(MonumentalRankContract.View view) {

    }

    @Override
    public void onDetach() {

    }
}