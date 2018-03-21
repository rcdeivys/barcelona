package com.BarcelonaSC.BarcelonaApp.ui.login.di;


import com.BarcelonaSC.BarcelonaApp.app.api.AuthApi;
import com.BarcelonaSC.BarcelonaApp.ui.login.fragments.AuthFragment;
import com.BarcelonaSC.BarcelonaApp.app.api.ProfileApi;
import com.BarcelonaSC.BarcelonaApp.ui.login.mvp.LoginModel;
import com.BarcelonaSC.BarcelonaApp.ui.login.mvp.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Erick on 12/10/2017.
 */

@Module
public class LoginModule {

    private AuthFragment authFragment;

    public LoginModule(AuthFragment authFragment) {
        this.authFragment = authFragment;
    }

    @Provides
    @LoginScope
    public AuthFragment provideActivity() {
        return authFragment;
    }

    @Provides
    @LoginScope
    public LoginModel provideLoginModel(AuthApi authApi, ProfileApi profileApi) {
        return new LoginModel(authApi, profileApi);
    }

    @Provides
    @LoginScope
    public LoginPresenter providePresenter(LoginModel model) {
        return new LoginPresenter(authFragment, model);
    }

}