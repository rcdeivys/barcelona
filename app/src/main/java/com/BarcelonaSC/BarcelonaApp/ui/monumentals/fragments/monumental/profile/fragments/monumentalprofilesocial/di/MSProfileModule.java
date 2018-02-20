package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MonumentalApi;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.MSProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp.MSProfileModel;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.mvp.MSProfilePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */
@Module
public class MSProfileModule {

    private MSProfileFragment fragment;

    public MSProfileModule(MSProfileFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MSProfileScope
    public MSProfileFragment provideMSProfile() {
        return fragment;
    }

    @Provides
    @MSProfileScope
    public MSProfileModel provideMSProfileModel(MonumentalApi api) {
        return new MSProfileModel(api);
    }

    @Provides
    @MSProfileScope
    public MSProfilePresenter provideMSProfilePresenter(MSProfileModel model) {
        return new MSProfilePresenter(fragment, model);
    }

}