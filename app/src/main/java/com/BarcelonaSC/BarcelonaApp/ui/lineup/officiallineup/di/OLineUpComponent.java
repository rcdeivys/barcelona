package com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.OfficialLineUpFragment;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@OLineUpScope
@Component(dependencies = {AppComponent.class}, modules = {OLineUpModule.class})
public interface OLineUpComponent {
    void inject(OfficialLineUpFragment fragment);
}
