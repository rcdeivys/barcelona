package com.millonarios.MillonariosFC.ui.home.menu.team.players.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.home.menu.team.players.PlayerOffSummonedFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@PlayerScope
@Component(dependencies = {AppComponent.class}, modules = {PlayerModule.class})
public interface PlayerComponent {
    void inject(PlayerOffSummonedFragment fragment);
}
