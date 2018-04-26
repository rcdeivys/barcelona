package com.BarcelonaSC.BarcelonaApp.ui.home.di;

import com.BarcelonaSC.BarcelonaApp.app.api.HomeApi;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.mvp.HomePresenter;
import com.BarcelonaSC.BarcelonaApp.ui.home.mvp.HomeModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 01/11/2017.
 */

@Module
public class HomeModule {

    private HomeActivity activity;

    public HomeModule(HomeActivity activity) {
        this.activity = activity;
    }


    @Provides
    @HomeScope
    public HomeActivity provideActivity() {
        return activity;
    }

    @Provides
    @HomeScope
    public HomeModel provideHomeModel(HomeApi homeApi) {
        return new HomeModel(homeApi);
    }

    @Provides
    @HomeScope
    public HomePresenter provideHomePresenter(HomeModel model) {
        return new HomePresenter(activity, model);
    }
}