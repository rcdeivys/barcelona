package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.VotesMonumental;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalResponse;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MProfileModel implements MProfileContract.Presenter {

    private static final String TAG = MProfileModel.class.getSimpleName();
    private MonumentalApi api;

    private boolean isCanceled = false;

    public MProfileModel(MonumentalApi api) {
        this.api = api;
    }

    public void loadMonumental(String id, final MProfileContract.ModelResultListener result) {
        api.getMonumentals(id).enqueue(new NetworkCallBack<MonumentalResponse>() {
            @Override
            public void onRequestSuccess(MonumentalResponse response) {
                if (response.getStatus().equals("exito"))
                    result.onGetMonumentalSuccess(response.getData());
                else
                    result.onGetMonumentalFailed(response.getError().get(0));
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMonumentalFailed(errorMessage);
            }
        });
    }

    public void voteMonumental(String idMonumental, String idEncuesta, String emei, final MProfileContract.ModelResultListener result) {
        reset();
        VotesMonumental votesMonumental = new VotesMonumental(Integer.parseInt(idEncuesta), Integer.parseInt(idMonumental), emei);
        api.getVote(votesMonumental).enqueue(new NetworkCallBack<MonumentalResponse>() {
            @Override
            public void onRequestSuccess(MonumentalResponse response) {
                if (!isCanceled) {
                    if (response.getStatus().equals("exito"))
                        result.onGetMonumentalVoteSuccess();
                    else
                        result.onGetMonumentalFailed(response.getError().get(0));
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMonumentalFailed(errorMessage);
            }
        });
    }

    @Override
    public void onAttach(MProfileContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void loadMonumental(String id) {

    }

    @Override
    public void voteMonumental(String idMonumental, String idEncuesta, String emei) {

    }

    @Override
    public void clickItem(News news) {

    }

    @Override
    public void cancel() {
        this.isCanceled = true;
    }

    @Override
    public void reset() {
        this.isCanceled = false;
    }

}