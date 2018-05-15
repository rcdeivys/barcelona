package com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioConversation;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;

import java.util.ArrayList;

/**
 * Created by Pedro Gomez on 25/01/2018.
 */

public class MessagesModel {

    private static final String TAG = MessagesModel.class.getSimpleName();


    public MessagesModel() {

    }

    public void loadRecieveMessages(int page, final MessagesContract.ModelResultListener result) {

        Log.i("tag", "/*/*/*--->1");
        ArrayList<MessageModelView> aux = new ArrayList<MessageModelView>();
        Log.i("tag", "/*/*/*--->1" + FirebaseManager.getInstance().getUsuario().getId());
        for (UsuarioConversation usuarioConversation : FirebaseManager.getInstance().getUsuario().getUsuarioConversations()) {

            Log.i("tag", "/*/*/*--->22");
            if (usuarioConversation.getId() != null && usuarioConversation.isStatus())
                for (Amigos amigos : FirebaseManager.getInstance().getUsuario().getAmigos()) {
                    if (amigos.getId_conversacion().equals(usuarioConversation.getId()) && !amigos.isBloqueado()) {
                        if (usuarioConversation.getId_participante() != null) {

                            Miembro miembro = amigos.getConversacion().getMiembro(usuarioConversation.getId_participante());
                            if (miembro != null) {
                                //TODO: aqui el tipo de mensaje esta harcodeado. Modificar. por ahora todos los msjs son de tipo texto
                                MessageModelView messageModelView = new MessageModelView(
                                        usuarioConversation.getId(),
                                        usuarioConversation.getId_participante(),
                                        miembro.getApodo(),
                                        usuarioConversation.getUltimo_mensaje(),
                                        miembro.getFoto(),
                                        usuarioConversation.getFecha(),
                                        miembro.getStatusChatAsSTATUS(),
                                        FirebaseManager.MsgTypes.TEXTO,
                                        false,
                                        amigos,
                                        miembro.getCreated_at());
                                aux.add(messageModelView);
                            }
                        }
                    }
                }

        }

        result.onGetMessagesSuccess(aux);
        /*groupsApi.getChatGroups(page).enqueue(new NetworkCallBack<GroupsResponse>() {
            @Override
            public void onRequestSuccess(GroupsResponse response) {
                result.onGetGroupsSuccess(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {
                result.onGetGroupsFailed();
            }
        });*/

    }

    public boolean haveSomeFriends() {
        if (FirebaseManager.getInstance().getUsuario().getAmigos().size() > 0) {
            return true;
        }
        return false;
    }

}
