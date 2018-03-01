package com.BarcelonaSC.BarcelonaApp.ui.home.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;

import dagger.Component;

/**
 * Created by Carlos on 01/11/2017.
 */

@HomeScope
@Component(dependencies = {AppComponent.class}, modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity activity);
}
