package com.millonarios.MillonariosFC.ui.referredto.fragments.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.referredto.fragments.ReferredListFragment;

import dagger.Component;

/**
 * Created by RYA-Laptop on 05/01/2018.
 */

@ReferredScope
@Component(dependencies = {AppComponent.class}, modules = {ReferredModule.class})
public interface ReferredComponent {
    void inject(ReferredListFragment activity);
}