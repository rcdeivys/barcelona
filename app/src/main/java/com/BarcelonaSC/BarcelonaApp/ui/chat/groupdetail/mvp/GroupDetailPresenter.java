package com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.mvp;

import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */

public class GroupDetailPresenter implements GroupDetailContract.Presenter, GroupDetailContract.ModelResultListener.OnDeleteMember,GroupDetailContract.ModelResultListener.OnLoadMembers {

    private GroupDetailContract.View view;
    private GroupDetailModel chatModel;

    private ArrayList<FriendsModelView> newMessages = new ArrayList<FriendsModelView>();

    public GroupDetailPresenter(GroupDetailContract.View view, GroupDetailModel chatModel) {
        this.view = view;
        this.chatModel = chatModel;
    }


    @Override
    public void onAttach(GroupDetailContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onGetMembersSuccess(List<FriendsModelView> friends) {
        view.updateMembers(friends);
    }

    @Override
    public void onGetMembersFailed() {

    }

    @Override
    public void onDeleteMemberSucces(List<FriendsModelView> friends) {

    }

    @Override
    public void onDeleteMembersFailed() {

    }

    @Override
    public void onClickMemberToDelete(FriendsModelView friendData) {
        chatModel.deleteMember(friendData.getId(),this);
    }

    @Override
    public void loadMembers(String id_group) {
        chatModel.loadMembers(id_group,this);
    }
}
