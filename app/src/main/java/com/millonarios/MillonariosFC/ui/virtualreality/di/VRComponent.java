package com.millonarios.MillonariosFC.ui.virtualreality.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.virtualreality.VRFragment;

import dagger.Component;

/**
 * Created by Carlos-pc on 04/10/2017.
 */
@VRScope
@Component(dependencies = {AppComponent.class}, modules = {VRModule.class})
public interface VRComponent {
    void inject(VRFragment fragment);
}
