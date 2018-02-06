package com.BarcelonaSC.BarcelonaApp.ui.validate.account.mvp;

import com.BarcelonaSC.BarcelonaApp.models.ValidateAccItem;
import com.BarcelonaSC.BarcelonaApp.models.response.AuthResponse;

/**
 * Created by Leonardojpr on 12/6/17.
 */

public class ValidateAccPresenter  implements ValidateAccContract.Presenter, ValidateAccContract.ModelResultListener {

    private static final String TAG = ValidateAccContract.class.getSimpleName();
    private ValidateAccContract.View view;
    private ValidateAccModel model;

    public ValidateAccPresenter(ValidateAccContract.View contract, ValidateAccModel model) {
        this.view = contract;
        this.model = model;
    }

    @Override
    public void sendCode(ValidateAccItem validateAccItem) {
        model.sendCode(validateAccItem, this);
        view.showProgress();
    }

    @Override
    public void resendCode(ValidateAccItem validateAccItem) {
        model.resendCode(validateAccItem, this);
        view.showProgress();
    }

    @Override
    public void onAttach(ValidateAccContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void onSuccess(AuthResponse data) {
        if (view != null) {
            view.success(data);
            view.hideProgress();
        }
    }

    @Override
    public void resendCodeSuccess(String msg) {
        if (view != null) {
            view.resendCodeSuccess(msg);
            view.hideProgress();
        }
    }

    @Override
    public void onFailed(String error) {
        if (view != null) {
            view.showToastError(error);
            view.hideProgress();
        }
    }
}
