package com.BarcelonaSC.BarcelonaApp.ui.geolocation.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.MapActivity;

import dagger.Component;

/**
 * Created by RYA-Laptop on 16/04/2018.
 */

@MapScope
@Component(dependencies = {AppComponent.class}, modules = {MapModule.class})
public interface MapComponent {
    void inject(MapActivity activity);
}