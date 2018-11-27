package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.di;

import com.BarcelonaSC.BarcelonaApp.app.api.LineUpApi;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.IdealElevenRankingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp.IdealElevenRankingModel;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp.IdealElevenRankingPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 13/11/2017.
 */
@Module
public class IdealElevenRankingModule {

    private IdealElevenRankingFragment fragment;


    public IdealElevenRankingModule(IdealElevenRankingFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @IdealElevenRankingScope
    public IdealElevenRankingFragment provideFragment() {
        return fragment;
    }

    @Provides
    @IdealElevenRankingScope
    public IdealElevenRankingModel provideModel(LineUpApi lineUpApi) {
        return new IdealElevenRankingModel(lineUpApi);
    }


    @Provides
    @IdealElevenRankingScope
    public IdealElevenRankingPresenter providePresenter(IdealElevenRankingModel model) {
        return new IdealElevenRankingPresenter(fragment, model);
    }

}
