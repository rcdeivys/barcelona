package com.BarcelonaSC.BarcelonaApp.ui.chat.messages.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessagesFragment;

import dagger.Component;

/**
 * Created by Pedro Gomez on 25/01/2018.
 */
@MessagesScope
@Component(dependencies = {AppComponent.class}, modules = {MessagesModule.class})
public interface MessagesComponent {
    void inject(MessagesFragment fragment);
}
