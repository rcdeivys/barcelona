package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalPoll;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalContract {

    public interface ModelResultListener {
        void onGetMonumentalSuccess(MonumentalPoll poll);

        void onGetMonumentalFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getMonumentals();

        boolean isViewNull();

        void onClickItem(int position);
    }

    public interface View {
        void setMonumentals(MonumentalPoll monumentals);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError();

        void navigateToMonumentalProfile(String monumentalId, String PollId);
    }

}