package com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp;


import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.ApplauseData;
import com.millonarios.MillonariosFC.models.ChooseProfileData;
import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.models.PlayerData;

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
