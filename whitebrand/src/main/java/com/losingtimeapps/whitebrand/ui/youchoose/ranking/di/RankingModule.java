package com.losingtimeapps.whitebrand.ui.youchoose.ranking.di;

import com.losingtimeapps.whitebrand.app.api.YouChooseApi;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingContract;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingModel;
import com.losingtimeapps.whitebrand.ui.youchoose.ranking.mvp.RankingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 14/10/2017.
 */

@Module
public class RankingModule {

    private RankingContract.View fragment;


    public RankingModule(RankingContract.View fragment) {
        this.fragment = fragment;
    }

    @Provides
    @RankingScope
    public RankingModel provideModel(YouChooseApi youChooseApi) {
        return new RankingModel(youChooseApi);
    }


    @Provides
    @RankingScope
    public RankingPresenter providePresenter(RankingModel model) {

        return new RankingPresenter(fragment, model);

    }

}
