package com.millonarios.MillonariosFC.ui.chat.friendselection.di;

import com.millonarios.MillonariosFC.app.api.FriendsApi;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.ui.chat.friendselection.FriendSelectionActivity;
import com.millonarios.MillonariosFC.ui.chat.friendselection.mvp.FriendSelectionModel;
import com.millonarios.MillonariosFC.ui.chat.friendselection.mvp.FriendSelectionPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */
@Module
public class FriendSelectionModule {

    private FriendSelectionActivity friendSelectionActivity;

    public FriendSelectionModule(FriendSelectionActivity friendSelectionActivity) {
        this.friendSelectionActivity = friendSelectionActivity;
    }

    @Provides
    @FriendSelectionScope
    public FriendSelectionActivity provideFragment() {
        return friendSelectionActivity;
    }

    @Provides
    @FriendSelectionScope
    public FriendSelectionModel provideFriendSelectionModel(FirebaseManager friendsApi) {
        return new FriendSelectionModel(friendsApi);
    }


    @Provides
    @FriendSelectionScope
    public FriendSelectionPresenter providePresenter(FriendSelectionModel model) {
        return new FriendSelectionPresenter(friendSelectionActivity, model);
    }

}
