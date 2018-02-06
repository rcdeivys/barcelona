package com.BarcelonaSC.BarcelonaApp.ui.chat.requests.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Solicitud;
import com.BarcelonaSC.BarcelonaApp.ui.chat.requests.RequestModelView;

import java.util.List;

/**
 * Created by Cesar on 30/01/2018.
 */

public class RequestContract {
    public interface ModelResultListener {

        void onGetRequestSuccess(List<Solicitud> request);

        void onGetRequestFailed();

        void onGetSuggestSuccess(List<RequestModelView> suggest);

        void onGetSuggestFailed();

        void ongetSendRequestSuccess(List<Solicitud> suggest);

        void ongetSendRequestFailed();

        void onInviteSuccess();

        void onInviteFailed();

        void onAcceptSuccess();

        void onAcceptFailed();

        void onRejectSuccess();

        void onRejectFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {


        boolean isViewNull();

        void onClickInvitedUser(String id, RequestModelView suggestModelView);

        void onClickAcceptUser(String id, RequestModelView suggestModelView);

        void onClickCancelUser(String id, String suggestModelView);

        void loadPendingRequest();

        void loadSendRequest();

        void findByName(String name);

    }

    public interface View {


        void setSuggestData(List<RequestModelView> listRequest);

        void setRequestData(List<RequestModelView> listRequest);

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void onCompletedSuccess(String Tag);

        void showToastError(String errror, String TAG);

        void prepareForSeach(boolean seach);

        void refresh();
    }
}
