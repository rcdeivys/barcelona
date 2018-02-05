package com.millonarios.MillonariosFC.ui.chat.groups.mvp;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */

public class GroupsModel {

    private static final String TAG = GroupsModel.class.getSimpleName();

    private FirebaseManager firebaseManager;

    public GroupsModel(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    public void loadSuscribedGroups(int page, final GroupsContract.ModelResultListener result) {

        result.onGetGroupsSuccess(GroupsModel.getAsGroupModelArrayLis(firebaseManager.getUsuario().getGrupos()), firebaseManager.getUsuario().getGrupos());

    }

    public void addFriendToSelectedGroup(Long id_amigo, String id_grupo) {
        //TODO: hacer la peticion para agregar al amigo al grupo
    }

    public boolean haveSomeFriends() {
        if (firebaseManager.getUsuario().getAmigos().size() > 0) {
            return true;
        }
        return false;
    }

    public static ArrayList<GroupModelView> getAsGroupModelArrayLis(List<Grupo> grupos) {
        ArrayList<GroupModelView> newGrupo = new ArrayList<GroupModelView>();
        for (Grupo grupo : grupos) {
            ArrayList<FriendsModelView> FriendsModelView = new ArrayList<>();
            for (Miembro miembro : grupo.getConversacion().getMiembros()) {
                FriendsModelView.add(new FriendsModelView(miembro.getId(),
                        miembro.getApodo(), miembro.getFoto(), miembro.getStatusChat(),
                        false, grupo.getId_conversacion(), miembro.getNombre(), miembro.getCreated_at()));
            }
            newGrupo.add(new GroupModelView(grupo.getKey(), grupo.getNombre(), grupo.getFoto(), FriendsModelView));
        }
        return newGrupo;
    }

}
