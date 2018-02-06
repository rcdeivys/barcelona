package com.BarcelonaSC.BarcelonaApp.ui.profile.mvp;

import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;

/**
 * Created by Leonardojpr on 11/10/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter, ProfileContract.ModelResultListener {

    private static final String TAG = ProfilePresenter.class.getSimpleName();
    private ProfileContract.View contract;
    private ProfileModel model;

    public ProfilePresenter(ProfileContract.View contract, ProfileModel model) {
        this.contract = contract;
        this.model = model;
    }

    @Override
    public void loadUser(String token) {
        model.loadUser(token, this);
      //  contract.showProgress();
    }

    @Override
    public void updateUser(User user, String token) {
        model.updateUser(user, token, this);
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
    public void onGetUserSuccess(UserItem data) {
        if (contract != null) {
            contract.setUser(data);
            contract.hideProgress();
        }
    }

    @Override
    public void onPutUserSuccess() {
        contract.updateUser();
        contract.hideProgress();
    }

    @Override
    public void onFailed(String error) {
        if (contract != null) {
            contract.showToastError(error);
            contract.hideProgress();
        }
    }
}
