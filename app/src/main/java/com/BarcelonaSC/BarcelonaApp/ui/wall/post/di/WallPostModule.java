package com.BarcelonaSC.BarcelonaApp.ui.wall.post.di;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePostActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostModel;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 1/24/18.
 */

@Module
public class WallPostModule {
    private WallCreatePostActivity activity;

    public WallPostModule(WallCreatePostActivity activity) {
        this.activity = activity;
    }

    @Provides
    @WallPostScope
    public WallCreatePostActivity provideActivity() {
        return activity;
    }

    @Provides
    @WallPostScope
    public WallPostModel provideModel(WallApi api) {
        return new WallPostModel(api);
    }


    @Provides
    @WallPostScope
    public WallPostPresenter providePresenter(WallPostModel model) {
        return new WallPostPresenter(activity, model);
    }
}
