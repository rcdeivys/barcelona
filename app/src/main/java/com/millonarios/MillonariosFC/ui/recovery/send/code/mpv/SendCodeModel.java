package com.millonarios.MillonariosFC.ui.recovery.send.code.mpv;

import com.millonarios.MillonariosFC.models.SendCode;
import com.millonarios.MillonariosFC.models.response.SendCodeResponse;
import com.millonarios.MillonariosFC.app.api.RecoveryPasswordApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendCodeModel {

    private static final String TAG = RecoveryPasswordApi.class.getSimpleName();
    private RecoveryPasswordApi recoveryPasswordApi;

    public SendCodeModel(RecoveryPasswordApi recoveryPasswordApi) {
        this.recoveryPasswordApi = recoveryPasswordApi;
    }

    public void sendCode(final String email, final String code, final SendCodeContract.ModelResultListener result) {
        SendCode sendCodeBody = new SendCode(email, code);

        recoveryPasswordApi.sendCode(sendCodeBody).enqueue(new NetworkCallBack<SendCodeResponse>() {
            @Override
            public void onRequestSuccess(SendCodeResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onSuccess(response);
                } else {
                    result.onFailed("Codigo incorrecto");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed(errorMessage);
            }
        });
    }
}
