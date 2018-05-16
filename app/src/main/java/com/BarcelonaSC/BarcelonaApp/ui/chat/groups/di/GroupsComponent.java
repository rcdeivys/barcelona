package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di;


import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsFragment;

import dagger.Component;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */
@GroupsScope
@Component(dependencies = {AppComponent.class}, modules = {GroupsModule.class})
public interface GroupsComponent {
    void inject(GroupsFragment fragment);
}
