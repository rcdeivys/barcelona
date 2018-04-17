package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.VideoFragment;

import dagger.Component;

/**
 * Created by Deivys on 3/29/2018.
 */

@VideoScope
@Component(dependencies = {AppComponent.class}, modules = {VideoModule.class})
public interface VideoComponent {
    void inject(VideoFragment videoFragment);
}
