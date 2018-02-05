package com.millonarios.MillonariosFC.ui.playerdetails.playersocial.di;

import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfileModel;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfilePresenter;
import com.millonarios.MillonariosFC.ui.playerdetails.playersocial.PlayerSocialMediaFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 11/10/2017.
 */

@Module
public class PlayerSocialMediaModule {

    private PlayerSocialMediaFragment fragment;


    public PlayerSocialMediaModule(PlayerSocialMediaFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PlayerSocialMediaScope
    public PlayerProfileModel provideModel(TeamApi teamApi) {
        return new PlayerProfileModel(teamApi);
    }


    @Provides
    @PlayerSocialMediaScope
    public PlayerProfilePresenter providePresenter(PlayerProfileModel model) {

        return new PlayerProfilePresenter(fragment, model);

    }

}
