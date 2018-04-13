package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp;


import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfileContract {

    public interface ModelResultListener {

        void onGetPlayerSuccess(PlayerData player);

        void onGetPlayerApplauseSuccess(ApplauseData applauseData);

        void onSetPlayerApplauseSuccess(String id, int aplaudio);

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

        void showShareApplause(final String id);

    }
}
