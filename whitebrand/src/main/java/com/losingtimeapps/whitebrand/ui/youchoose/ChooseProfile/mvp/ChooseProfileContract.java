package com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp;


import com.losingtimeapps.whitebrand.commons.mvp.MVPContract;
import com.losingtimeapps.whitebrand.models.ChooseProfileData;
import com.losingtimeapps.whitebrand.models.News;

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

        void clickItem(News news);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String error);

        void setChooseProfileData(ChooseProfileData chooseProfileData);

        void navigateToVideoNewsActivity(News news, int currentPosition);
        void navigateToInfografiaActivity(News news);
        void navigateToNewsDetailsActivity(News news);
        void navigateToGalleryActivity(News news);

    }

}