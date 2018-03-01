package com.BarcelonaSC.BarcelonaApp.ui.login.mvp;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.AuthItem;
import com.BarcelonaSC.BarcelonaApp.models.User;

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