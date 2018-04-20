package com.BarcelonaSC.BarcelonaApp.ui.wall.search.di;

import com.BarcelonaSC.BarcelonaApp.app.api.WallSearchApi;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.WallSearchFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp.WallSearchModel;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp.WallSearchPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Cesar on 17/01/2018.
 */

@Module
public class WallSearchModule {

    private WallSearchFragment wallSearchFragment = null;

    public WallSearchModule(WallSearchFragment fragment) {
        this.wallSearchFragment = fragment;
    }

    @Provides
    @WallSearchScope
    public WallSearchModel provideModel(WallSearchApi api) {
        return new WallSearchModel(api);
    }

    @Provides
    @WallSearchScope
    public WallSearchPresenter providePresenter(WallSearchModel model) {
        //TODO: pendiente con esto
        return new WallSearchPresenter(wallSearchFragment, model);
    }

}
