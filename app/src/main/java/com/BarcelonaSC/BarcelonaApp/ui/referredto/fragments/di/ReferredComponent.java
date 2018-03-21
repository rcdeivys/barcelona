package com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.referredto.fragments.ReferredListFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

@ReferredScope
@Component(dependencies = {AppComponent.class}, modules = {ReferredModule.class})
public interface ReferredComponent {
    void inject(ReferredListFragment activity);
}