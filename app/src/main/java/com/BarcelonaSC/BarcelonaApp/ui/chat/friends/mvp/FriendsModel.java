package com.BarcelonaSC.BarcelonaApp.ui.chat.friends.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.FriendsApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class FriendsModel {

    private static final String TAG = FriendsModel.class.getSimpleName();
    List<FriendsModelView> listfriends;

    private FriendsApi friendsApi;

    public FriendsModel(FriendsApi friendsApi) {
        this.friendsApi = friendsApi;
    }

    public void loadFriends(String id_usuario, final FriendsContract.ModelResultListener.OnGetFriendsListener result) {

        listfriends = new ArrayList<>();
        if (FirebaseManager.getInstance().getUsuario().getAmigos() != null)
            for (int i = 0; i < FirebaseManager.getInstance().getUsuario().getAmigos().size(); i++) {
                Amigos amigo = FirebaseManager.getInstance().getUsuario().getAmigos().get(i);
                Miembro miembro = new Miembro();
                if (amigo.getConversacion().getMiembros().size() > 0)
                    miembro = amigo.getConversacion().getMiembros().get(0);
                Log.i(TAG, "------betaloco" + miembro.getStatusChatAsSTATUS().getValue());
                listfriends.add(new FriendsModelView(amigo.getId()
                        , miembro.getApodo(), miembro.getFoto()
                        , String.valueOf(miembro.getStatusChatAsSTATUS().getValue())
                        , amigo.isBloqueado()
                        , amigo.getId_conversacion()
                        , miembro.getNombre(), miembro.getCreated_at()));
            }

        result.onGetFriendsSuccess(listfriends, FirebaseManager.getInstance().getUsuario().getAmigos());
    }

    public void removeFriend(Amigos amistad) {
        FirebaseManager.getInstance().eliminarAmigo(FirebaseManager.getInstance().getUsuario().getId()
                , amistad.getConversacion().getMiembros().get(0).getId(), amistad.getId_conversacion(), new FirebaseManager.FireResultListener() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void addASelectedFriendToAGroup(String id_group, String id_friend, final FriendsContract.ModelResultListener.OnAddAGroupListener result) {

    }
}
