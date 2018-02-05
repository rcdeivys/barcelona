package com.millonarios.MillonariosFC.ui.recovery.send.password.di;

import com.millonarios.MillonariosFC.ui.recovery.send.password.SendPasswordFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@SendPasswordScope
@Component(modules = {SendPasswordModule.class})
public interface SendPasswordComponent {
    void inject(SendPasswordFragment sendPasswordFragment);
}
