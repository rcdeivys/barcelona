package com.millonarios.MillonariosFC.ui.referredto.fragments.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.Referido;
import com.millonarios.MillonariosFC.models.ReferredData;

import java.util.List;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

public class ReferredContract {

    public interface ModelResultListener {
        void onGetReferidosSuccess(ReferredData referidos);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void getReferidos(String token, String p);

        void clickItem(int position);

        boolean isViewNull();
    }

    public interface View {
        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setReferidos(ReferredData referidos);
    }

}