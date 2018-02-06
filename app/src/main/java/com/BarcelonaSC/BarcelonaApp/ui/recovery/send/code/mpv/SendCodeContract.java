package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.response.SendCodeResponse;

/**
 * Created by Leonardojpr on 11/12/17.
 */

public class SendCodeContract {

    public interface ModelResultListener {

        void onSuccess(SendCodeResponse sendCodeResponse);

        void onFailed(String error);

    }

    public interface Presenter extends MVPContract.Presenter {
        void sendCode(String email, String code);
    }

    public interface View {
        void success(SendCodeResponse data);

        void showProgress();

        void hideProgress();

        void showToastError(String error);
    }
}
