package com.millonarios.MillonariosFC.ui.validate.account.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.ValidateAccItem;
import com.millonarios.MillonariosFC.models.response.AuthResponse;

/**
 * Created by Leonardojpr on 12/6/17.
 */

public class ValidateAccContract {

    public interface ModelResultListener {

        void onSuccess(AuthResponse genericResponse);

        void resendCodeSuccess(String msg);

        void onFailed(String error);

    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void sendCode(ValidateAccItem validateAccItem );

        void resendCode(ValidateAccItem validateAccItem);
    }

    public interface View {
        void success(AuthResponse data);

        void resendCodeSuccess(String msg);

        void showProgress();

        void hideProgress();

        void showToastError(String error);
    }

}
