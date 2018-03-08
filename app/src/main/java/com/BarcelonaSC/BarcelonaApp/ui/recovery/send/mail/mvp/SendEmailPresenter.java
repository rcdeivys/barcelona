package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.mvp;

import com.BarcelonaSC.BarcelonaApp.models.response.SendEmailResponse;
import com.BarcelonaSC.BarcelonaApp.ui.profile.mvp.ProfilePresenter;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendEmailPresenter implements SendEmailContract.Presenter, SendEmailContract.ModelResultListener {

    private static final String TAG = ProfilePresenter.class.getSimpleName();
    private SendEmailContract.View contract;
    private SendEmailModel model;

    public SendEmailPresenter(SendEmailContract.View contract, SendEmailModel model) {
        this.contract = contract;
        this.model = model;
    }

    @Override
    public void sendEmail(String email) {
        if (!email.isEmpty()) {
            if (email.contains("@")) {
                model.sendEmail(email, this);
                contract.showProgress();
            } else {
                onFailed("El e-mail no es válido");
            }
        } else {
            onFailed("El correo y la contraseña no pueden estar vacíos");
        }
        if (email.isEmpty())
            model.sendEmail(email, this);
    }

    @Override
    public void onAttach(Object view) {

    }

    @Override
    public void onDetach() {
        contract = null;
    }

    @Override
    public void onSuccess(SendEmailResponse data) {
        if (contract != null) {
            contract.success(data);
            contract.hideProgress();
        }
    }

    @Override
    public void onFailed(String error) {
        if (contract != null) {
            contract.showToastError(error);
            contract.hideProgress();
        }
    }
}