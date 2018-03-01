package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.SendCodeFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@SendCodeScope
@Component(dependencies = {AppComponent.class},modules = {SendCodeModule.class})
public interface SendCodeComponent {

    void inject(SendCodeFragment sendCodeFragment);
}
