package com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.IdealElevenFragment;

import dagger.Component;

/**
 * Created by Carlos on 13/11/2017.
 */

@IdealElevenScope
@Component(dependencies = {AppComponent.class}, modules = {IdealElevenModule.class})
public interface IdealElevenComponent {
    void inject(IdealElevenFragment fragment);
}
