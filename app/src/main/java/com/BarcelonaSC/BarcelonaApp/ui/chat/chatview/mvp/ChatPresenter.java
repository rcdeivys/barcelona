package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.ChatReportData;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatPresenter implements ChatContract.Presenter, ChatContract.ModelResultListener.OnChatReport, ChatContract.ModelResultListener.OnLoadMessages, ChatContract.ModelResultListener.OnSendMessages, ChatContract.ModelResultListener.OnDeleteMember {

    private ChatContract.View view;
    private ChatModel chatModel;
    private boolean block = false;
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
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    public void setBlock(boolean block) {
        this.block = block;

    }

    @Override
    public void toSendMessage(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, Uri uri, String idGrupo) {
        Log.i("DAD", "55555---444>" + idConv + "   " + idSender);

        if (block) {
            if (view == null)
                return;
            view.blockUser();
            return;
        }

        if (uri != null) {
            chatModel.sendImageMessage(idConv, idSender, idReceiver, uri, idGrupo, this);
        } else if (msgContent != null && msgContent.length() > 0) {
            chatModel.sendTextMessage(idConv, idSender, idReceiver, msgContent, idGrupo, this);
        } else {
            view.onMissingParams();
        }
    }

    public void toInfoMessage(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, Uri uri, String idGrupo) {
        Log.i("DAD", "55555---444>" + idConv + "   " + idSender);
        List<Miembro> auxList = new ArrayList<>();
        for (Miembro miembro : idReceiver) {
            if (String.valueOf(miembro.getId()).equals(SessionManager.getInstance().getUser().getId_usuario()))
                auxList.add(miembro);
        }

        if (msgContent != null && msgContent.length() > 0) {
            chatModel.sendInfoMessage(idConv, idSender, auxList, msgContent, idGrupo, this);
        } else {
            view.onMissingParams();
        }
    }



    public void toSendGif(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, String uri, String idGrupo) {
        if (block) {
            if (view == null)
                return;
            view.blockUser();
            return;
        }
        if (uri != null) {
            chatModel.sendGifMessage(idConv, idSender, idReceiver, uri, idGrupo, this);
        } else {
            view.onMissingParams();
        }
    }

    @Override
    public void loadMessagesPrivate(Conversacion idConversacion) {
        chatModel.loadPrivateMessages(idConversacion, this);
    }


    public void loadMessagesPaginate(Conversacion idConversacion) {
        if (this.newMessages != null && this.newMessages.size() > 0)
            chatModel.loadPaginateMessages(this.newMessages.get(0).getId(), idConversacion, this);
    }

    @Override
    public void loadMessagesGroup(String id_chat) {
        chatModel.loadGroupMessages(this);
    }

    public void getAllMessage() {
        if (view == null)
            return;

        view.updateMesage(newMessages);
    }

    @Override
    public void onSendMessageSuccess(MessageModelView message) {
        this.newMessages.add(message);
        if (view == null)
            return;
        view.updateMesage(newMessages);
    }

    @Override
    public void onSendMessageSuccess(ArrayList<MessageModelView> messages) {

    }

    public void onClickMemberToDelete(Long id, Grupo grupo, boolean isAdmin) {

        chatModel.deleteMember(id, grupo, isAdmin, this);
    }

    @Override
    public void onSendMessageFailed() {

    }

    @Override
    public void multimediaSuccess() {
        if (view == null)
            return;
        view.multimediaSuccess();
    }

    @Override
    public void multimediaFailed() {
        if (view == null)
            return;
        view.multimediaFailed();
    }


    @Override
    public void onLoadMessageSuccess(ArrayList<MessageModelView> messages) {
        this.newMessages = messages;
        if (view == null)
            return;
        if (block)
            return;
        view.updateMesage(messages);
    }

    @Override
    public void onLoadMessageFailed() {

    }

    @Override
    public void onLoadPaginateMessage(ArrayList<MessageModelView> mensajes) {
        this.newMessages = mensajes;
        if (view == null)
            return;
        if (block)
            return;
        view.updateMesage(mensajes);
    }

    public void clearMessage() {

        newMessages.clear();
        if (view == null)
            return;
        view.updateMesage(newMessages);
    }

    @Override
    public void onDeleteMyUserSuccesS(List<FriendsModelView> friends) {
        if (view == null)
            return;
        view.deleteMember();
    }

    @Override
    public void onDeleteMemberSucces(List<FriendsModelView> friends) {

    }

    @Override
    public void onDeleteMembersFailed() {

    }

    public void reportUser(ChatReportData chatReportData) {
        Log.i("TAG", "////" + chatReportData.toString());
        chatModel.reportUser(chatReportData, this);

    }

    public void toSendVideo(String idConv, Long idSender, List<Miembro> idReceiver, String msgContent, String uri, String idGrupo, Bitmap videoThumbnail) {
        if (uri != null) {
            chatModel.sendVideoMessage(idConv, idSender, idReceiver, uri, idGrupo, videoThumbnail, this);
        } else {
            view.onMissingParams();
        }
    }

    @Override
    public void onReportSuccess() {
        if (view == null)
            return;
        view.onReportSuccess();
    }

    @Override
    public void onReportFailed() {
        if (view == null)
            return;
        view.onReportFailed();
    }
}
