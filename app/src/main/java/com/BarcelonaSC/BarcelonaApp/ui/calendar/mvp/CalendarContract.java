package com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.Tournament;

import java.util.List;

/**
 * Created by Erick on 31/10/2017.
 */

public class CalendarContract {

    public interface ModelResultListener {
        void onGetCupSuccess(List<Tournament> tournaments);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getCup();

        void getMatch();

        void clickItem(int position);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setCup(List<Tournament> tournaments);

    }

}
