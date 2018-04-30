package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.di;

import com.BarcelonaSC.BarcelonaApp.app.api.HinchaDoradoApi;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.HinchaDoradoRegister;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp.HinchaDoradoModel;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.mvp.HinchaDoradoPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by cesar on 24/2/2018.
 */

@Module
public class HinchaDoradoModule {

    private HinchaDoradoRegister activity;

    public HinchaDoradoModule(HinchaDoradoRegister activity) {
        this.activity = activity;
    }

    @Provides
    @HinchaDoradoScope
    public HinchaDoradoModel provideGroupsModel(HinchaDoradoApi hinchaDoradoApi, ProfileApi profileApi) {
        return new HinchaDoradoModel(hinchaDoradoApi, profileApi);
    }

    @Provides
    @HinchaDoradoScope
    public HinchaDoradoPresenter providePresenter(HinchaDoradoModel model) {
        return new HinchaDoradoPresenter(activity, model);
    }
}
