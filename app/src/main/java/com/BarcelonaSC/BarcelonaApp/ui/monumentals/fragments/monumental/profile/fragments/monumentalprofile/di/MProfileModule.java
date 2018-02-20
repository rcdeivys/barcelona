package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.MProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfileModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfilePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

@Module
public class MProfileModule {

    private MProfileFragment fragment;

    public MProfileModule(MProfileFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MProfileScope
    public MProfileFragment provideMProfile() {
        return fragment;
    }

    @Provides
    @MProfileScope
    public MProfileModel provideMProfileModel(MonumentalApi api) {
        return new MProfileModel(api);
    }

    @Provides
    @MProfileScope
    public MProfilePresenter provideMProfilePresenter(MProfileModel model) {
        return new MProfilePresenter(fragment, model);
    }

}