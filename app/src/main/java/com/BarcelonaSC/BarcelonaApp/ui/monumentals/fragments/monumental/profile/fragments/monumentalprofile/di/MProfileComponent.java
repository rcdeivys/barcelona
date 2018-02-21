package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.MProfileFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

@MProfileScope
@Component(dependencies = {AppComponent.class}, modules = {MProfileModule.class})
public interface MProfileComponent {
    void inject(MProfileFragment fragment);
}