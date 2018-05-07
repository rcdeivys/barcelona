package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di;

import com.BarcelonaSC.BarcelonaApp.app.api.WallApi;
import com.BarcelonaSC.BarcelonaApp.app.api.WallCommentApi;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentEditActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentModel;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@Module
public class WallCommentModule {

    private WallCommentFragment activity;
    private WallCommentEditActivity activityEdit;

    public WallCommentModule(WallCommentFragment activity) {
        this.activity = activity;
    }

    public WallCommentModule(WallCommentEditActivity activity) {
        this.activityEdit = activity;
    }

    @Provides
    @WallCommentScope
    public WallCommentFragment provideFragment() {
        return activity;
    }

    @Provides
    @WallCommentScope
    public WallCommentModel provideModel(WallApi wallApi, WallCommentApi api) {
        return new WallCommentModel(wallApi, api);
    }


    @Provides
    @WallCommentScope
    public WallCommentPresenter providePresenter(WallCommentModel model) {
        return new WallCommentPresenter(activity, model);
    }
}
