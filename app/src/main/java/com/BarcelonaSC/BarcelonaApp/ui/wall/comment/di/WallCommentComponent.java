package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@WallCommentScope
@Component(dependencies = {AppComponent.class}, modules = {WallCommentModule.class})
public interface WallCommentComponent {
    void inject(WallCommentActivity activity);
}
