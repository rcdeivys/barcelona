package com.millonarios.MillonariosFC.ui.profile.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.User;
import com.millonarios.MillonariosFC.models.UserItem;

/**
 * Created by Leonardojpr on 11/10/17.
 */

public class ProfileContract {

    public interface ModelResultListener {

        void onGetUserSuccess(UserItem data);

        void onPutUserSuccess();

        void onFailed(String error);

    }

    public interface Presenter extends MVPContract.Presenter {

        void loadUser(String token);

        void updateUser(User user, String token);
    }

    public interface View {
        void setUser(UserItem data);

        void updateUser();

        void showProgress();

        void hideProgress();

        void showToastError(String error);
    }
}
