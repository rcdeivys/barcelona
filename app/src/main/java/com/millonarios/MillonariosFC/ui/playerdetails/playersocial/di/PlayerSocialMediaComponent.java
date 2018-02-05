package com.millonarios.MillonariosFC.ui.playerdetails.playersocial.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.playerdetails.playersocial.PlayerSocialMediaFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@PlayerSocialMediaScope
@Component(dependencies = {AppComponent.class}, modules = {PlayerSocialMediaModule.class})
public interface PlayerSocialMediaComponent {
    void inject(PlayerSocialMediaFragment fragment);
}
