package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ranking.di;

import com.BarcelonaSC.BarcelonaApp.app.api.YouChooseApi;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ranking.RankingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ranking.mvp.RankingModel;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ranking.mvp.RankingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 14/10/2017.
 */

@Module
public class RankingModule {

    private RankingFragment fragment;


    public RankingModule(RankingFragment fragment) {
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
