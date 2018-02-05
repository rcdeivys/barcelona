package com.millonarios.MillonariosFC.ui.home.menu.settings.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.home.menu.settings.mvp.SettingsFragment;

import dagger.Component;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

@SettingsScope
@Component(dependencies = {AppComponent.class}, modules = {SettingsModule.class})
public interface SettingsComponent {
    void inject(SettingsFragment settingsFragment);
}