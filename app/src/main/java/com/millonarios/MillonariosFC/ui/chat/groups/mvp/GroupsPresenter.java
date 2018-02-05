package com.millonarios.MillonariosFC.ui.chat.groups.mvp;

import android.util.Log;

import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;
import com.millonarios.MillonariosFC.models.firebase.Grupo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */

public class GroupsPresenter implements GroupsContract.Presenter, GroupsContract.ModelResultListener {

    private GroupsContract.View view;
    private GroupsModel groupsModel;
    private List<Grupo> grupo;

    private List<GroupModelView> groupsList = new ArrayList<GroupModelView>();

    public GroupsPresenter(GroupsContract.View view, GroupsModel groupsModel) {
        this.view = view;
        this.groupsModel = groupsModel;
        init();
    }

    private void init() {

    }

    @Override
    public void onAttach(GroupsContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickGroupItem(GroupModelView groupItem) {

    }

    @Override
    public void loadGroups() {
        Log.i("FIREBASE", " ---> LOADGROUPS");
        groupsModel.loadSuscribedGroups(1, this);
    }

    @Override
    public void findByName(String name) {
        ArrayList<GroupModelView> auxList = new ArrayList<GroupModelView>();
        if (name.length() > 0) {
            for (GroupModelView auxGrupo : groupsList) {
                if (auxGrupo.getNameGroup().toLowerCase().contains(name.toLowerCase())) {
                    auxList.add(auxGrupo);
                }
            }
            view.updateGroups(auxList);
        }
    }

    @Override
    public boolean haveFriends() {
        return groupsModel.haveSomeFriends();
    }

    @Override
    public void onGetGroupsSuccess(List<GroupModelView> groups, List<Grupo> grupos) {
        if (groupsList != null) groupsList.clear();
        if (view == null) return;
        grupo = grupos;
        groupsList = groups;
        view.updateGroups(groupsList);
    }

    @Override
    public void onGetGroupsFailed() {

    }

    @Override
    public void addSelectedFriendToAGroup(Long id_friend,String id_group){
        groupsModel.addFriendToSelectedGroup(id_friend,id_group);
    }

    public Grupo findGroup(String idGroup) {
        for (Grupo grupo : grupo) {
            if (grupo.getKey().equals(idGroup))
                return grupo;
        }
        return new Grupo();
    }

}
