package com.millonarios.MillonariosFC.ui.home.menu.team.players.di;


import com.millonarios.MillonariosFC.app.api.TeamApi;
import com.millonarios.MillonariosFC.ui.home.menu.team.players.PlayerOffSummonedFragment;
import com.millonarios.MillonariosFC.ui.home.menu.team.players.mvp.PlayerModel;
import com.millonarios.MillonariosFC.ui.home.menu.team.players.mvp.PlayerPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Carlos on 11/10/2017.
 */

@Module
public class PlayerModule {

    private PlayerOffSummonedFragment fragment;
    private String type;

    public PlayerModule(PlayerOffSummonedFragment fragment, String type) {
        this.fragment = fragment;
        this.type = type;
    }

    @Provides
    @PlayerScope
    public PlayerOffSummonedFragment provideFragment() {
        return fragment;
    }

    @Provides
    @PlayerScope
    public PlayerModel provideModel(TeamApi teamApi) {
        return new PlayerModel(teamApi);
    }


    @Provides
    @PlayerScope
    public PlayerPresenter providePresenter(PlayerModel model) {
        return new PlayerPresenter(fragment, model, type);
    }

}
