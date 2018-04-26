package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp;

import android.net.Uri;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class CreateGroupContract {

    public interface ModelResultListener {
        void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos);

        void onGetFriendsFailed();

        void onCreateGroupSuccess(Grupo group);

        void onCreateGroupFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickFriend(FriendsModelView friendData);

        void onClickSelectedFriend(FriendsModelView friendData);

        void onClickSelectedFriendToInvitedGroup(FriendsModelView friendData, String id_grupo);

        void loadFriends(int i);

        ArrayList<FriendsModelView> getAllNewGroupMembers();

        void findByName(String name);

        void initGroupMembers(List<FriendsModelView> groupMembers);

        void createGroupChat(String groupName, Uri encodedImage);
    }

    public interface View {

        void showNoResultText(boolean show);

        void showProgress();

        void hideProgress();

        void updateFriends(List<FriendsModelView> friends);

        void updateSelectedFriends(List<FriendsModelView> friends);

        void refresh();

        void showMessage(String message);

        void lunchChatActivity(Grupo newGroup);

    }

}
