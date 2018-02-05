package com.millonarios.MillonariosFC.ui.chat.requests.mvp;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.WindowManager;

import com.arasthel.asyncjob.AsyncJob;
import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.Solicitud;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestConsts;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestModelView;
import com.millonarios.MillonariosFC.ui.chat.requests.SuggestModelView;
import com.millonarios.MillonariosFC.utils.Commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 30/01/2018.
 */

public class RequestPresenter implements RequestContract.Presenter, RequestContract.ModelResultListener {

    private static final String TAG = RequestPresenter.class.getSimpleName();
    private RequestContract.View view;
    private RequestModel requestModel;

    private List<Solicitud> requestList = new ArrayList<>();
    private List<RequestModelView> suggestList = new ArrayList<>();
    private List<Solicitud> sendRequestList = new ArrayList<>();

    public RequestPresenter(RequestContract.View view, RequestModel requestModel) {
        this.view = view;
        this.requestModel = requestModel;
    }


    @Override
    public void onAttach(RequestContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onGetRequestSuccess(List<Solicitud> requests) {
        List<RequestModelView> listSolicitudesPendientes = new ArrayList<>();

        requestList = requests;
        if (this.requestList != null) {
            for (int i = 0; i < this.requestList.size(); i++) {
                Solicitud solicitud = this.requestList.get(i);
                listSolicitudesPendientes.add(new RequestModelView(Long.valueOf(solicitud.getKey())
                        , solicitud.getMiembro().getApodo()
                        , solicitud.getMiembro().getFoto()
                        , solicitud.getMiembro().getNombre()
                        , false));
            }
        }
        if (isViewNull()) return;
        view.setRequestData(listSolicitudesPendientes);
    }

    @Override
    public void onGetRequestFailed() {

    }

    @Override
    public void onGetSuggestSuccess(List<RequestModelView> suggests) {
        if (suggests == null)
            suggests = new ArrayList<>();
        suggestList = suggests;
        if (isViewNull() || suiche) return;

        view.setSuggestData(processSugerList(suggestList));


    }

    private List<RequestModelView> processSugerList(List<RequestModelView> suggests) {
        Log.i(TAG, "--->" + FirebaseManager.getInstance().getUsuario().getSolicitudesEnviadas().size());
        List<RequestModelView> sugggersAuxList = new ArrayList<>();
        if (suggests != null) {

            for (RequestModelView str : suggests) {
                str.setSend(false);
                boolean addItem = true;
                for (Solicitud solicitud : FirebaseManager.getInstance().getUsuario().getSolicitudesEnviadas()) {
                    if (str.getId().equals(solicitud.getMiembro().getId())) {
                        if (solicitud.getStatus() == 1)
                            addItem = false;
                        str.setSend(true);
                        break;
                    }

                }
                if (addItem)
                    sugggersAuxList.add(str);

            }

            return sugggersAuxList;

        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public void onGetSuggestFailed() {

    }

    @Override
    public void ongetSendRequestSuccess(List<Solicitud> suggest) {

    }

    @Override
    public void ongetSendRequestFailed() {

    }

    @Override
    public void onInviteSuccess() {
        if (view == null) return;
        view.onCompletedSuccess(RequestConsts.TAG_ONCOMPLETE_REQUEST_INVITED);
    }

    @Override
    public void onInviteFailed() {
        if (view == null) return;
        view.showToastError(Commons.getString(R.string.toast_error_invited), RequestConsts.TAG_TOAST_ERROR_INVITED);
    }

    @Override
    public void onAcceptSuccess() {
        if (view == null) return;
        view.onCompletedSuccess(RequestConsts.TAG_ONCOMPLETE_ACCEPT_USER);
    }

    @Override
    public void onAcceptFailed() {
        if (view == null) return;
        view.showToastError(Commons.getString(R.string.toast_error_accept_user), RequestConsts.TAG_TOAST_ERROR_ACCEPT_USER);
    }

    @Override
    public void onRejectSuccess() {
        if (view == null) return;
        view.onCompletedSuccess(RequestConsts.TAG_ONCOMPLETE_REQUEST_USER);
    }

    @Override
    public void onRejectFailed() {
        if (view == null) return;
        view.showToastError(Commons.getString(R.string.toast_error_reject_user), RequestConsts.TAG_TOAST_ERROR_REJECT_USER);
    }


    @Override
    public void onClickInvitedUser(String id, RequestModelView suggestModelView) {
        requestModel.sendInvitedToUser(id, String.valueOf(suggestModelView.getId()), this);
    }

    @Override
    public void onClickAcceptUser(String id, RequestModelView suggestModelView) {
        requestModel.acceptUser(id, String.valueOf(suggestModelView.getId()), this);
    }

    @Override
    public void onClickCancelUser(String id, String suggestModelView) {
        requestModel.rejectUser(id, suggestModelView, this);
    }

    @Override
    public void loadPendingRequest() {
        requestModel.loadRequest(SessionManager.getInstance().getUser().getId_usuario(), this);
        requestModel.loadSuggest(this);
    }


    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void loadSendRequest() {


        if (seach.isEmpty())
            requestModel.loadSuggest(this);
        else
            findByName(seach);

    }

    private boolean suiche = false;
    String seach = "";

    @Override
    public void findByName(final String name) {
        seach = name;

        if (!seach.isEmpty()) {
            if (!suiche) {
                view.prepareForSeach(true);
                suiche = true;
            }
            FirebaseManager.getInstance().buscarUsuario(seach, new FirebaseManager.FireListener<List<RequestModelView>>() {
                @Override
                public void onDataChanged(final List<RequestModelView> data) {
                    Log.i(TAG, "--->onDataChanged PASO POR AQUI");
                    if (isViewNull()) return;
                    Log.i(TAG, "--->onDataChanged PASO POR AQUI TAMBIEN : size:" + data.size());
                    if (suiche)
                        view.setSuggestData(processSugerList(data));

                }

                @Override
                public void onDataDelete(String id) {

                }

                @Override
                public void onCancelled() {

                }
            });


        } else {
            suiche = false;
            view.prepareForSeach(false);
            onGetSuggestSuccess(suggestList);
        }
    }


}
