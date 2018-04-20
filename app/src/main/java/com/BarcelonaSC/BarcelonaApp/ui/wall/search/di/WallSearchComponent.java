package com.BarcelonaSC.BarcelonaApp.ui.wall.search.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.WallSearchFragment;

import dagger.Component;

/**
 * Created by Cesar on 17/01/2018.
 */

@WallSearchScope
@Component(dependencies = {AppComponent.class}, modules = {WallSearchModule.class})
public interface WallSearchComponent {
    void inject(WallSearchFragment fragment);
}
