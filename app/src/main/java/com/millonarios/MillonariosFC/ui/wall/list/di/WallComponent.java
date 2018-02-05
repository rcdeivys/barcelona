package com.millonarios.MillonariosFC.ui.wall.list.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.wall.list.WallFragment;

import dagger.Component;

/**
 * Created by Leonardojpr on 1/10/18.
 */
@WallScope
@Component(dependencies = {AppComponent.class}, modules = {WallModule.class})
public interface WallComponent {
    void inject(WallFragment wallFragment);
}
