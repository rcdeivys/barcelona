package com.BarcelonaSC.BarcelonaApp.ui.wall.list.di;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.WallFragmentList;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp.WallModel;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.mvp.WallPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 1/10/18.
 */

@Module
public class WallModule {

    private WallFragmentList fragment;

    public WallModule(WallFragmentList fragment) {
        this.fragment = fragment;
    }

    @Provides
    @WallScope
    public WallFragmentList provideFragment() {
        return fragment;
    }

    @Provides
    @WallScope
    public WallModel provideModel(WallApi api) {
        return new WallModel(api);
    }


    @Provides
    @WallScope
    public WallPresenter providePresenter(WallModel model) {
        return new WallPresenter(fragment, model);
    }

}
