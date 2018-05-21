package com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp;

import com.losingtimeapps.whitebrand.commons.mvp.MVPContract;
import com.losingtimeapps.whitebrand.models.RespuestaData;

import java.util.List;

/**
 * Created by Carlos on 14/10/2017.
 */

public class RankingContract {

    public interface ModelResultListener {

        void onGetRankingVotesSuccess(List<RespuestaData> mRespuestasData);

        void noShowVotes();

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void getRanking();

        void getRanking(boolean show);

        boolean isViewNull();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void noShowVotes();

        void setRefreshing(boolean state);

        void showToastError(String error);

        void setVotesData(List<RespuestaData> mPlayersVotes);

    }

}
