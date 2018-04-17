package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp;


import com.BarcelonaSC.BarcelonaApp.commons.BaseModelResultListener;
import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfileContract {

    public interface ModelResultListener {

    public interface ModelResultListener extends BaseModelResultListener {
        void onGetPlayerSuccess(PlayerData player);

        void onGetPlayerApplauseSuccess(ApplauseData applauseData);

        void onSetPlayerApplauseSuccess(String id, int aplaudio);

        void onError(String error);

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getPlayer(String playerId);

        void getPlayer();

        void clickItem(News news);

        void getPlayerApplause();

        void setPlayerApplause();

        boolean isViewNull();

    }

    public interface View extends BaseView {

        void showProgress();

        void hideProgress();

        String getImei();

        void setRefreshing(boolean state);

        void showToast(String error);

        void showShareApplause(final String id);

        void setPlayerData(PlayerData player);

        void setPlayerApplause(ApplauseData applauseData);

        void navigateToVideoNewsActivity(News news, int currentPosition);

        void navigateToInfografiaActivity(News news);

        void navigateToNewsDetailsActivity(News news);

        void navigateToGalleryActivity(News news);

    }
}
