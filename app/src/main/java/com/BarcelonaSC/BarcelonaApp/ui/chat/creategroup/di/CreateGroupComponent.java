package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_add_group;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.CreateGroupActivity;

import dagger.Component;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */
@CreateGroupScope
@Component(dependencies = {AppComponent.class}, modules = {CreateGroupModule.class})
public interface CreateGroupComponent {
    void inject(CreateGroupActivity createGroupActivity);
    void inject(Dialog_add_group dialog_add_group);
}
