package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.mvp;

import com.millonarios.MillonariosFC.app.api.BeneficiosApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.BeneficiosResponse;

/**
 * Created by Pedro Gomez on 20/02/2018.
 */

public class BeneficiosModel {

    private static final String TAG = BeneficiosModel.class.getSimpleName();

    private BeneficiosApi beneficiosApi;

    public BeneficiosModel(BeneficiosApi beneficiosApi) {
        this.beneficiosApi = beneficiosApi;
    }

    public void loadBeneficios(final BeneficiosContract.ModelResultListener result){

        beneficiosApi.getBeneficios().enqueue(new NetworkCallBack<BeneficiosResponse>() {
            @Override
            public void onRequestSuccess(BeneficiosResponse response) {
                result.onGetBeneficiosSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetBeneficiosFailed();
            }
        });
    }
}
