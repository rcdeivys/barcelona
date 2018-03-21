package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp;

import com.BarcelonaSC.BarcelonaApp.commons.mvp.MVPContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;

import java.util.List;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */

public class GroupsContract {

    public interface ModelResultListener {

        void onGetGroupsSuccess(List<GroupModelView> groups, List<Grupo> grupos);
        void onGetGroupsFailed();

    }

    public interface Presenter extends MVPContract.Presenter<View> {

        void onClickGroupItem(GroupModelView groupItem);
        void addSelectedFriendToAGroup(Long id_friend,String id_group);
        void loadGroups();
        void findByName(String name);
        boolean haveFriends();

    }

    public interface View {

        void updateGroups(List<GroupModelView> groups);
        void refresh();

    }
}
