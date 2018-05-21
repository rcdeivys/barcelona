package com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp;

import android.util.Log;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.app.api.YouChooseApi;
import com.losingtimeapps.whitebrand.app.network.NetworkCallBack;
import com.losingtimeapps.whitebrand.models.SendChooseVote;
import com.losingtimeapps.whitebrand.models.response.ChooseEncuestaResponse;
import com.losingtimeapps.whitebrand.models.response.SendChooseVoteResponse;


/**
 * Created by Carlos on 14/10/2017.
 */

public class VoteModel {

    public static final String TAG = VoteModel.class.getSimpleName();
    private YouChooseApi youChooseApi;

    public VoteModel(YouChooseApi youChooseApi) {
        this.youChooseApi = youChooseApi;
    }

    public void getChooseData(final VoteContract.ModelResultListener listener) {
        youChooseApi.getEncuestas(WhiteBrand.getUserToken()).enqueue(new NetworkCallBack<ChooseEncuestaResponse>() {
            @Override
            public void onRequestSuccess(ChooseEncuestaResponse response) {
                if (response.getData() != null) {
                    listener.onGetChooseSuccess(response.getData());
                } else
                    listener.onGetChooseFailed();
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });

    }


    public void setPlayersVotes(final SendChooseVote sendChooseVote, final VoteContract.ModelResultListener listener, final int msj) {
        sendChooseVote.setToken(WhiteBrand.getUserToken());
        Log.i(TAG, "/--->" + sendChooseVote.toString());
        youChooseApi.sendVote(sendChooseVote).enqueue(new NetworkCallBack<SendChooseVoteResponse>() {
            @Override
            public void onRequestSuccess(SendChooseVoteResponse response) {
                if (response.getData() != null)
                    listener.onsetVotesSuccess(sendChooseVote.getIdRespuesta(),msj);
                else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }
}
