package com.millonarios.MillonariosFC.ui.chat.groupdetail.di;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.GroupDetailActivity;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.mvp.GroupDetailModel;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.mvp.GroupDetailPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */
@Module
public class GroupDetailModule {

    private GroupDetailActivity fragment;

    public GroupDetailModule(GroupDetailActivity activity) {
        this.fragment = activity;
    }

    @Provides
    @GroupDetailScope
    public GroupDetailActivity provideFragment() {
        return fragment;
    }

    @Provides
    @GroupDetailScope
    public GroupDetailModel provideGroupDetailModel(FirebaseManager groupsApi) {
        return new GroupDetailModel(groupsApi);
    }


    @Provides
    @GroupDetailScope
    public GroupDetailPresenter providePresenter(GroupDetailModel model) {
        return new GroupDetailPresenter(fragment, model);
    }

}
