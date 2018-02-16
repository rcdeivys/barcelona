package com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp.SettingsFragment;

import dagger.Component;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

@SettingsScope
@Component(dependencies = {AppComponent.class}, modules = {SettingsModule.class})
public interface SettingsComponent {
    void inject(SettingsFragment settingsFragment);
}