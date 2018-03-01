package com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.di;

import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.app.api.RecoveryPasswordApi;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.SendCodeFragment;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv.SendCodeContract;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv.SendCodeModel;
import com.BarcelonaSC.BarcelonaApp.ui.recovery.send.code.mpv.SendCodePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@Module
public class SendCodeModule {


    private SendCodeFragment sendCodeFragment;

    public SendCodeModule(SendCodeFragment sendCodeFragment) {
        this.sendCodeFragment = sendCodeFragment;
    }

    @Provides
    @SendCodeScope
    public SendCodeContract.View provideActivity() {
        return sendCodeFragment;
    }

    @Provides
    @SendCodeScope
    public SendCodeModel providModel(RecoveryPasswordApi recoveryPasswordApi, ProfileApi profileApi) {
        return new SendCodeModel(recoveryPasswordApi, profileApi);
    }

    @Provides
    @SendCodeScope
    public SendCodePresenter providePresenter(SendCodeModel model) {
        return new SendCodePresenter(sendCodeFragment, model);
    }
}
