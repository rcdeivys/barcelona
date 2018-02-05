package com.millonarios.MillonariosFC.ui.chat.groupdetail.mvp;

import android.util.Log;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.Usuario;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.mvp.GroupsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */

public class GroupDetailModel {

    private static final String TAG = GroupDetailModel.class.getSimpleName();

    private FirebaseManager groupsApi;

    public GroupDetailModel(FirebaseManager groupsApi) {
        this.groupsApi = groupsApi;
    }

    public void loadMembers(String idGrupo, final GroupDetailContract.ModelResultListener.OnLoadMembers result) {


        if (groupsApi.getUsuario().getGrupos() != null) {

            for (int i = 0; i < groupsApi.getUsuario().getGrupos().size(); i++) {
                if (idGrupo.equals(groupsApi.getUsuario().getGrupos().get(i).getKey())) {
                    List<FriendsModelView> friendsModelViews = new ArrayList<>();
                    for (Miembro miembro : groupsApi.getUsuario().getGrupos().get(i).getConversacion().getMiembros()) {
                        FriendsModelView friendsModelViews1 = new FriendsModelView(miembro.getId(), miembro.getApodo(), miembro.getFoto()
                                , miembro.getStatusChat(), false, groupsApi.getUsuario().getGrupos().get(i).getConversacion().getId(),miembro.getNombre(),miembro.getCreated_at());

                    }
                    result.onGetMembersSuccess(new ArrayList<FriendsModelView>());
                    break;
                }
            }
        }


    }

    public void deleteMember(String id_member, final GroupDetailContract.ModelResultListener.OnDeleteMember result) {

    }

}
