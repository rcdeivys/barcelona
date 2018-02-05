package com.millonarios.MillonariosFC.ui.chat.creationgroup.di;

import com.millonarios.MillonariosFC.app.api.GroupsApi;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.CreationGroupActivity;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp.CreationGroupModel;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp.CreationGroupPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */
@Module
public class CreationGroupModule {


    private CreationGroupActivity creationGroupActivity;

    public CreationGroupModule(CreationGroupActivity creationGroupActivity) {
        this.creationGroupActivity = creationGroupActivity;
    }

    @Provides
    @CreationGroupScope
    public CreationGroupActivity provideFragment() {
        return creationGroupActivity;
    }

    @Provides
    @CreationGroupScope
    public CreationGroupModel provideCreationGroupModel(FirebaseManager groupsApi) {
        return new CreationGroupModel(groupsApi);
    }


    @Provides
    @CreationGroupScope
    public CreationGroupPresenter providePresenter(CreationGroupModel model) {
        return new CreationGroupPresenter(creationGroupActivity, model);
    }


}
