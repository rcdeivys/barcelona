package com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.di;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp.SettingsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp.SettingsModel;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.settings.mvp.SettingsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by JulianOtalora on 17/11/2017.
 */

@Module
public class SettingsModule {

    private SettingsFragment settingsFragment;

    public SettingsModule(SettingsFragment settingsFragment) {
        this.settingsFragment = settingsFragment;
    }

    @Provides
    @SettingsScope
    public SettingsFragment provideActivity() {
        return this.settingsFragment = settingsFragment;
    }

    @Provides
    @SettingsScope
    public SettingsModel provideSettingsModel(SessionManager sessionManager) {
        return new SettingsModel(sessionManager);
    }

    @Provides
    @SettingsScope
    public SettingsPresenter providePresenter(SettingsModel model) {
        return new SettingsPresenter(settingsFragment, model);
    }

}