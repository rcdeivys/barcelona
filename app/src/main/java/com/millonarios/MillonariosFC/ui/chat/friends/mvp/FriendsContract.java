package com.millonarios.MillonariosFC.ui.chat.friends.mvp;

import com.millonarios.MillonariosFC.commons.mvp.MVPContract;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class FriendsContract {

    static class ModelResultListener{

        public interface OnGetFriendsListener {

            void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos);
            void onGetFriendsFailed();

        }

        public interface OnAddAGroupListener{

            void onAddSuccess();
            void onAddFailed();

        }

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void addSelectedFriendAGroup(String id_group,String id_friend);
        void onClickFriend(FriendsModelView friendData);
        void loadFriends();
        void findByName(String name);

    }

    public interface View {

        void updateFriends(List<FriendsModelView> friends);
        void showProgress();
        void hideProgress();
        void showToastError(String errror);
        void refresh();
        void addFriendSuccess();
        void addFriendFailed();
    }
}
