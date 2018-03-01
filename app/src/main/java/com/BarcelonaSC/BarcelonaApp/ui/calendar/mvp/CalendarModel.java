package com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp;

import com.BarcelonaSC.BarcelonaApp.models.response.TournamentResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.app.api.TournamentApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;

/**
 * Created by Erick on 31/10/2017.
 */

public class CalendarModel {

    private TournamentApi tournamentApi;

    public CalendarModel(TournamentApi tournamentApi) {
        this.tournamentApi = tournamentApi;
    }

    public void getCups(final CalendarContract.ModelResultListener listener) {
        tournamentApi.getTournaments().enqueue(new NetworkCallBack<TournamentResponse>() {
            @Override
            public void onRequestSuccess(TournamentResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetCupSuccess(response.getData());
                } else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }

    public void getCupsFb(final CalendarContract.ModelResultListener listener) {
        tournamentApi.getCalendariofb().enqueue(new NetworkCallBack<TournamentResponse>() {
            @Override
            public void onRequestSuccess(TournamentResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetCupSuccess(response.getData());
                } else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }

    public void getMatches(final CalendarContract.ModelResultListener listener) {
        tournamentApi.getMatches().enqueue(new NetworkCallBack<TournamentResponse>() {
            @Override
            public void onRequestSuccess(TournamentResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetCupSuccess(response.getData());
                } else {
                    listener.onError("");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                listener.onError(errorMessage);
            }
        });
    }
}