package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.di;

import com.BarcelonaSC.BarcelonaApp.app.api.ChatApi;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatFragment;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp.ChatModel;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp.ChatPresenter;

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
    public ChatModel provideChatModel(ChatApi chatApi) {
        return new ChatModel(chatApi);
    }


    @Provides
    @ChatScope
    public ChatPresenter providePresenter(ChatModel model) {
        return new ChatPresenter(chatActivity, model);
    }
}
