package com.losingtimeapps.whitebrand.ui.youchoose.vote.mvp;


import com.losingtimeapps.whitebrand.commons.mvp.MVPContract;
import com.losingtimeapps.whitebrand.models.EncuestaData;
import com.losingtimeapps.whitebrand.models.SendChooseVote;

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

        void setChooseVote(SendChooseVote sendChooseVote, int msj);

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
