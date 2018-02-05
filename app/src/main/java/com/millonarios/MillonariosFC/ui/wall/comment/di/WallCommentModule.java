package com.millonarios.MillonariosFC.ui.wall.comment.di;

import com.millonarios.MillonariosFC.app.api.WallCommentApi;
import com.millonarios.MillonariosFC.ui.wall.comment.WallCommentActivity;
import com.millonarios.MillonariosFC.ui.wall.comment.mvp.WallCommentModel;
import com.millonarios.MillonariosFC.ui.wall.comment.mvp.WallCommentPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@Module
public class WallCommentModule {

    private WallCommentActivity activity;

    public WallCommentModule(WallCommentActivity activity) {
        this.activity = activity;
    }

    @Provides
    @WallCommentScope
    public WallCommentActivity provideFragment() {
        return activity;
    }

    @Provides
    @WallCommentScope
    public WallCommentModel provideModel(WallCommentApi api) {
        return new WallCommentModel(api);
    }


    @Provides
    @WallCommentScope
    public WallCommentPresenter providePresenter(WallCommentModel model) {
        return new WallCommentPresenter(activity, model);
    }
}
