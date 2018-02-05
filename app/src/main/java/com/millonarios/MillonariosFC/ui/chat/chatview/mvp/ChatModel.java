package com.millonarios.MillonariosFC.ui.chat.chatview.mvp;

import android.net.Uri;
import android.util.Log;

import com.millonarios.MillonariosFC.app.api.GroupsApi;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.firebase.Conversacion;
import com.millonarios.MillonariosFC.models.firebase.Mensajes;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.Usuario;
import com.millonarios.MillonariosFC.ui.chat.messages.MessageModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatModel {

    private static final String TAG = ChatModel.class.getSimpleName();

    private GroupsApi groupsApi;
    ArrayList<MessageModelView> mensajes = new ArrayList<MessageModelView>();

    public ChatModel(GroupsApi groupsApi) {
        this.groupsApi = groupsApi;
    }

    public void sendTextMessage(String idConv, Long senderId, List<Miembro> idReceiver, String content, boolean esGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().enviarMensajeTexto(
                idConv,
                senderId,
                idReceiver,
                content,
                esGrupo,
                FirebaseManager.MsgTypes.TEXTO,
                new FirebaseManager.FireResultListener() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void sendImageMessage(final String idConv, final Long senderId, final List<Miembro> idReceiver, final Uri uri, final boolean esGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().uploadImage(uri, new FirebaseManager.FireGroupResultListener() {
            @Override
            public void onComplete(String idGroup) {
                FirebaseManager.getInstance().enviarMensajeImagen(
                        idConv,
                        senderId,
                        idReceiver,
                        "",
                        idGroup.toString(),
                        esGrupo,
                        FirebaseManager.MsgTypes.IMAGEN
                        , new FirebaseManager.FireResultListener() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });


    }

    public void loadPrivateMessages(final Conversacion conversacion, final ChatContract.ModelResultListener.OnLoadMessages result) {
        final Usuario user = FirebaseManager.getInstance().getUsuario();

        FirebaseManager.getInstance().getAllMensajesListener(String.valueOf(user.getId()), conversacion.getId(), new FirebaseManager.FireListener<List<Mensajes>>() {
            @Override
            public void onDataChanged(List<Mensajes> data) {
                Miembro miembro;
                for (Mensajes mensaje : data) {
                    if (!user.getId().equals(mensaje.getEmisor_mensaje()))
                        miembro = conversacion.getMiembro(mensaje.getEmisor_mensaje());
                    else
                        miembro = new Miembro(
                                user.getNombre(),
                                user.getApellido(),
                                user.getApodo(),
                                user.getFoto(),
                                user.getChat_status()
                                , user.getCreated_at());
                    if (miembro != null) {
                        Log.i("MIEMBRO", " ---> " + miembro.toString());
                        mensajes.add(new MessageModelView(
                                mensaje.getEmisor_mensaje(),
                                miembro.getApodo(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()) == FirebaseManager.MsgTypes.IMAGEN ? mensaje.getUrl_imagen() : mensaje.getTexto_mensaje(),
                                miembro.getFoto(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()),
                                mensaje.getEmisor_mensaje().equals(user.getId())));
                    }
                }
                FirebaseManager.getInstance().getMessage(user.getId(), conversacion.getId(), new FirebaseManager.FireListener<Mensajes>() {
                    @Override
                    public void onDataChanged(Mensajes data) {
                        Miembro miembro;
                        if (!user.getId().equals(data.getEmisor_mensaje()))
                            miembro = conversacion.getMiembro(data.getEmisor_mensaje());
                        else
                            miembro = new Miembro(
                                    user.getNombre(),
                                    user.getApellido(),
                                    user.getApodo(),
                                    user.getFoto(),
                                    user.getChat_status()
                                    , user.getCreated_at());

                        mensajes.add(new MessageModelView(
                                data.getEmisor_mensaje(),
                                miembro.getApodo(),
                                ChatModel.getTypeMsg(data.getTipo_mensaje()) == FirebaseManager.MsgTypes.IMAGEN ? data.getUrl_imagen() : data.getTexto_mensaje(),
                                miembro.getFoto(),
                                ChatModel.getTypeMsg(data.getTipo_mensaje()),
                                data.getEmisor_mensaje().equals(user.getId())));

                        result.onLoadMessageSuccess(mensajes);
                    }

                    @Override
                    public void onDataDelete(String id) {

                    }

                    @Override
                    public void onCancelled() {

                    }
                });
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });

        Log.i("MESSAGE", " ---> LOAD FROM PRIVATE ");


    }

    public static FirebaseManager.MsgTypes getTypeMsg(String type) {
        switch (type) {
            case "texto":
                return FirebaseManager.MsgTypes.TEXTO;
            case "imagen":
                return FirebaseManager.MsgTypes.IMAGEN;
            case "video":
                return FirebaseManager.MsgTypes.VIDEO;
            default:
                return FirebaseManager.MsgTypes.TEXTO;
        }
    }

    public void loadGroupMessages(final ChatContract.ModelResultListener.OnLoadMessages result) {
        Log.i("MESSAGE", " ---> LOAD FROM GROUP ");
        result.onLoadMessageSuccess(mensajes);
    }

}
