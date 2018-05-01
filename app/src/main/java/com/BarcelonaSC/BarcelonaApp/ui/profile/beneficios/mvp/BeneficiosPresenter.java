package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.mvp;

import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;

import java.util.List;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

public class BeneficiosPresenter implements BeneficiosContract.Presenter, BeneficiosContract.ModelResultListener{

    private BeneficiosContract.View view;
    private BeneficiosModel beneficiosModel;
    private List<BeneficioData> beneficios;

    public BeneficiosPresenter(BeneficiosContract.View view, BeneficiosModel beneficiosModel) {
        this.view = view;
        this.beneficiosModel = beneficiosModel;
    }

    @Override
    public void onAttach(BeneficiosContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickItem(BeneficioData beneficio) {

    }

    @Override
    public void loadBeneficios() {
        beneficiosModel.loadBeneficios(this);
    }

    @Override
    public void onGetBeneficiosSuccess(List<BeneficioData> beneficios) {
        view.updateBeneficios(beneficios);
    }

    @Override
    public void onGetBeneficiosFailed() {
        view.onFailedUpdate();
    }
}
