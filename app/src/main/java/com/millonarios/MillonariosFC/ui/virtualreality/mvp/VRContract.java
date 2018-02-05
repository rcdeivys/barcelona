package com.millonarios.MillonariosFC.ui.virtualreality.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.VideoReality;

import java.util.ArrayList;

/**
 * Created by Carlos-pc on 04/10/2017.
 */

public class VRContract {

    public interface ModelResultListener {

        void onGetVideosSuccess(ArrayList<VideoReality> videoRealities);

        void onGetVideosFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void loadVideos();

        void onclickVideoItem(int position);
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setVideos(ArrayList<VideoReality> videoRealities);

        void navigateVirtualActivity(VideoReality videoReality);
    }


}
