package com.BarcelonaSC.BarcelonaApp.ui.chat.friendselection.mvp;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendSelectionModel {

    private static final String TAG = FriendSelectionModel.class.getSimpleName();

    private FirebaseManager friendsApi;

    public FriendSelectionModel(FirebaseManager friendsApi) {
        this.friendsApi = friendsApi;
    }

    public void loadFriends(int page, final FriendSelectionContract.ModelResultListener result) {

        result.onGetFriendsSuccess(FirebaseManager.getInstance().getUsuario().getAmigos());

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

}
