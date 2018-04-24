package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di;

import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_add_group;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsModel;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */
@Module
public class GroupsModule {

    private GroupsFragment fragment;
    private Dialog_add_group dialog_add_group;

    public GroupsModule(GroupsFragment fragment) {
        this.fragment = fragment;
    }


    public GroupsModule(Dialog_add_group dialog_add_group) {
        this.dialog_add_group = dialog_add_group;
    }

    public GroupsModule() {
    }

    @Provides
    @GroupsScope
    public GroupsModel provideGroupsModel() {
        return new GroupsModel();
    }

    @Provides
    @GroupsScope
    public GroupsPresenter providePresenter(GroupsModel model) {
        if (fragment != null)
            return new GroupsPresenter(fragment, model);
        else
            return new GroupsPresenter(dialog_add_group, model);
    }
}
