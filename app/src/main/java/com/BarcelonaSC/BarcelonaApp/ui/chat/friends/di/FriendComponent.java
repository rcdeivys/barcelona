package com.BarcelonaSC.BarcelonaApp.ui.chat.friends.di;

import com.BarcelonaSC.BarcelonaApp.app.di.AppComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsFragment;

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
