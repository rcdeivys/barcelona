package com.BarcelonaSC.BarcelonaApp.ui.login.mvp;


import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;

/**
 * Created by Erick on 12/10/2017.
 */

public class LoginPresenter implements LoginContract.Presenter, LoginContract.ModelResultListener {

    private static final String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View loginContract;
    private LoginModel loginModel;

    public LoginPresenter(LoginContract.View loginContract, LoginModel loginModel) {
        this.loginContract = loginContract;
        this.loginModel = loginModel;
    }

    @Override
    public void onGetLoginSuccess(AuthItem data) {
        if (loginContract != null) {
            loginContract.setLogin(data);
            loginContract.hideProgress();
        }
    }

    @Override
    public void onGetLoginFailed(String error) {
        if (loginContract != null) {
            loginContract.showToastError(error);
            loginContract.hideProgress();
        }
    }

    @Override
    public void loadLogin(User user) {
        loginModel.loadLogin(user, this);
        loginContract.showProgress();
    }

    @Override
    public void loadLoginSocial(User user) {
        loginModel.loadLoginSocial(user, this);
    }

    @Override
    public void onAttach(Object view) {

    }

    @Override
    public void onDetach() {
        loginContract = null;
    }
}