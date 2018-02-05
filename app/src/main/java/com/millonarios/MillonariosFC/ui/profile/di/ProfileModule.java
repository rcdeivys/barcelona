package com.millonarios.MillonariosFC.ui.profile.di;

import com.millonarios.MillonariosFC.ui.home.menu.profile.ProfileFragment;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfileContract;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfileModel;
import com.millonarios.MillonariosFC.ui.profile.mvp.ProfilePresenter;
import com.millonarios.MillonariosFC.app.api.ProfileApi;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Amplex on 11/10/2017.
 */

@Module
public class ProfileModule {

    private ProfileFragment profileFragment;

    public ProfileModule(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
    }

    @Provides
    @ProfileScope
    public ProfileContract.View provideActivity() {
        return profileFragment;
    }

    @Provides
    @ProfileScope
    public ProfileModel provideProfileModel(ProfileApi profileApi) {
        return new ProfileModel(profileApi);
    }

    @Provides
    @ProfileScope
    public ProfilePresenter providePresenter(ProfileModel model) {
        return new ProfilePresenter(profileFragment, model);
    }

}