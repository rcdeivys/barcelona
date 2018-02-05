package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.di;


import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.PlayerProfileFragment;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfileModel;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfilePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 11/10/2017.
 */

@Module
public class PlayerProfileModule {

    private PlayerProfileFragment fragment;


    public PlayerProfileModule(PlayerProfileFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @PlayerProfileScope
    public PlayerProfileModel provideModel(TeamApi teamApi) {
        return new PlayerProfileModel(teamApi);
    }


    @Provides
    @PlayerProfileScope
    public PlayerProfilePresenter providePresenter(PlayerProfileModel model) {

        return new PlayerProfilePresenter(fragment, model);

    }

}
