package com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.response.SendEmailResponse;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendEmailContract {


    public interface ModelResultListener {

        void onSuccess(SendEmailResponse sendEmailResponse);

        void onFailed(String error);

    }

    public interface Presenter extends MVPContract.Presenter {
        void sendEmail(String email);
    }

    public interface View {
        void success(SendEmailResponse data);

        void showProgress();
        void hideProgress();
        void showToastError(String error);
    }
}
