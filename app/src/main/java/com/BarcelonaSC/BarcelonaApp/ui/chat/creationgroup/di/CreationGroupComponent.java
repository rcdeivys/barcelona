package com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.CreationGroupActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */
@CreationGroupScope
@Component(dependencies = {AppComponent.class}, modules = {CreationGroupModule.class})
public interface CreationGroupComponent {
    void inject(CreationGroupActivity creationGroupActivity);
}
