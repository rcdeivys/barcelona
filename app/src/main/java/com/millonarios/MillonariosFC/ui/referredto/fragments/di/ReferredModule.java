package com.millonarios.MillonariosFC.ui.referredto.fragments.di;

import com.millonarios.MillonariosFC.app.api.ReferredApi;
import com.millonarios.MillonariosFC.ui.referredto.fragments.ReferredListFragment;
import com.millonarios.MillonariosFC.ui.referredto.fragments.mvp.ReferredModel;
import com.millonarios.MillonariosFC.ui.referredto.fragments.mvp.ReferredPresenter;

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