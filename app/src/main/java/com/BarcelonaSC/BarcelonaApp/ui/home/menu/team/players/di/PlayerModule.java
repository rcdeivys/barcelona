package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.di;


import com.BarcelonaSC.BarcelonaApp.app.api.TeamApi;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.PlayerOffSummonedFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.mvp.PlayerModel;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.mvp.PlayerPresenter;

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
