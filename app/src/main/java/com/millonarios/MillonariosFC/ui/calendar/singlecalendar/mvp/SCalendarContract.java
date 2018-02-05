package com.millonarios.MillonariosFC.ui.calendar.singlecalendar.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.SCalendarData;

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