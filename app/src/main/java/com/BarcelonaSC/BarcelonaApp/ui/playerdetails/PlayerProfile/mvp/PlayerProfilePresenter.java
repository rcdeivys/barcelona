package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.eventbus.PlayerEvent;
import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerApplause;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfilePresenter implements PlayerProfileContract.Presenter, PlayerProfileContract.ModelResultListener {

    private static final String TAG = PlayerProfilePresenter.class.getSimpleName();
    private PlayerProfileContract.View view;
    private PlayerProfileModel playerProfileModel;

    public PlayerData playerData;

    private String playerId = "";

    public PlayerProfilePresenter(PlayerProfileContract.View view, PlayerProfileModel playerProfileModel) {
        this.view = view;
        this.playerProfileModel = playerProfileModel;
        playerData = new PlayerData();
    }

    @Override
    public void onAttach(PlayerProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getPlayer(String playerId) {
        this.playerId = playerId;
        playerProfileModel.getPlayerData(this.playerId, this);
    }

    @Override
    public void getPlayer() {
        playerProfileModel.getPlayerData(playerId, this);
    }

    public void getPlayerFB(String playerId) {
        this.playerId = playerId;
        playerProfileModel.getPlayerDataFB(this.playerId, this);
    }

    @Override
    public void clickItem(News news) {
        if (isViewNull()) return;

        if (news.getTipo().matches(Constant.NewsType.GALERY)) {
            view.navigateToGalleryActivity(news);
        } else if (news.getTipo().matches(Constant.NewsType.INFOGRAFY) || news.getTipo().matches(Constant.NewsType.STAT)) {
            view.navigateToInfografiaActivity(news);
        } else {
            view.navigateToNewsDetailsActivity(news);
        }
    }

    @Override
    public void getPlayerApplause() {

    }

    @Override
    public void noDoradoErrorListener() {
        if (isViewNull()) return;
        view.hideProgress();
        view.setRefreshing(false);
        view.showDialogDorado();
    }

    @Override
    public void setPlayerApplause() {
        if (isViewNull()) return;
        playerProfileModel.setPlayerApplause(new PlayerApplause(playerId, playerData.getIdpartido(), view.getImei()), this);
    }

    @Override
    public void onGetPlayerSuccess(PlayerData player) {
        playerData = player;
        if (isViewNull()) return;
        view.setPlayerData(this.playerData);
    }

    @Override
    public void onGetPlayerApplauseSuccess(ApplauseData applauseData) {

    }

    @Override
    public void onSetPlayerApplauseSuccess(String id, int aplaudio) {

        if (playerData != null) {
            if (aplaudio == 1) {
                playerData.setApalusosUltimoPartido(playerData.getApalusosUltimoPartido() + 1);
                playerData.setAplausosAcumulado(playerData.getAplausosAcumulado() + 1);
                playerData.setUltimoAplauso(1);

                if (isViewNull()) return;
                view.showToast(App.getAppContext().getString(R.string.applause_successful));
                view.setPlayerData(playerData);
                view.showShareApplause(id);
                EventBus.getDefault().post(new PlayerEvent(true));
            } else {
                playerData.setApalusosUltimoPartido(playerData.getApalusosUltimoPartido() - 1);
                playerData.setAplausosAcumulado(playerData.getAplausosAcumulado() - 1);
                playerData.setUltimoAplauso(0);

                if (isViewNull()) return;
                view.showToast(App.getAppContext().getString(R.string.applause_remove));
                view.setPlayerData(playerData);
                EventBus.getDefault().post(new PlayerEvent(true));
            }
        }
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

}
