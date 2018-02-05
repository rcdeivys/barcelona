package com.millonarios.MillonariosFC.ui.chat.chatview.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatFragment;

import dagger.Component;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */
@ChatScope
@Component(dependencies = {AppComponent.class}, modules = {ChatModule.class})
public interface  ChatComponent {
    void inject(ChatFragment chatActivity);
}
