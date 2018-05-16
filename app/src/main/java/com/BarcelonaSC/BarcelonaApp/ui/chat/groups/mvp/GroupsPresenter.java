package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;

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
        this.view=view;
    }

    @Override
    public void onDetach() {
        this.view=null;
    }

    @Override
    public void onClickGroupItem(GroupModelView groupItem) {

    }

    @Override
    public void loadGroups() {
        Log.i("FIREBASE", " ---> LOADGROUPS");
        groupsModel.loadSuscribedGroups(1, this);
    }

    private boolean suiche = false;
    String seach = "";

    public boolean isViewNull() {
        return view == null;
    }

    @Override
    public void findByName(String name) {
        if(isViewNull()) return;
        seach = name;

        if (!seach.isEmpty()) {
            if (!suiche) {

                suiche = true;
            }
            ArrayList<GroupModelView> auxList = new ArrayList<GroupModelView>();
            if (seach.length() > 0) {
                for (GroupModelView auxGrupo : groupsList) {
                    if (auxGrupo.getNameGroup().toLowerCase().contains(seach.toLowerCase())) {
                        auxList.add(auxGrupo);
                    }
                }
                view.updateGroups(auxList);
            }
        }else{
            loadGroups();
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
    public void onAddToSelectedGroupSuccess(String id_group) {
        if(view==null) return;

        view.goToSelectedGroup(id_group);
    }

    @Override
    public void onAddToSelectedGroupFailed(String message) {
        if(view==null) return;

        view.showMessage(message);
    }

    @Override
    public void addSelectedFriendToAGroup(Long id_friend,String id_group){
        groupsModel.addFriendToSelectedGroup(id_friend,id_group,this);
    }

    public Grupo findGroup(String idGroup) {
        for (Grupo grupo : grupo) {
            if (grupo.getKey().equals(idGroup))
                return grupo;
        }
        return new Grupo();
    }

}
