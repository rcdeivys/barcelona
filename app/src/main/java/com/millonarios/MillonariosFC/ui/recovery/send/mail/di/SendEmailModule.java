package com.millonarios.MillonariosFC.ui.recovery.send.mail.di;

import com.millonarios.MillonariosFC.ui.recovery.send.mail.SendEmailFragment;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp.SendEmailContract;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp.SendEmailModel;
import com.millonarios.MillonariosFC.app.api.RecoveryPasswordApi;
import com.millonarios.MillonariosFC.ui.recovery.send.mail.mvp.SendEmailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 11/12/17.
 */

@Module
public class SendEmailModule {

    private SendEmailFragment sendEmailFragment;

    public SendEmailModule(SendEmailFragment sendEmailFragment) {
        this.sendEmailFragment = sendEmailFragment;
    }

    @Provides
    @SendEmailScope
    public SendEmailContract.View provideActivity() {
        return sendEmailFragment;
    }

    @Provides
    @SendEmailScope
    public SendEmailModel providModel(RecoveryPasswordApi recoveryPasswordApi) {
        return new SendEmailModel(recoveryPasswordApi);
    }

    @Provides
    @SendEmailScope
    public SendEmailPresenter providePresenter(SendEmailModel model) {
        return new SendEmailPresenter(sendEmailFragment, model);
    }
}
