package com.BarcelonaSC.BarcelonaApp.ui.register.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.User;
import com.BarcelonaSC.BarcelonaApp.models.response.GenericResponse;

/**
 * Created by Erick on 14/10/2017.
 */

public class RegisterContract {

    public interface ModelResultListener {

        void onGetRegisterSuccess(GenericResponse data);

        void onGetRegisterFailed(String error);

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void loadRegister(User user);
    }

    public interface View {
        void setRegister(GenericResponse data);

        void showProgress();

        void hideProgress();

        void showToastError(String error);
    }

}