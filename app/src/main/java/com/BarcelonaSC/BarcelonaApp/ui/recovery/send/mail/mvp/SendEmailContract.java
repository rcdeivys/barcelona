package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.response.SendEmailResponse;

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
