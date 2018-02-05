package com.millonarios.MillonariosFC.ui.wall.post.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.wall.post.WallCreatePostActivity;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/24/18.
 */

@WallPostScope
@Component(dependencies = {AppComponent.class}, modules = {WallPostModule.class})
public interface WallPostComponent {
    void inject(WallCreatePostActivity wallCreatePostActivity);
}
