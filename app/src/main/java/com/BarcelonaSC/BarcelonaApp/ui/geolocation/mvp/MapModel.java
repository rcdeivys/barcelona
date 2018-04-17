package com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.MapApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.MapResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by RYA-Laptop on 15/03/2018.
 */

public class MapModel {

    private MapApi api;

    public MapModel(MapApi api) {
        this.api = api;
    }

    public void getPoints(final MapContract.ModelResultListener result) {
        api.getPoints().enqueue(new NetworkCallBack<MapResponse>() {
            @Override
            public void onRequestSuccess(MapResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    if (response.getData() != null) {
                        result.onGetPointsSuccess(response.getData());
                    } else {
                        result.onError("No hay puntos de interés");
                    }
                } else {
                    result.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onError(errorMessage);
            }
        });
    }

}