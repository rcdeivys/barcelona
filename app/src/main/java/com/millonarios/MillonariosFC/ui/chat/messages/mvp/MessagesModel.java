package com.millonarios.MillonariosFC.ui.chat.messages.mvp;

import com.millonarios.MillonariosFC.app.api.GroupsApi;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.UsuarioConversation;
import com.millonarios.MillonariosFC.ui.chat.chatview.mvp.ChatModel;
import com.millonarios.MillonariosFC.ui.chat.messages.MessageModelView;

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
