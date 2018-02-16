package com.BarcelonaSC.BarcelonaApp.ui.login.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.login.fragments.AuthFragment;

import dagger.Component;

/**
 * Created by Amplex on 12/10/2017.
 */

@LoginScope
@Component(dependencies = {AppComponent.class}, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(AuthFragment activity);
}
