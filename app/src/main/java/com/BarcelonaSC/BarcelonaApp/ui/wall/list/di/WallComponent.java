package com.BarcelonaSC.BarcelonaApp.ui.wall.list.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.WallFragmentList;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/10/18.
 */
@WallScope
@Component(dependencies = {AppComponent.class}, modules = {WallModule.class})
public interface WallComponent {
    void inject(WallFragmentList wallFragmentList);
}
