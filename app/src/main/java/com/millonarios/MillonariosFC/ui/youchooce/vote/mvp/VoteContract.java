package com.millonarios.MillonariosFC.ui.youchooce.vote.mvp;


import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.EncuestaData;
import com.millonarios.MillonariosFC.models.PlayerVote;
import com.millonarios.MillonariosFC.models.SendChooseVote;

import java.util.List;

/**
 * Created by Carlos on 14/10/2017.
 */

public class VoteContract {

    public interface ModelResultListener {

        void onGetChooseSuccess(EncuestaData mEncuestaData);

        void onGetChooseFailed();

        void onsetVotesSuccess();

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getChoose();

        void setChooseVote(SendChooseVote sendChooseVote);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void setChooseData(EncuestaData mEncuestaData);

        void showNoEncuentas();

        void navigateToChooseProfileActivity(int id, boolean showVotes);

    }

}
