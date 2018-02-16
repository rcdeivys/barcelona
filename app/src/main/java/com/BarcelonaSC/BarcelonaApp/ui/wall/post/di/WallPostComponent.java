package com.BarcelonaSC.BarcelonaApp.ui.wall.post.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePostActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/24/18.
 */

@WallPostScope
@Component(dependencies = {AppComponent.class}, modules = {WallPostModule.class})
public interface WallPostComponent {
    void inject(WallCreatePostActivity wallCreatePostActivity);
}
