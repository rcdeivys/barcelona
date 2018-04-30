package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.HinchaDoradoRegister;

import dagger.Component;

/**
 * Created by cesar on 24/2/2018.
 */

@HinchaDoradoScope
@Component(dependencies = {AppComponent.class}, modules = {HinchaDoradoModule.class})
public interface HinchaDoradoComponent {
    void inject(HinchaDoradoRegister activity);
}
