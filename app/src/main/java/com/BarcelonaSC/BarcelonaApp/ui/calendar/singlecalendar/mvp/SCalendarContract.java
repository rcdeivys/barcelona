package com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.SCalendarData;

/**
 * Created by Erick on 01/11/2017.
 */

public class SCalendarContract {

    public interface ModelResultListener {
        void onGetNoticiasSuccess(SCalendarData noticias);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getNoticias(int id);

        void clickItem(int position);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setNoticias(SCalendarData noticias);

    }

}