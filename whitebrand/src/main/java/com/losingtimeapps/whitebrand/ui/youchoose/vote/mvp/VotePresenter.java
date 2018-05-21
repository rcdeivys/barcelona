package com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp;


import com.losingtimeapps.whitebrand.evenbus.ChooseOpenEvent;
import com.losingtimeapps.whitebrand.evenbus.ChooseUpdateRanEvent;
import com.losingtimeapps.whitebrand.models.EncuestaData;
import com.losingtimeapps.whitebrand.models.SendChooseVote;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Carlos on 14/10/2017.
 */

public class VotePresenter implements VoteContract.Presenter, VoteContract.ModelResultListener {

    private static final String TAG = VotePresenter.class.getSimpleName();
    private VoteContract.View view;
    private VoteModel model;
    private EncuestaData mEncuestaData;

    public VotePresenter(VoteContract.View view, VoteModel model) {
        this.view = view;
        this.model = model;
    }


    @Override
    public void onAttach(VoteContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }

    @Override
    public void getChoose() {
        model.getChooseData(this);
    }

    @Override
    public void setChooseVote(SendChooseVote sendChooseVote, int msj) {
        model.setPlayersVotes(sendChooseVote, this, msj);
    }

    public void onClickPlayerVote(int position, int msj) {
        //Log.i(TAG, "/--->onClickPlayerVote PRESENTER");
        /*if (mEncuestaData.getPuedevotar() == 0) {
            onError("No puedes votar en esta encuesta");
            return;
        } else if ("1".equals(mEncuestaData.getRespuestas().get(position).getYaVoto())) {
            onError("Ya votaste por esa opcion");
            return;
        }*/
        SendChooseVote sendChooseVote = new SendChooseVote();
        sendChooseVote.setIdEncuesta(mEncuestaData.getIdencuesta());
        sendChooseVote.setIdRespuesta(mEncuestaData.getRespuestas().get(position).getIdrespuesta());
        setChooseVote(sendChooseVote,msj);
    }

    @Override
    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void onGetChooseSuccess(EncuestaData mEncuestaData) {

        this.mEncuestaData = mEncuestaData;
        if (isViewNull()) return;
        view.setChooseData(this.mEncuestaData);
        EventBus.getDefault().post(new ChooseOpenEvent("0", true));
    }

    @Override
    public void onGetChooseFailed() {
        if (isViewNull()) return;
        view.showNoEncuentas();
        EventBus.getDefault().post(new ChooseOpenEvent("0", false));
    }

    @Override
    public void onsetVotesSuccess(int id, int msj) {
        getChoose();
        EventBus.getDefault().post(new ChooseUpdateRanEvent(true));
        if( msj == 1 ) {
            view.showShareVote(id);
            view.showToastError("Voto Registrado");
            EventBus.getDefault().post(new ChooseOpenEvent("2", true));
        } else {
            view.showToastError("Se ha borrado tu voto");
        }
    }

    @Override
    public void onError(String error) {
        if (isViewNull()) return;
        view.showToastError(error);

    }

    public boolean canSeeVotes() {
        return mEncuestaData.getPuedevervotos() == 1;
    }

    public void onFinishEncuesta() {
        EventBus.getDefault().post(new ChooseOpenEvent("2"));

    }

    public void onclickItem(int id) {
        if (isViewNull()) return;
        view.navigateToChooseProfileActivity(id, canSeeVotes());
    }
}
