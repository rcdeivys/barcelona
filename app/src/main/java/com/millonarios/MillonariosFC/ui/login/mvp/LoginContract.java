package com.millonarios.MillonariosFC.ui.login.mvp;
import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.AuthItem;
import com.millonarios.MillonariosFC.models.User;

/**
 * Created by Erick on 12/10/2017.
 */

public class LoginContract {

    public interface ModelResultListener {

        void onGetLoginSuccess(AuthItem data);

        void onGetLoginFailed(String error);
    }

    public interface Presenter extends MVPContract.Presenter {
        void loadLogin(User user);

        void loadLoginSocial(User user);
    }

    public interface View {
        void setLogin(AuthItem data);

        void showProgress();

        void hideProgress();

        void showToastError(String errror);
    }

}