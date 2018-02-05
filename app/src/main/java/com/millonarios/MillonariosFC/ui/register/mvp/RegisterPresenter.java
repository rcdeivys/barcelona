package com.millonarios.MillonariosFC.ui.register.mvp;

import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.response.AuthResponse;
import com.millonarios.MillonariosFC.models.response.GenericResponse;

/**
 * Created by Erick on 14/10/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter, RegisterContract.ModelResultListener {

    private static final String TAG = RegisterPresenter.class.getSimpleName();
    private RegisterContract.View registerContract;
    private RegisterModel registerModel;

    public RegisterPresenter(RegisterContract.View registerContract, RegisterModel registerModel) {
        this.registerContract = registerContract;
        this.registerModel = registerModel;
    }

    @Override
    public void onGetRegisterSuccess(GenericResponse data) {
        if (registerContract != null) {
            registerContract.setRegister(data);
            registerContract.hideProgress();
        }
    }

    @Override
    public void onGetRegisterFailed(String error) {
        if (registerContract != null) {
            registerContract.showToastError(error);
            registerContract.hideProgress();
        }
    }

    @Override
    public void loadRegister(User user) {
        registerModel.loadRegister(user, this);
        registerContract.showProgress();
    }

    @Override
    public void onAttach(RegisterContract.View view) {
        this.registerContract = view;
    }

    @Override
    public void onDetach() {
        registerContract = null;
    }
}