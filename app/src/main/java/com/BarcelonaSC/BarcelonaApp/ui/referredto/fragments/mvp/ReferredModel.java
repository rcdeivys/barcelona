package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp;

import com.BarcelonaSC.BarcelonaApp.app.api.ReferredApi;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.response.ReferredToResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

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