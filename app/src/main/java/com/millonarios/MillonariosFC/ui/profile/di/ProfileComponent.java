package com.millonarios.MillonariosFC.ui.profile.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.home.menu.profile.ProfileFragment;

import dagger.Component;

/**
 * Created by Erick on 11/10/2017.
 */

@ProfileScope
@Component(dependencies = {AppComponent.class}, modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileFragment profileFragment);
}