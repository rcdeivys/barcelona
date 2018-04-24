package com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */

public class GroupsModel {

    private static final String TAG = GroupsModel.class.getSimpleName();



    public GroupsModel() {

    }

    public void loadSuscribedGroups(int page, final GroupsContract.ModelResultListener result) {

        result.onGetGroupsSuccess(GroupsModel.getAsGroupModelArrayLis(FirebaseManager.getInstance().getUsuario().getGrupos()), FirebaseManager.getInstance().getUsuario().getGrupos());

    }

    public void addFriendToSelectedGroup(final Long id_amigo, final String id_grupo, final GroupsContract.ModelResultListener result) {
        //TODO: hacer la peticion para agregar al amigo al grupo
        Log.i("AGREGANDO"," ---> id_amigo : "+id_amigo+" id_grupo : "+id_grupo);
        FirebaseManager.getInstance().buscarUsuarioEnGrupo(id_amigo,id_grupo, new FirebaseManager.FireValuesListener() {
            @Override
            public void onComplete(String value) {
                Log.i("AGREGANDO"," ---> value: "+value);
                if(value.equals("false")) {
                    FirebaseManager.getInstance().invitarUsuarioGrupo(id_amigo, id_grupo, new FirebaseManager.FireResultListener() {
                        @Override
                        public void onComplete() {
                            result.onAddToSelectedGroupSuccess(id_grupo);
                        }

                        @Override
                        public void onError() {
                            result.onAddToSelectedGroupFailed("Error al agregar usuario");
                        }
                    });
                }else{
                    result.onAddToSelectedGroupFailed("Este usuario pertenece al grupo");
                }
            }

            @Override
            public void onCanceled() {

            }
        });

    }

    public boolean haveSomeFriends() {
        if (FirebaseManager.getInstance().getUsuario().getAmigos().size() > 0) {
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
            newGrupo.add(new GroupModelView(grupo, FriendsModelView));
        }
        Collections.reverse(newGrupo);
        return newGrupo;
    }

}
