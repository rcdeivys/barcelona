package com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.PlayByPlay;

/**
 * Created by Carlos on 13/11/2017.
 */

public class OLineUpContract {


    public interface ModelResultListener {


        void onGetPlayByPlaySuccess(PlayByPlay data);

        void onError(String error);
    }

    public interface Presenter extends MVPContract.Presenter<View> {


        boolean isViewNull();

        void onClickItem(int position,boolean isTitular);

        void onClickHeader();
    }

    public interface View {

        void showProgress();

        void hideProgress();

        void setRefreshing(boolean state);

        void showToast(String message);

        void setPlayByPlayData(PlayByPlay playByPlayData);

        void navigateToProfilePlayerActivity(String playerId);

        void setVideo(String url,String info);
    }
}
