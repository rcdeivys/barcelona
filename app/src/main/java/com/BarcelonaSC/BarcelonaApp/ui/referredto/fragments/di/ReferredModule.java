package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.di;

import com.BarcelonaSC.BarcelonaApp.app.api.ReferredApi;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.ReferredListFragment;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp.ReferredModel;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.mvp.ReferredPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

@Module
public class ReferredModule {

    private ReferredListFragment fragment;

    public ReferredModule(ReferredListFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @ReferredScope
    public ReferredModel provideModel(ReferredApi referredApi) {
        return new ReferredModel(referredApi);
    }

    @Provides
    @ReferredScope
    public ReferredPresenter providePresenter(ReferredModel model) {
        return new ReferredPresenter(fragment, model);
    }

}