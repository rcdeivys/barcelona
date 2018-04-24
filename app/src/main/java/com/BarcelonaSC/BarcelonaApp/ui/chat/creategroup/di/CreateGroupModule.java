package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di;

import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_add_group;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.CreateGroupActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupModel;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */
@Module
public class CreateGroupModule {

    private CreateGroupActivity createGroupActivity;

    private Dialog_add_group dialog_add_group;

    public CreateGroupModule(CreateGroupActivity createGroupActivity) {
        this.createGroupActivity = createGroupActivity;
    }

    public CreateGroupModule(Dialog_add_group dialog_add_group) {
        this.dialog_add_group = dialog_add_group;
    }

    @Provides
    @CreateGroupScope
    public CreateGroupActivity provideFragment() {
        return createGroupActivity;
    }

    @Provides
    @CreateGroupScope
    public CreateGroupModel provideFriendSelectionModel() {
        return new CreateGroupModel();
    }


    @Provides
    @CreateGroupScope
    public CreateGroupPresenter providePresenter(CreateGroupModel model) {
        if(createGroupActivity !=null)
            return new CreateGroupPresenter(createGroupActivity, model);
        else
            return new CreateGroupPresenter(dialog_add_group, model);
    }

}
