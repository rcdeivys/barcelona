package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp;

import com.BarcelonaSC.BarcelonaApp.models.ChooseProfileData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp.ChooseProfileContract;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;


/**
 * Created by Carlos on 13/10/2017.
 */

public class ChooseProfilePresenter implements ChooseProfileContract.Presenter, ChooseProfileContract.ModelResultListener {

    private static final String TAG = ChooseProfilePresenter.class.getSimpleName();
    private ChooseProfileContract.View view;
    private ChooseProfileModel playerProfileModel;

    public ChooseProfileData chooseProfileData;

    private String profileId = "";

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public ChooseProfilePresenter(ChooseProfileContract.View view, ChooseProfileModel playerProfileModel) {
        this.view = view;
        this.playerProfileModel = playerProfileModel;
        chooseProfileData = new ChooseProfileData();


    }

    @Override
    public void onAttach(ChooseProfileContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getChooseProfileData(String playerId) {
        playerProfileModel.getChooseProfile(playerId, this);
    }

    @Override
    public void clickItem(News news) {

        //news = chooseProfileData.getNoticias().get(position);
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
    public void onGetChooseProfileSuccess(ChooseProfileData chooseProfileData) {
        this.chooseProfileData = chooseProfileData;

        if (isViewNull()) return;
        view.setChooseProfileData(this.chooseProfileData);
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
