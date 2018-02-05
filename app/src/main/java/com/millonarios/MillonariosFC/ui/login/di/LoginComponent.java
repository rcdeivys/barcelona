package com.millonarios.MillonariosFC.ui.login.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.login.fragments.AuthFragment;

import dagger.Component;

/**
 * Created by Amplex on 12/10/2017.
 */

@LoginScope
@Component(dependencies = {AppComponent.class}, modules = {LoginModule.class})
public interface LoginComponent {
    void inject(AuthFragment activity);
}
