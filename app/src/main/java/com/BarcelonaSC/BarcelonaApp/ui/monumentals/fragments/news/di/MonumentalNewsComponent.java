package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.MonumentalNewsFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 15/02/2018.
 */

@MonumentalNewsScope
@Component(dependencies = {AppComponent.class}, modules = {MonumentalNewsModule.class})
public interface MonumentalNewsComponent {
    void inject(MonumentalNewsFragment fragment);
}