package com.BarcelonaSC.BarcelonaApp.ui.geolocation.di;

import com.BarcelonaSC.BarcelonaApp.app.api.MapApi;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.MapActivity;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp.MapModel;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp.MapPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RYA-Laptop on 16/04/2018.
 */


@Module
public class MapModule {

    private MapActivity activity;

    public MapModule(MapActivity activity) {
        this.activity = activity;
    }

    @Provides
    @MapScope
    public MapActivity provideActivity() {
        return activity;
    }

    @Provides
    @MapScope
    public MapModel provideMapModel(MapApi api) {
        return new MapModel(api);
    }

    @Provides
    @MapScope
    public MapPresenter provideMapPresenter(MapModel model) {
        return new MapPresenter(activity, model);
    }

}