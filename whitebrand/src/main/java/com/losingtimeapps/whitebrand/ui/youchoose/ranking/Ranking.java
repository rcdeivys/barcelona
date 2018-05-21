package com.losingtimeapps.whitebrand.ui.youchoose.ranking;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.di.DaggerRankingComponent;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.di.RankingModule;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingPresenter;

import javax.inject.Inject;

/**
 * Created by Pedro Gomez on 21/05/2018.
 */

public class Ranking {

    @Inject
    public RankingPresenter rankingPresenter;

    public RankingPresenter initRanking(RankingContract.View view) {
        if (rankingPresenter == null) {
            DaggerRankingComponent.builder()
                    .appComponent(WhiteBrand.getComponent())
                    .rankingModule(new RankingModule(view))
                    .build().inject(this);
        }
        return rankingPresenter;

    }
}
