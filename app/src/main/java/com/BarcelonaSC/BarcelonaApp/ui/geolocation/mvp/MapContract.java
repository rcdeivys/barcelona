package com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.MapData;

import java.util.List;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public class MapContract {

    public interface Presenter extends MVPContract.Presenter<View> {
        void getPoints();

        boolean isViewNull();
    }

    public interface ModelResultListener {
        void onGetPointsSuccess(List<MapData> points);

        void onError(String error);
    }

    public interface View {
        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setPoints(List<MapData> points);
    }

}