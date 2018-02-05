package com.millonarios.MillonariosFC.ui.chat.chatview.di;

import com.millonarios.MillonariosFC.app.api.GroupsApi;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatFragment;
import com.millonarios.MillonariosFC.ui.chat.chatview.mvp.ChatModel;
import com.millonarios.MillonariosFC.ui.chat.chatview.mvp.ChatPresenter;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.di.CreationGroupScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */
@Module
public class ChatModule {

    private ChatFragment chatActivity;

    public ChatModule(ChatFragment chatActivity) {
        this.chatActivity = chatActivity;
    }

    @Provides
    @ChatScope
    public ChatFragment provideFragment() {
        return chatActivity;
    }

    @Provides
    @ChatScope
    public ChatModel provideChatModel(GroupsApi groupsApi) {
        return new ChatModel(groupsApi);
    }


    @Provides
    @ChatScope
    public ChatPresenter providePresenter(ChatModel model) {
        return new ChatPresenter(chatActivity, model);
    }
}
