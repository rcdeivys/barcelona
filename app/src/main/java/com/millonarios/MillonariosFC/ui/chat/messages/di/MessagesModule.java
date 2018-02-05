package com.millonarios.MillonariosFC.ui.chat.messages.di;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.ui.chat.messages.MessagesFragment;
import com.millonarios.MillonariosFC.ui.chat.messages.mvp.MessagesModel;
import com.millonarios.MillonariosFC.ui.chat.messages.mvp.MessagesPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 25/01/2018.
 */
@Module
public class MessagesModule {

    private MessagesFragment fragment;

    public MessagesModule(MessagesFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @MessagesScope
    public MessagesFragment provideFragment() {
        return fragment;
    }

    @Provides
    @MessagesScope
    public MessagesModel provideMessagesModel(FirebaseManager firebaseManager) {
        return new MessagesModel(firebaseManager);
    }


    @Provides
    @MessagesScope
    public MessagesPresenter providePresenter(MessagesModel model) {
        return new MessagesPresenter(fragment, model);
    }

}
