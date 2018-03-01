package com.BarcelonaSC.BarcelonaApp.ui.register.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;

import dagger.Component;

/**
 * Created by Amplex on 15/10/2017.
 */

@RegisterScope
@Component(dependencies = {AppComponent.class}, modules = {RegisterModule.class})
public interface RegisterComponent {
    void inject(RegisterFragment registerFragment);
}