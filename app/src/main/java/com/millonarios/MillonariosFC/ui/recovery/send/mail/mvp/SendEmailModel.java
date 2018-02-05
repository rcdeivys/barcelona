package com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp;

import com.millonarios.MillonariosFC.app.api.RecoveryPasswordApi;
import com.millonarios.MillonariosFC.app.network.NetworkCallBack;
import com.millonarios.MillonariosFC.models.SendEmail;
import com.millonarios.MillonariosFC.models.response.SendEmailResponse;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendEmailModel {

    private static final String TAG = RecoveryPasswordApi.class.getSimpleName();
    private RecoveryPasswordApi recoveryPasswordApi;

    public SendEmailModel(RecoveryPasswordApi recoveryPasswordApi) {
        this.recoveryPasswordApi = recoveryPasswordApi;
    }

    public void sendEmail(final String email, final SendEmailContract.ModelResultListener result) {
        SendEmail sendEmail = new SendEmail(email);

        recoveryPasswordApi.sendEmail(sendEmail).enqueue(new NetworkCallBack<SendEmailResponse>() {
            @Override
            public void onRequestSuccess(SendEmailResponse response) {
                if (!response.getStatus().equals("fallo")) {
                    result.onSuccess(response);
                } else {
                    result.onFailed("Email incorrecto");
                }
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onFailed("Ha ocurrido un error inesperado, vuelva a intentarlo.");
            }
        });
    }


}
