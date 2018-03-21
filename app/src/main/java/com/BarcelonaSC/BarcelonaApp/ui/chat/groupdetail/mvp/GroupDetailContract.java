package com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

import java.util.List;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */

public class GroupDetailContract {

    static class ModelResultListener {

        public interface OnLoadMembers{

            void onGetMembersSuccess(List<FriendsModelView> friends);
            void onGetMembersFailed();

        }

        public interface OnDeleteMember{

            void onDeleteMemberSucces(List<FriendsModelView> friends);
            void onDeleteMembersFailed();

        }


    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickMemberToDelete(FriendsModelView friendData);
        void loadMembers(String id_group);

    }

    public interface View {

        void updateMembers(List<FriendsModelView> friends);
        void refresh();

    }

}
