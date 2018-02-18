package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.MonumentalFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

@MonumentalScope
@Component(dependencies = {AppComponent.class}, modules = {MonumentalModule.class})
public interface MonumentalComponent {
    void inject(MonumentalFragment fragment);
}
