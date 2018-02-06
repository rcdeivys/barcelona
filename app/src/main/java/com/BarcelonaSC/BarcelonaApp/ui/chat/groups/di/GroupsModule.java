package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsActivity;
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

    private GroupsActivity activity;

    public GroupsModule(GroupsFragment fragment) {
        this.fragment = fragment;
    }

    public GroupsModule(GroupsActivity activity) {
        this.activity = activity;
    }

    @Provides
    @GroupsScope
    public GroupsModel provideGroupsModel(FirebaseManager groupsApi) {
        return new GroupsModel(groupsApi);
    }

    @Provides
    @GroupsScope
    public GroupsPresenter providePresenter(GroupsModel model) {
        if(fragment!=null)
            return new GroupsPresenter(fragment, model);
        else
            return new GroupsPresenter(activity,model);
    }
}
