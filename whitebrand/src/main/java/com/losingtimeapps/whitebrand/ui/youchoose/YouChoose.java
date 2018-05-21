package com.losingtimeapps.whitebrand.ui.youchoose;


import android.support.v4.app.Fragment;

import com.losingtimeapps.whitebrand.app.WhiteBrand;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.di.ChooseProfileModule;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfileContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ChooseProfile.mvp.ChooseProfilePresenter;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.di.RankingModule;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingPresenter;

import javax.inject.Inject;

/**
 * Created by Pedro Gomez on 18/05/2018.
 */

public class YouChoose extends Fragment{

    @Inject
    public RankingPresenter rankingPresenter;
    @Inject
    public ChooseProfilePresenter chooseProfilePresenter;

    public RankingPresenter initRanking(RankingContract.View view) {
        if (rankingPresenter == null) {
            DaggerYouChooseComponent.builder()
                    .appComponent(WhiteBrand.getComponent())
                    .rankingModule(new RankingModule(view))
                    .build().inject(this);
        }
        return rankingPresenter;

    }
    public ChooseProfilePresenter initProfile(ChooseProfileContract.View view) {
        if (chooseProfilePresenter == null) {
            DaggerYouChooseComponent.builder()
                    .appComponent(WhiteBrand.getComponent())
                    .chooseProfileModule(new ChooseProfileModule(view))
                    .build().inject(this);
        }
        return chooseProfilePresenter;

    }

}