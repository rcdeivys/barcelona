package com.millonarios.MillonariosFC.ui.wall.post.di;

import com.millonarios.MillonariosFC.app.api.WallApi;
import com.millonarios.MillonariosFC.ui.wall.post.WallCreatePostActivity;
import com.millonarios.MillonariosFC.ui.wall.post.mvp.WallPostModel;
import com.millonarios.MillonariosFC.ui.wall.post.mvp.WallPostPresenter;

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
