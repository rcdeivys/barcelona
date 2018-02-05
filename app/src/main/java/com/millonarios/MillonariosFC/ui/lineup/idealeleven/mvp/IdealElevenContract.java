package com.millonarios.MillonariosFC.ui.lineup.idealeleven.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.GameSummonedData;
import com.millonarios.MillonariosFC.models.IdealElevenData;
import com.millonarios.MillonariosFC.models.response.SendMyIdealElevenData;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class IdealElevenContract {


    public interface ModelResultListener {

        void onSetIdealElevenSuccess();

        void onGetIdealElevenSuccess(SendMyIdealElevenData myIdealElevenData);

        void onGetGameSummonedSuccess(GameSummonedData data);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {


        boolean isViewNull();

        void onClickItem(int position);

        void onClickHeader();
    }

    public interface View {


        void showShareDialog(String message);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setIdealEleven(List<IdealElevenData> idealElevenDatas);

        void setPlayByPlayData(GameSummonedData playByPlayData);

        void navigateToProfilePlayerActivity(String playerId);

    }
}
