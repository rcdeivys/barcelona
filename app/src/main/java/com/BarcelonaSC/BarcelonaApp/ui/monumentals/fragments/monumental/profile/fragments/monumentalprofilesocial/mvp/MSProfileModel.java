package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.VotesMonumental;
import com.BarcelonaSC.BarcelonaApp.models.response.MonumentalResponse;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfileContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfileModel;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MSProfileModel implements MSProfileContract.Presenter {

    private static final String TAG = MProfileModel.class.getSimpleName();
    private MonumentalApi api;

    private boolean isCanceled = false;

    public MSProfileModel(MonumentalApi api) {
        this.api = api;
    }

    public void loadMonumental(String id, final MSProfileContract.ModelResultListener result) {
        api.getMonumentals(id).enqueue(new NetworkCallBack<MonumentalResponse>() {
            @Override
            public void onRequestSuccess(MonumentalResponse response) {
                result.onGetMonumentalSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMonumentalFailed();
            }
        });
    }

    public void voteMonumental(String idMonumental, String idEncuesta, String emei, final MSProfileContract.ModelResultListener result) {
        reset();
        VotesMonumental votesMonumental = new VotesMonumental(Integer.parseInt(idEncuesta), Integer.parseInt(idMonumental), emei);
        api.getVote(votesMonumental).enqueue(new NetworkCallBack<MonumentalResponse>() {
            @Override
            public void onRequestSuccess(MonumentalResponse response) {
                if (!isCanceled) {
                    result.onGetMonumentalSuccess(response.getData());
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetMonumentalFailed();
            }
        });
    }

    @Override
    public void onAttach(MSProfileContract.View view) {

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