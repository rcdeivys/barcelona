package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.NewConversationActivity;

import dagger.Component;

/**
 * Created by Cesar on 17/01/2018.
 */

@NewConversationScope
@Component(dependencies = {AppComponent.class}, modules = {NewConversationModule.class})
public interface NewConversationComponent {
    void inject(NewConversationActivity activity);
}
