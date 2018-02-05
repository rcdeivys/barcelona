package com.millonarios.MillonariosFC.ui.recovery.send.code.mpv;

import com.millonarios.MillonariosFC.models.response.SendCodeResponse;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfilePresenter;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendCodePresenter implements SendCodeContract.Presenter, SendCodeContract.ModelResultListener {

    private static final String TAG = ProfilePresenter.class.getSimpleName();
    private SendCodeContract.View contract;
    private SendCodeModel model;

    public SendCodePresenter(SendCodeContract.View contract, SendCodeModel model) {
        this.contract = contract;
        this.model = model;
    }

    @Override
    public void sendCode(String email, String code) {
        model.sendCode(email, code, this);
        contract.showProgress();
    }

    @Override
    public void onAttach(Object view) {

    }

    @Override
    public void onDetach() {
        contract = null;
    }

    @Override
    public void onSuccess(SendCodeResponse data) {
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
