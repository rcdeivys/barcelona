package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.playersocial.PlayerSocialMediaFragment;

import dagger.Component;

/**
 * Created by Carlos on 11/10/2017.
 */

@PlayerSocialMediaScope
@Component(dependencies = {AppComponent.class}, modules = {PlayerSocialMediaModule.class})
public interface PlayerSocialMediaComponent {
    void inject(PlayerSocialMediaFragment fragment);
}
