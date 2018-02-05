package com.millonarios.MillonariosFC.ui.home.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.home.HomeActivity;

import dagger.Component;

/**
 * Created by Carlos on 01/11/2017.
 */

@HomeScope
@Component(dependencies = {AppComponent.class}, modules = {HomeModule.class})
public interface HomeComponent {
    void inject(HomeActivity activity);
}
