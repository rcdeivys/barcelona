package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;

import java.util.List;

/**
 * Created by RYA-Laptop on 18/02/2018.
 */

public class MonumentalRankContract {

    public interface ModelResultListener {
        void onGetMRankSuccess(List<MonumentalRankingItem> rankingItemList);

        void onGetMRankFailed();
    }

    public interface Presenter extends MVPContract.Presenter<View> {
        void loadMonumentalRank();

        void cancel();

        void reset();
    }

    public interface View {
        void showRankingList(List<MonumentalRankingItem> rankingItemList);
    }

}