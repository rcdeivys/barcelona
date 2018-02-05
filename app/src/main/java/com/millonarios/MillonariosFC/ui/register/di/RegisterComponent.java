package com.millonarios.MillonariosFC.ui.register.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.register.fragments.RegisterFragment;

import dagger.Component;

/**
 * Created by Amplex on 15/10/2017.
 */

@RegisterScope
@Component(dependencies = {AppComponent.class}, modules = {RegisterModule.class})
public interface RegisterComponent {
    void inject(RegisterFragment registerFragment);
}