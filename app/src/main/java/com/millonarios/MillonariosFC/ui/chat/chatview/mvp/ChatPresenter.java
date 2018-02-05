package com.millonarios.MillonariosFC.ui.chat.chatview.mvp;

import android.net.Uri;
import android.util.Log;

import com.millonarios.MillonariosFC.models.firebase.Conversacion;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.ui.chat.messages.MessageModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatPresenter implements ChatContract.Presenter, ChatContract.ModelResultListener.OnLoadMessages, ChatContract.ModelResultListener.OnSendMessages {

    private ChatContract.View view;
    private ChatModel chatModel;

    private ArrayList<MessageModelView> newMessages = new ArrayList<MessageModelView>();

    public ChatPresenter(ChatContract.View view, ChatModel chatModel) {
        this.view = view;
        this.chatModel = chatModel;
        init();
    }

    private void init() {

    }

    @Override
    public void onAttach(ChatContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void toSendMessage(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, Uri uri, boolean esGrupo) {
        if (uri != null) {
            chatModel.sendImageMessage(idConv, idSender, idReceiver, uri, esGrupo, this);
        } else if (msgContent != null && msgContent.length() > 0) {
            chatModel.sendTextMessage(idConv, idSender, idReceiver, msgContent, esGrupo, this);
        } else {
            view.onMissingParams();
        }
    }

    @Override
    public void loadMessagesPrivate(Conversacion idConversacion) {
        chatModel.loadPrivateMessages(idConversacion, this);
    }

    @Override
    public void loadMessagesGroup(String id_chat) {
        chatModel.loadGroupMessages(this);
    }


    @Override
    public void onSendMessageSuccess(MessageModelView message) {
        this.newMessages.add(message);
        view.updateMesage(newMessages);
    }

    @Override
    public void onSendMessageSuccess(ArrayList<MessageModelView> messages) {

    }

    @Override
    public void onSendMessageFailed() {

    }

    @Override
    public void onLoadMessageSuccess(ArrayList<MessageModelView> messages) {
        this.newMessages = messages;
        view.updateMesage(messages);
    }

    @Override
    public void onLoadMessageFailed() {

    }
}
