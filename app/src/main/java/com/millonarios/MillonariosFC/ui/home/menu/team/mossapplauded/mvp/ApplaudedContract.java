package com.millonarios.MillonariosFC.ui.home.menu.team.mossapplauded.mvp;


import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.MostApplaudedPlayerData;

/**
 * Created by Carlos on 14/10/2017.
 */

public class ApplaudedContract {

    public interface ModelResultListener {

        void onGetPlayerApplauseSuccess(MostApplaudedPlayerData mostApplaudedPlayer);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getPlayerApplause();

        String getAcumulatePlayer(int position);

        String getActualPlayer(int position);



        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void setApplauseData(MostApplaudedPlayerData mostApplaudedPlayer);

    }

}
