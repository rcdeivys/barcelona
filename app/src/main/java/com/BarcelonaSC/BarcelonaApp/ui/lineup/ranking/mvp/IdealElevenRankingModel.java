package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.LineUpApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.GameSummonedResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.IdealElevenRankingResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenRankingModel {

    private static final String TAG = IdealElevenRankingModel.class.getSimpleName();
    private LineUpApi lineUpApi;
    List<Object> list;

    public IdealElevenRankingModel(LineUpApi lineUpApi) {
        this.lineUpApi = lineUpApi;

    }

    public void getPlayByPlay(final IdealElevenRankingContract.ModelResultListener modelResultListener) {
        list = new ArrayList<>();
        lineUpApi.getGameSummoned().enqueue(new NetworkCallBack<GameSummonedResponse>() {
            @Override
            public void onRequestSuccess(GameSummonedResponse response) {
                if (response.getData() != null) {
                    list.add(response.getData());
                    getIdealElevenRanking(modelResultListener);
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                modelResultListener.onError(errorMessage);
            }
        });


    }


    public void getIdealElevenRanking(final IdealElevenRankingContract.ModelResultListener modelResultListener) {
        lineUpApi.getOnceIdealRanking(SessionManager.getInstance().getSession().getToken()).enqueue(new NetworkCallBack<IdealElevenRankingResponse>() {
            @Override
            public void onRequestSuccess(IdealElevenRankingResponse response) {
                Log.i(TAG, "--->getPlayByPlay onRequestSuccess");
                if (response.getData() != null) {
                    list.add(response.getData().getAlineacionOficial());
                    list.add(response.getData().getOnceIdealData());
                    list.addAll(response.getData().getRanking());
                    modelResultListener.onIdealElevenRankingData(list);
                } else {
                    modelResultListener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                Log.i(TAG, "--->getPlayByPlay onRequestFail");
                modelResultListener.onError(errorMessage);
            }
        });
    }
}
