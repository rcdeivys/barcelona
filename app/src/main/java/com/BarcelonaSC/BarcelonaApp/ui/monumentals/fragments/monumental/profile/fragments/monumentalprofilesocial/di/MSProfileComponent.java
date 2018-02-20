package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofilesocial.MSProfileFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

@MSProfileScope
@Component(dependencies = {AppComponent.class}, modules = {MSProfileModule.class})
public interface MSProfileComponent {
    void inject(MSProfileFragment fragment);
}