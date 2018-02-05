package com.millonarios.MillonariosFC.ui.recovery.send.code.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.recovery.send.code.SendCodeFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@SendCodeScope
@Component(dependencies = {AppComponent.class},modules = {SendCodeModule.class})
public interface SendCodeComponent {

    void inject(SendCodeFragment sendCodeFragment);
}
