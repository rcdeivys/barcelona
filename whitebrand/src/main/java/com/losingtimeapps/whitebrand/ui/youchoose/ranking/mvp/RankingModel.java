package com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp;


import com.losingtimeapps.whitebrand.app.api.YouChooseApi;
import com.losingtimeapps.whitebrand.app.network.NetworkCallBack;
import com.losingtimeapps.whitebrand.models.response.ChooseEncuestaResponse;
import com.losingtimeapps.whitebrand.models.response.ChooseRankingResponse;

/**
 * Created by Carlos on 14/10/2017.
 */

public class RankingModel {

    public static final String TAG = RankingModel.class.getSimpleName();
    private YouChooseApi youChooseApi;

    public RankingModel(YouChooseApi youChooseApi) {
        this.youChooseApi = youChooseApi;
    }

    public void getPlayersVotes(int idEncuesta, final RankingContract.ModelResultListener listener) {
        youChooseApi.getChooseRanking(idEncuesta).enqueue(new NetworkCallBack<ChooseRankingResponse>() {
            @Override
            public void onRequestSuccess(ChooseRankingResponse response) {
                if (response.getRespuestas() != null) {
                    listener.onGetRankingVotesSuccess(response.getRespuestas());
                } else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });

    }

    public void getRanking(final RankingContract.ModelResultListener listener, final boolean show) {
    /*    youChooseApi.getEncuestas(SessionManager.getInstance().getSession().getToken()).enqueue(new NetworkCallBack<ChooseEncuestaResponse>() {
            @Override
            public void onRequestSuccess(ChooseEncuestaResponse response) {
                if (response.getData() != null) {
                    if (response.getData().getPuedevervotos() == 1 || show)
                        getPlayersVotes(response.getData().getIdencuesta(), listener);
                    else {
                        listener.noShowVotes();
                    }
                } else
                    listener.onError("");
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });*/

    }

}
