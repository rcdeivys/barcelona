package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.PlayerProfileFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@PlayerProfileScope
@Component(dependencies = {AppComponent.class}, modules = {PlayerProfileModule.class})
public interface PlayerProfileComponent {
    void inject(PlayerProfileFragment fragment);
}
