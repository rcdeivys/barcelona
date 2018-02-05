package com.millonarios.MillonariosFC.ui.chat.friends.di;

import com.millonarios.MillonariosFC.app.api.FriendsApi;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsFragment;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsModel;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Cesar on 17/01/2018.
 */

@Module
public class FriendModule {

    private FriendsFragment friendsFragment = null;
    private FriendsActivity friendsActivity = null;

    public FriendModule(FriendsFragment fragment) {
        this.friendsFragment = fragment;
    }

    public FriendModule(FriendsActivity activity) {
        this.friendsActivity = activity;
    }

    @Provides
    @FriendScope
    public FriendsModel provideModel(FriendsApi api) {
        return new FriendsModel(api);
    }

    @Provides
    @FriendScope
    public FriendsPresenter providePresenter(FriendsModel model) {
        //TODO: pendiente con esto
        if(friendsFragment!=null)
            return new FriendsPresenter(friendsFragment, model);
        else
            return new FriendsPresenter(friendsActivity,model);
    }

}
