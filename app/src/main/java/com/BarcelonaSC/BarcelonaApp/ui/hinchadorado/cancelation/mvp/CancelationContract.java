package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.BaseView;
import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.ReasonData;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class CancelationContract {


    public interface ModelResultListener {

        void onError(String error);

        void onGetReasonSuccess(List<ReasonData> reasonData);

        void cancelSuccess();
    }

    public interface Presenter extends MVPContract.Presenter<View> {


        boolean isViewNull();


    }

    public interface View extends BaseView {

        void setReasonData(List<ReasonData> reasonData);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);


        void finishActivity();
    }
}
