package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.MonumentalFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp.MonumentalModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp.MonumentalPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

@Module
public class MonumentalModule {

    private MonumentalFragment fragment;

    public MonumentalModule(MonumentalFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MonumentalScope
    public MonumentalFragment provideMonumental() {
        return fragment;
    }

    @Provides
    @MonumentalScope
    public MonumentalModel provideMonumentalModel(MonumentalApi api) {
        return new MonumentalModel(api);
    }

    @Provides
    @MonumentalScope
    public MonumentalPresenter provideMonumentalPresenter(MonumentalModel model) {
        return new MonumentalPresenter(fragment, model);
    }

}