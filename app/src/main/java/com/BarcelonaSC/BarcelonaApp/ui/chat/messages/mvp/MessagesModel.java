package com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp;

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

    private FirebaseManager firebaseManager;

    public MessagesModel(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    public void loadRecieveMessages(int page, final MessagesContract.ModelResultListener result) {


        ArrayList<MessageModelView> aux = new ArrayList<MessageModelView>();
        for (UsuarioConversation usuarioConversation : firebaseManager.getUsuario().getUsuarioConversations()) {
            if (usuarioConversation.getId() != null)
                for (Amigos amigos : firebaseManager.getUsuario().getAmigos()) {
                    if (amigos.getId_conversacion().equals(usuarioConversation.getId())) {
                        if (usuarioConversation.getId_participante() != null) {

                            Miembro miembro = amigos.getConversacion().getMiembro(usuarioConversation.getId_participante());
                            if (miembro != null) {
                                //TODO: aqui el tipo de mensaje esta harcodeado. Modificar. por ahora todos los msjs son de tipo texto
                                MessageModelView messageModelView = new MessageModelView(
                                        usuarioConversation.getId_participante(),
                                        miembro.getApodo(),
                                        usuarioConversation.getUltimo_mensaje(),
                                        miembro.getFoto(),
                                        usuarioConversation.getFecha(),
                                        miembro.getStatusChatAsSTATUS(),
                                        FirebaseManager.MsgTypes.TEXTO,
                                        false,
                                        amigos);
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

    public boolean haveSomeFriends(){
        if(firebaseManager.getUsuario().getAmigos().size()>0){
            return true;
        }
        return false;
    }

}
