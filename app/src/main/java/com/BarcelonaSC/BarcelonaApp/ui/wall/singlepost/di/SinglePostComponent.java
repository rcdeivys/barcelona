package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@SinglePostScope
@Component(dependencies = {AppComponent.class}, modules = {SinglePostModule.class})
public interface SinglePostComponent {
    void inject(SinglePostActivity activity);
}
