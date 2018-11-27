package com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.mvp;


import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.EncuestaData;
import com.BarcelonaSC.BarcelonaApp.models.SendChooseVote;

/**
 * Created by Carlos on 14/10/2017.
 */

public class VoteContract {

    public interface ModelResultListener {

        void onGetChooseSuccess(EncuestaData mEncuestaData);

        void onGetChooseFailed();
      
        void onsetVotesSuccess(int id, int msj);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getChoose();

        void setChooseVote(SendChooseVote sendChooseVote,int msj);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void setChooseData(EncuestaData mEncuestaData);

        void showNoEncuentas();

        void showShareVote(int id);

        void navigateToChooseProfileActivity(int id, boolean showVotes);

    }

}
