package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp.MonumentalModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.MonumentalRankingFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp.MonumentalRankModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp.MonumentalRankPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 18/02/2018.
 */

@Module
public class MonumentalRankModule {

    private MonumentalRankingFragment fragment;

    public MonumentalRankModule(MonumentalRankingFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MonumentalRankScope
    public MonumentalRankingFragment provideMonumentalRank() {
        return fragment;
    }

    @Provides
    @MonumentalRankScope
    public MonumentalRankModel provideMonumentalRankModel(MonumentalApi api) {
        return new MonumentalRankModel(api);
    }

    @Provides
    @MonumentalRankScope
    public MonumentalRankPresenter provideMonumentalRankPresenter(MonumentalRankModel model) {
        return new MonumentalRankPresenter(fragment, model);
    }

}