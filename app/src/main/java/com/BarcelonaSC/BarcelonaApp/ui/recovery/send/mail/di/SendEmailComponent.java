package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.mail.SendEmailFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@SendEmailScope
@Component(dependencies = {AppComponent.class}, modules = {SendEmailModule.class})
public interface SendEmailComponent {
    void inject(SendEmailFragment sendEmailFragment);
}
