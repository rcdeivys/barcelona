package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatFragment;

import dagger.Component;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */
@ChatScope
@Component(dependencies = {AppComponent.class}, modules = {ChatModule.class})
public interface  ChatComponent {
    void inject(ChatFragment chatActivity);
}
