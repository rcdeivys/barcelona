package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class NewConversationContract {

    static class ModelResultListener {

        public interface OnGetFriendsListener {

            void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos);

            void onGetFriendsFailed();

            void onAcceptSuccess(Amigos amigos);

            void onAcceptFailed();
        }

        public interface OnAddAGroupListener {

            void onAddSuccess();

            void onAddFailed();

        }

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void addSelectedFriendAGroup(String id_group, Long id_friend);

        void onClickFriend(FriendsModelView friendData);

        void loadFriends(int current_page);

        void findByName(String name);

    }

    public interface View {

        void updateFriends(List<FriendsModelView> friends);

        void onCompleteFriends(Amigos amigos);

        void showProgress();

        void showNoResultText(boolean show);

        void hideProgress();

        void showToastError(String errror);

        void refresh();

        void addFriendSuccess();

        void addFriendFailed();

        void showFindFriend(boolean visibility);
    }
}
