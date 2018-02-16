package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.ReferredData;

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