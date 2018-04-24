package com.BarcelonaSC.BarcelonaApp.ui.chat.messages.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pedro Gomez on 25/01/2018.
 */

public class MessagesPresenter implements MessagesContract.Presenter, MessagesContract.ModelResultListener {

    private MessagesContract.View view;
    private MessagesModel messagesModel;

    private ArrayList<MessageModelView> messagesList = new ArrayList<MessageModelView>();

    public MessagesPresenter(MessagesContract.View view, MessagesModel messagesModel) {
        this.view = view;
        this.messagesModel = messagesModel;
    }


    @Override
    public void onAttach(MessagesContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onGetMessagesSuccess(List<MessageModelView> messagesData) {
        if (messagesList == null)
            messagesList = new ArrayList<>();
        messagesList.clear();
        if (view == null) return;
        for (MessageModelView message : messagesData) {
            Crashlytics.log(Log.DEBUG, "LISTA_MENSAJES", " ---> " + message.getApodo());
            messagesList.add(message);
        }

        Collections.sort(messagesList, new TimeComparator());

        view.updateMessages(messagesList);

    }

    public static class TimeComparator implements Comparator<MessageModelView> {
        @Override
        public int compare(MessageModelView s, MessageModelView t) {
            int f = t.getTime().compareTo(s.getTime());
            return (f != 0) ? f : t.getTime().compareTo(s.getTime());
        }
    }

    @Override
    public void onGetMessagesFailed() {

    }

    @Override
    public void findByName(String name) {
        ArrayList<MessageModelView> auxList = new ArrayList<>();
        if (name.length() >= 0) {
            for (MessageModelView mensaje : messagesList) {
                if (mensaje.getApodo().toLowerCase().contains(name.toLowerCase())) {
                    auxList.add(mensaje);
                }
            }
            view.updateMessages(auxList);
        }
    }


    @Override
    public void onClickMessages(MessageModelView messageData) {

    }

    @Override
    public void loadMessages() {
        Log.i("tag", "/*/*/*--->1");
        messagesModel.loadRecieveMessages(1, this);
    }

    @Override
    public boolean haveFriends() {
        return messagesModel.haveSomeFriends();
    }

    public void removeConversation(Conversacion conversacion) {
        FirebaseManager.getInstance().borrarConversacion(
                FirebaseManager.getInstance().getUsuario().getId(),
                conversacion.getId(), false);
    }
}
