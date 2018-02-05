package com.millonarios.MillonariosFC.ui.home.menu.team.players.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.NominaItem;
import com.millonarios.MillonariosFC.models.GameSummonedData;

import java.util.List;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerContract {


    public interface ModelResultListener {
        void onGetPlayerOff(List<NominaItem> player);

        void onGetGameSummoned(GameSummonedData gameSummonedData);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getPlayeroff();

        void getGameSummoned();

        boolean isViewNull();

        void onClickItem(int position);

        void onClickHeader();
    }

    public interface View {
        void setPlayer(List<NominaItem> player);

        void setPlayerWithHeader(GameSummonedData gameSummoned);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void navigateToPlayerActivity(int playerId, String type);

        void navigateToLineUp(String calendaryId);
    }
}
