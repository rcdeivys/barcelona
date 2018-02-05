package com.millonarios.MillonariosFC.ui.calendar.singlecalendar.mvp;

import com.millonarios.MillonariosFC.models.SCalendarData;

/**
 * Created by Erick on 01/11/2017.
 */

public class SCalendarPresenter implements SCalendarContract.Presenter, SCalendarContract.ModelResultListener {

    private SCalendarContract.View view;
    private SCalendarModel model;
    private SCalendarData noticias;
    private String type;

    public SCalendarPresenter(SCalendarContract.View view, SCalendarModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void onAttach(SCalendarContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetNoticiasSuccess(SCalendarData noticias) {
        //for (SCalendarData sCalendarData : noticias) {
        //sCalendarData.setNoticias(sCalendarData.getNoticias());
        //}
        this.noticias = noticias;

        if (isViewNull()) return;

        view.setNoticias(this.noticias);
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public void getNoticias(int id) {
        model.getNoticias(id, this);
    }

    public void getNoticiasFb(int id) {
        model.getNoticiasFb(id, this);
    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }
}
