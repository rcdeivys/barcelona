package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.di;

import com.BarcelonaSC.BarcelonaApp.app.api.FriendsApi;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.NewConversationActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp.NewConversationModel;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp.NewConversationPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Cesar on 17/01/2018.
 */

@Module
public class NewConversationModule {

    private NewConversationActivity newConversationActivity = null;


    public NewConversationModule(NewConversationActivity activity) {
        this.newConversationActivity = activity;
    }

    @Provides
    @NewConversationScope
    public NewConversationModel provideModel(FriendsApi api) {
        return new NewConversationModel(api);
    }

    @Provides
    @NewConversationScope
    public NewConversationPresenter providePresenter(NewConversationModel model) {
            return new NewConversationPresenter(newConversationActivity,model);
    }

}
