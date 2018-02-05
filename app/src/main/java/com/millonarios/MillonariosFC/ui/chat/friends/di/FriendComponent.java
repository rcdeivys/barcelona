package com.millonarios.MillonariosFC.ui.chat.friends.di;

import com.millonarios.MillonariosFC.app.di.AppComponent;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsFragment;

import dagger.Component;

/**
 * Created by Cesar on 17/01/2018.
 */

@FriendScope
@Component(dependencies = {AppComponent.class}, modules = {FriendModule.class})
public interface FriendComponent {
    void inject(FriendsFragment fragment);
    void inject(FriendsActivity activity);
}
