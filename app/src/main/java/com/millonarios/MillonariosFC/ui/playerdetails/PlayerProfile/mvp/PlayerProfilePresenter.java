package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.eventbus.PlayerEvent;
import com.millonarios.MillonariosFC.models.ApplauseData;
import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.models.PlayerApplause;
import com.millonarios.MillonariosFC.models.PlayerData;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

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
    public void clickItem(int position) {

        News news = playerData.getNewsList().get(position);
        if (isViewNull()) return;

        if (news.getTipo().matches(Constant.NewsType.GALERY)) {
            view.navigateToGalleryActivity(news.getId());
        } else if (news.getTipo().matches(Constant.NewsType.VIDEO)) {
            view.navigateToVideoNewsActivity(news);
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
    public void onSetPlayerApplauseSuccess() {
        if (playerData != null) {
            playerData.setApalusosUltimoPartido(playerData.getApalusosUltimoPartido() + 1);
            playerData.setAplausosAcumulado(playerData.getAplausosAcumulado() + 1);
            view.setPlayerData(playerData);
            EventBus.getDefault().post(new PlayerEvent(true));
            view.showToast(App.getAppContext().getString(R.string.applause_successful));
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
