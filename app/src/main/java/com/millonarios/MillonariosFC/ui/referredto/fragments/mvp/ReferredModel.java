package com.millonarios.MillonariosFC.ui.referredto.fragments.mvp;

import com.millonarios.MillonariosFC.app.api.ReferredApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.response.ReferredToResponse;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

public class ReferredModel {

    private ReferredApi referredApi;

    public ReferredModel(ReferredApi referredApi) {
        this.referredApi = referredApi;
    }

    public void getReferidos(String token, String p, final ReferredContract.ModelResultListener listener) {
        referredApi.getReferidos(token, p).enqueue(new NetworkCallBack<ReferredToResponse>() {
            @Override
            public void onRequestSuccess(ReferredToResponse response) {
                if (Constant.Key.SUCCESS.equals(response.getStatus())) {
                    listener.onGetReferidosSuccess(response.getData());
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

}