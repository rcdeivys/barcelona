package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;
import com.BarcelonaSC.BarcelonaApp.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cesar on 24/2/2018.
 */

public class HinchaDoradoPresenter implements HinchaDoradoContract.Presenter, HinchaDoradoContract.ModelResultListener {

    private HinchaDoradoContract.View view;
    private HinchaDoradoModel hinchaDoradoModel;
    private List<SuscripcionData> listsuscripcionData = new ArrayList<SuscripcionData>();


    public HinchaDoradoPresenter(HinchaDoradoContract.View view, HinchaDoradoModel hinchaDoradoModel) {
        this.view = view;
        this.hinchaDoradoModel = hinchaDoradoModel;
    }

    @Override
    public void onAttach(HinchaDoradoContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    public void updateUser(User user, String token) {
        hinchaDoradoModel.updateUser(user, token, this);

    }

    @Override
    public void onGetSuscripcionSuccess(List<SuscripcionData> suscripcionData) {
        if (listsuscripcionData != null) listsuscripcionData.clear();
        if (view == null) return;
        if(suscripcionData==null) {
            view.onFailedUpdate("No existen planes de suscripcion");
            return;
        }
        for (SuscripcionData suscripcionData1 : suscripcionData) {
            Crashlytics.log(Log.DEBUG, this.getClass().getSimpleName(), "---> Lista suscripcion data " + suscripcionData1.getDescripcion());
            listsuscripcionData.add(suscripcionData1);
        }

        view.updateSuscripcion(listsuscripcionData);
    }

    @Override
    public void onGetSuscripcionFailed() {
        view.onFailedUpdate("Error al cargar tipos de suscripción");
    }

    @Override
    public void onGetStatusSuscripcionSuccess(String status) {
        if (view == null) return;
        view.updateStatusSuscripcion(status);
    }

    @Override
    public void onGetStatusSuscripcionFailed() {
        if (view == null) return;
        view.onFailedStatusSuscripcion();
    }

    @Override
    public void onPutUserSuccess() {

    }

    @Override
    public void onClickItem(SuscripcionData suscripcionData) {

    }

    @Override
    public void loadSuscripcion() {
        hinchaDoradoModel.loadSuscripcion(this);
    }

    @Override
    public void getStatusSuscription() {
        hinchaDoradoModel.getStatusSuscription(this);
    }
}
