package com.BarcelonaSC.BarcelonaApp.ui.virtualreality.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VRFragment;

import dagger.Component;

/**
 * Created by Carlos-pc on 04/10/2017.
 */
@VRScope
@Component(dependencies = {AppComponent.class}, modules = {VRModule.class})
public interface VRComponent {
    void inject(VRFragment fragment);
}
