package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenRankingContract {


    public interface ModelResultListener {


        void onIdealElevenRankingData(List<Object> data);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

       void getIdealElevenRankingData();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void showIdealElevenRankingData(List<Object> ranking);

    }
}
