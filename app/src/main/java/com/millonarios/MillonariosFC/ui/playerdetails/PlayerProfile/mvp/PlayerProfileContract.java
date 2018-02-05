package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp;


import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.ApplauseData;
import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.models.PlayerData;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfileContract {


    public interface ModelResultListener {
        void onGetPlayerSuccess(PlayerData player);

        void onGetPlayerApplauseSuccess(ApplauseData applauseData);

        void onSetPlayerApplauseSuccess();

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getPlayer(String playerId);

        void getPlayer();

        void clickItem(int position);

        void getPlayerApplause();

        void setPlayerApplause();

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        String getImei();

        void setRefreshing(boolean state);

        void showToast(String error);

        void setPlayerData(PlayerData player);

        void setPlayerApplause(ApplauseData applauseData);
        void navigateToVideoNewsActivity(News news);
        void navigateToInfografiaActivity(News news);
        void navigateToNewsDetailsActivity(News news);
        void navigateToGalleryActivity(int id);

    }

}
