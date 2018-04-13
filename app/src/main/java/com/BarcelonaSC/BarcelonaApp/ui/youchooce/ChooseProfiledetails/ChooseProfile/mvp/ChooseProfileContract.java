package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp;


import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.ChooseProfileData;
import com.BarcelonaSC.BarcelonaApp.models.News;

/**
 * Created by Carlos on 13/10/2017.
 */

public class ChooseProfileContract {


    public interface ModelResultListener {
        void onGetChooseProfileSuccess(ChooseProfileData chooseProfileData);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getChooseProfileData(String playerId);

        void clickItem(int position);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String error);

        void setChooseProfileData(ChooseProfileData chooseProfileData);

        void navigateToVideoNewsActivity(News news);

        void navigateToInfografiaActivity(News news);

        void navigateToNewsDetailsActivity(News news);

        void navigateToGalleryActivity(int id);

    }

}
