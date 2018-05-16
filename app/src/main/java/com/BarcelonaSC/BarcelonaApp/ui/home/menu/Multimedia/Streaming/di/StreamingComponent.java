package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.StreamingFragment;

import dagger.Component;

/**
 * Created by Deivys on 4/16/2018.
 */

@StreamingScope
@Component(dependencies = {AppComponent.class}, modules = {StreamingModule.class})
public interface StreamingComponent {
    void inject(StreamingFragment streamingFragment);
}
