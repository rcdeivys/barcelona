package com.BarcelonaSC.BarcelonaApp.ui.chat.friendselection.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendSelectionContract {

    public interface ModelResultListener {

        void onGetFriendsSuccess(List<Amigos> friends);
        void onGetFriendsFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickFriend(Amigos friendData);
        void onClickSelectedFriend(Amigos friendData);
        void loadFriends();
        ArrayList<Amigos> getAllNewGroupMembers();
        void findByName(String name);
    }

    public interface View {

        void updateFriends(List<Amigos> friends);
        void updateSelectedFriends(List<Amigos> friends);
        void refresh();

    }

}
