package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp;

import com.BarcelonaSC.BarcelonaApp.models.Referido;
import com.BarcelonaSC.BarcelonaApp.models.ReferredData;

import java.util.ArrayList;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

public class ReferredPresenter implements ReferredContract.Presenter, ReferredContract.ModelResultListener {

    private ReferredContract.View view;
    private ReferredModel model;

    private ArrayList<Referido> referredList;

    public ReferredPresenter(ReferredContract.View view, ReferredModel model) {
        this.view = view;
        this.model = model;
        referredList = new ArrayList<>();
    }

    @Override
    public void onAttach(ReferredContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onGetReferidosSuccess(ReferredData referidos) {
        if (isViewNull()) return;

        view.setReferidos(referidos);

        view.hideProgress();
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToast(error);
    }

    @Override
    public void getReferidos(String token, String p) {
        if (view == null) return;
        referredList.clear();

        model.getReferidos(token, p, this);

        view.showProgress();
    }

    @Override
    public void clickItem(int position) {

    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }
}