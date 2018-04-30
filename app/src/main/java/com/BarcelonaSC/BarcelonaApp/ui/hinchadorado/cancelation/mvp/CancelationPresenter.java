package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.cancelation.mvp;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.ReasonData;
import com.BarcelonaSC.BarcelonaApp.models.SendReasonCancelation;

import java.util.List;

/**
 * Created by Carlos on 13/11/2017.
 */

public class CancelationPresenter implements CancelationContract.Presenter, CancelationContract.ModelResultListener {

    public static final String TAG = CancelationPresenter.class.getSimpleName();
    private CancelationContract.View view;
    private CancelationModel model;
    private List<ReasonData> reasonData;


    public CancelationPresenter(CancelationContract.View view, CancelationModel model) {
        this.view = view;
        this.model = model;
    }


    public void getReason() {
        model.getReason(this);

    }


    @Override
    public void onAttach(CancelationContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    @Override
    public void onError(String error) {
        if (isViewNull())
            return;
        view.showToast(error);
    }

    @Override
    public void onGetReasonSuccess(List<ReasonData> reasonData) {
        this.reasonData = reasonData;
        if (isViewNull())
            return;
        view.setReasonData(this.reasonData);
    }

    @Override
    public void cancelSuccess() {
        if (isViewNull())
            return;
        view.finishActivity();
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }


    public void cancelSuscription(String other) {
        SendReasonCancelation sendReasonCancelation = new SendReasonCancelation();
        sendReasonCancelation.setIdRazon(null);
        sendReasonCancelation.setOther(other);
        sendReasonCancelation.setToken(SessionManager.getInstance().getSession().getToken());

        if (reasonData == null) {
            if (!isViewNull())
                view.showToast("Espere mientras se cargan las razones");
            return;
        }

        for (ReasonData reason : reasonData) {
            if (reason.isCheck()) {
                sendReasonCancelation.setIdRazon(reason.getId());
                break;
            }
        }

        if (other != null && !other.isEmpty())
            sendReasonCancelation.setIdRazon("otro");

        if (sendReasonCancelation.getIdRazon() == null && (sendReasonCancelation.getOther() == null || sendReasonCancelation.getOther().isEmpty())) {
            if (isViewNull())
                return;
            view.showToast("Error en la Cancelación");
        } else {

            model.cancelSubscription(sendReasonCancelation, this);
        }
    }
}
