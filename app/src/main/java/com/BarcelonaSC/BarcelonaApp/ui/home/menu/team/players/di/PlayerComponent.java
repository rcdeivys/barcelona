package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.PlayerOffSummonedFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@PlayerScope
@Component(dependencies = {AppComponent.class}, modules = {PlayerModule.class})
public interface PlayerComponent {
    void inject(PlayerOffSummonedFragment fragment);
}
