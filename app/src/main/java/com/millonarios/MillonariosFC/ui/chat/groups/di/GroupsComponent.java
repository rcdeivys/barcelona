package com.millonarios.MillonariosFC.ui.chat.groups.di;


import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupsActivity;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupsFragment;

import dagger.Component;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */
@GroupsScope
@Component(dependencies = {AppComponent.class}, modules = {GroupsModule.class})
public interface GroupsComponent {
    void inject(GroupsFragment fragment);
    void inject(GroupsActivity activity);
}
