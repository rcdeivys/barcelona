package com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp;

import com.BarcelonaSC.BarcelonaApp.models.response.SCalendarResponse;
import com.BarcelonaSC.BarcelonaApp.app.api.SCalendarApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Amplex on 7/11/2017.
 */

public class SCalendarModel {

    private SCalendarApi sCalendarApi;

    public SCalendarModel(SCalendarApi sCalendarApi) {
        this.sCalendarApi = sCalendarApi;
    }

    public void getNoticias(int id, final SCalendarContract.ModelResultListener listener) {
        sCalendarApi.getNoticias(id).enqueue(new NetworkCallBack<SCalendarResponse>() {
            @Override
            public void onRequestSuccess(SCalendarResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetNoticiasSuccess(response.getData());
                } else {
                    listener.onError("Hay un error");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }

    public void getNoticiasFb(int id, final SCalendarContract.ModelResultListener listener) {
        sCalendarApi.getNoticiasFb(id).enqueue(new NetworkCallBack<SCalendarResponse>() {
            @Override
            public void onRequestSuccess(SCalendarResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetNoticiasSuccess(response.getData());
                } else {
                    listener.onError("Hay un error");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError("Hay un error");
            }
        });
    }

}