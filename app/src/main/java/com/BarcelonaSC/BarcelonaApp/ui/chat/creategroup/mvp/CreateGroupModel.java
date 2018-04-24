package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp;

import android.net.Uri;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class CreateGroupModel {

    private static final String TAG = CreateGroupModel.class.getSimpleName();

    public CreateGroupModel() {
    }

    public void loadFriends(long page, final CreateGroupContract.ModelResultListener result) {

        FirebaseManager.getInstance().buscarUsuario(page, new FirebaseManager.FireListener<List<FriendsModelView>>() {
            @Override
            public void onDataChanged(List<FriendsModelView> data) {
                result.onGetFriendsSuccess(data, FirebaseManager.getInstance().getUsuario().getAmigos());
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });

    }

    public static ArrayList<FriendsModelView> getAsGroupModelArrayLis(List<Amigos> amigos) {
        ArrayList<FriendsModelView> newGrupo = new ArrayList<FriendsModelView>();
        for (Amigos amigo : amigos) {

            newGrupo.add(new FriendsModelView(
                    amigo.getId(),
                    amigo.getConversacion().getMiembros().get(0).getApodo(),
                    amigo.getConversacion().getMiembros().get(0).getFoto(),
                    amigo.getConversacion().getMiembros().get(0).getStatusChatAsSTATUS(),
                    amigo.isBloqueado(),
                    amigo.getFecha_amistad(),
                    amigo.getId_conversacion(),
                    amigo.getConversacion().getMiembros().get(0).getNombre(),
                    amigo.getConversacion().getMiembros().get(0).getCreated_at())
            );
        }
        return newGrupo;
    }

    private int generarIntAlea(int rango, int min) {
        return (int) ((Math.random() * rango) + min);
    }

    private String generarCaracteresAleatoreos() {
        String cadena = "";
        for (int i = 0; i < generarIntAlea(6, 5); i++) {
            int consonante = generarIntAlea(23, 97);
            cadena = cadena + (char) (consonante) + getVocal();
        }
        return cadena;
    }

    private char getVocal() {
        int vocal = generarIntAlea(6, 1);
        switch (vocal) {
            case 1:
                return 'a';
            case 2:
                return 'e';
            case 3:
                return 'i';
            case 4:
                return 'o';
            case 5:
                return 'u';
            default:
                return 'a';
        }
    }

    public void createGroup(String groupName, Uri encodedImage, List<Miembro> groupMembers, final CreateGroupContract.ModelResultListener result) {
        FirebaseManager.getInstance().crearGrupo(groupName, encodedImage, groupMembers, new FirebaseManager.FireGroupResultListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete(String id) {
                Log.i("GRUPOCREACION", " ---> " + id);
                Grupo devolver = null;
                for (Grupo grupo : FirebaseManager.getInstance().getUsuario().getGrupos()) {
                    if (grupo.getKey().equals(id)) {
                        devolver = grupo;
                    }
                }
                //   if (devolver != null)
                result.onCreateGroupSuccess(devolver);
            }
        });
    }
}
