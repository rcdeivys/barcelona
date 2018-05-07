package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.di;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp.SinglePostModel;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp.SinglePostPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@Module
public class SinglePostModule {

    private SinglePostActivity activity;

    public SinglePostModule(SinglePostActivity activity) {
        this.activity = activity;
    }

    @Provides
    @SinglePostScope
    public SinglePostActivity provideFragment() {
        return activity;
    }

    @Provides
    @SinglePostScope
    public SinglePostModel provideModel(WallApi wallApi) {
        return new SinglePostModel(wallApi);
    }


    @Provides
    @SinglePostScope
    public SinglePostPresenter providePresenter(SinglePostModel model) {
        return new SinglePostPresenter(activity, model);
    }
}
