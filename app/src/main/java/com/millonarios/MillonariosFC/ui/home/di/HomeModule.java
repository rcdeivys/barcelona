package com.millonarios.MillonariosFC.ui.home.di;

import com.millonarios.MillonariosFC.ui.home.HomeActivity;
import com.millonarios.MillonariosFC.ui.home.mvp.HomePresenter;
import com.millonarios.MillonariosFC.ui.home.mvp.HomeModel;

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
    public HomeModel provideHomeModel() {
        return new HomeModel();
    }


    @Provides
    @HomeScope
    public HomePresenter provideHomePresenter(HomeModel model) {
        return new HomePresenter(activity, model);
    }
}
