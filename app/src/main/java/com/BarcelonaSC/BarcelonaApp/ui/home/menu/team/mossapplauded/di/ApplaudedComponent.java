package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.ApplaudedFragment;

import dagger.Component;

/**
 * Created by Carlos on 14/10/2017.
 */
@ApplaudedScope
@Component(dependencies = {AppComponent.class}, modules = {ApplaudedModule.class})
public interface ApplaudedComponent {
    void inject(ApplaudedFragment fragment);
}
