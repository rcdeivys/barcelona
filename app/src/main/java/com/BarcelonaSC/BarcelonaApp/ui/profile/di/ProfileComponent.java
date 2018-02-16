package com.BarcelonaSC.BarcelonaApp.ui.profile.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.profile.ProfileFragment;

import dagger.Component;

/**
 * Created by Erick on 11/10/2017.
 */

@ProfileScope
@Component(dependencies = {AppComponent.class}, modules = {ProfileModule.class})
public interface ProfileComponent {
    void inject(ProfileFragment profileFragment);
}