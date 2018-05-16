package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.FriendsApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;

import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class NewConversationModel {

    private static final String TAG = NewConversationModel.class.getSimpleName();
    List<FriendsModelView> listfriends;

    private FriendsApi friendsApi;

    public NewConversationModel(FriendsApi friendsApi) {
        this.friendsApi = friendsApi;
    }

    public void loadFriends(Long pagination, final NewConversationContract.ModelResultListener.OnGetFriendsListener result) {
        FirebaseManager.getInstance().buscarUsuario(pagination, new FirebaseManager.FireListener<List<FriendsModelView>>() {
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

    public void addASelectedFriendToAGroup(String id_group, Long id_friend, final NewConversationContract.ModelResultListener.OnAddAGroupListener result) {
        FirebaseManager.getInstance().invitarUsuarioGrupo(id_friend, id_group, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                result.onAddSuccess();
            }

            @Override
            public void onError() {
                result.onAddFailed();
            }
        });
    }

    public void acceptUser(String myID, final FriendsModelView UserToInvite, final NewConversationContract.ModelResultListener.OnGetFriendsListener result) {
        Log.i(TAG, "111--->adwdad: ");
        FirebaseManager.getInstance().aceptarSolicitud(UserToInvite, myID, new FirebaseManager.FireFriendsResultListener() {
            @Override
            public void onComplete(Amigos amigos) {

                result.onAcceptSuccess(amigos);
            }

            @Override
            public void onError() {
                Log.i(TAG, "--->acceptUser() onError ");
                result.onAcceptFailed();
            }
        });
    }
}

