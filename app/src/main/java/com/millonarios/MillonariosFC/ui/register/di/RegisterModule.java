package com.millonarios.MillonariosFC.ui.register.di;

import com.millonarios.MillonariosFC.app.api.AuthApi;
import com.millonarios.MillonariosFC.ui.register.mvp.RegisterModel;
import com.millonarios.MillonariosFC.app.api.ProfileApi;
import com.millonarios.MillonariosFC.ui.register.fragments.RegisterFragment;
import com.millonarios.MillonariosFC.ui.register.mvp.RegisterPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Erick on 14/10/2017.
 */

@Module
public class RegisterModule {

    private RegisterFragment registerFragment;

    public RegisterModule(RegisterFragment registerFragment) {
        this.registerFragment = registerFragment;
    }

    @Provides
    @RegisterScope
    public RegisterFragment provideActivity() {
        return this.registerFragment;
    }

    @Provides
    @RegisterScope
    public RegisterModel provideRegisterModel(AuthApi registerApi, ProfileApi profileApi) {
        return new RegisterModel(registerApi, profileApi);
    }

    @Provides
    @RegisterScope
    public RegisterPresenter providePresenter(RegisterModel model) {
        return new RegisterPresenter(registerFragment, model);
    }

}