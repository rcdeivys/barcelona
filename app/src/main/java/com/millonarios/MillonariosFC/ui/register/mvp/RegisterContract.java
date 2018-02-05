package com.millonarios.MillonariosFC.ui.register.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.response.GenericResponse;

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