package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.di;


import com.BarcelonaSC.BarcelonaApp.app.api.TeamApi;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.mvp.ApplaudedModel;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.mvp.ApplaudedPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.ApplaudedFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 14/10/2017.
 */

@Module
public class ApplaudedModule {

    private ApplaudedFragment fragment;


    public ApplaudedModule(ApplaudedFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ApplaudedScope
    public ApplaudedModel provideModel(TeamApi teamApi) {
        return new ApplaudedModel(teamApi);
    }


    @Provides
    @ApplaudedScope
    public ApplaudedPresenter providePresenter(ApplaudedModel model) {

        return new ApplaudedPresenter(fragment, model);

    }

}
