package com.millonarios.MillonariosFC.ui.wall.comment.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.wall.comment.WallCommentActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/16/18.
 */

@WallCommentScope
@Component(dependencies = {AppComponent.class}, modules = {WallCommentModule.class})
public interface WallCommentComponent {
    void inject(WallCommentActivity activity);
}
