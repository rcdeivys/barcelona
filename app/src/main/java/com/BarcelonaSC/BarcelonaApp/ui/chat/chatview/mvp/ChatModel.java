package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.api.ChatApi;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.ChatReportData;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Mensajes;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 29/01/2018.
 */

public class ChatModel {

    private static final String TAG = ChatModel.class.getSimpleName();

    private ChatApi chatApi;
    ArrayList<MessageModelView> mensajes = new ArrayList<MessageModelView>();
    String lastId = "";

    public ChatModel(ChatApi chatApi) {
        this.chatApi = chatApi;
    }

    public void sendTextMessage(String idConv, Long senderId, List<Miembro> idReceiver, String content, String idGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().enviarMensajeTexto(
                idConv,
                senderId,
                idReceiver,
                content,
                idGrupo,
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

    public void sendImageMessage(final String idConv, final Long senderId, final List<Miembro> idReceiver, final Uri uri, final String idGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().uploadImage(uri, new FirebaseManager.FireGroupResultListener() {
            @Override
            public void onComplete(String fotoUrl) {
                FirebaseManager.getInstance().enviarMensajeMultimedia(
                        idConv,
                        senderId,
                        idReceiver,
                        "<Imagen adjunta>",
                        fotoUrl,
                        idGrupo,
                        FirebaseManager.MsgTypes.IMAGEN
                        , ""
                        , new FirebaseManager.FireResultListener() {
                            @Override
                            public void onComplete() {
                                result.multimediaSuccess();
                            }

                            @Override
                            public void onError() {
                                result.multimediaFailed();
                            }
                        });
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {
                result.multimediaFailed();
            }
        });


    }


    public void deleteMember(final long id_member, Grupo grupo, boolean isAdmin, final ChatContract.ModelResultListener.OnDeleteMember result) {
        FirebaseManager.getInstance().salirUsuarioGrupo(id_member, grupo.getKey(), isAdmin, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                Log.i(TAG, " ---> eliminado usuario " + id_member);
                if (id_member == FirebaseManager.getInstance().getUsuario().getId())
                    result.onDeleteMyUserSuccesS(new ArrayList<FriendsModelView>());
                else
                    result.onDeleteMemberSucces(new ArrayList<FriendsModelView>());
            }

            @Override
            public void onError() {
                Log.i(TAG, " ---> ERROR eliminado usuario" + id_member);
            }
        });
    }

    public void sendGifMessage(final String idConv, final Long senderId, final List<Miembro> idReceiver, final String uri, final String idGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().enviarMensajeMultimedia(
                idConv,
                senderId,
                idReceiver,
                "<Imagen adjunta>",
                uri,
                idGrupo,
                FirebaseManager.MsgTypes.GIF
                , ""
                , new FirebaseManager.FireResultListener() {
                    @Override
                    public void onComplete() {
                        result.multimediaSuccess();
                    }

                    @Override
                    public void onError() {
                        result.onSendMessageFailed();
                    }
                });
    }

    public void sendInfoMessage(String idConv, Long senderId, List<Miembro> idReceiver, String content, String idGrupo, final ChatContract.ModelResultListener.OnSendMessages result) {

        FirebaseManager.getInstance().enviarMensajeTexto(
                idConv,
                senderId,
                idReceiver,
                content,
                idGrupo,
                FirebaseManager.MsgTypes.INFO,
                new FirebaseManager.FireResultListener() {
                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }


    public void memberNull(String id, Mensajes mensajesData, final Count count, final ChatContract.ModelResultListener.OnLoadMessages result) {

        final MessageModelView messageModelView = new MessageModelView(
                mensajesData.getEmisor_mensaje(),
                ChatModel.getTypeMsg(mensajesData.getTipo_mensaje()) == FirebaseManager.MsgTypes.TEXTO ? mensajesData.getTexto_mensaje() : mensajesData.getUrl_imagen(),
                ChatModel.getTypeMsg(mensajesData.getTipo_mensaje()),
                mensajesData.getEmisor_mensaje().equals(SessionManager.getInstance().getUser().getId_usuario()));

        mensajes.add(messageModelView);

        (new MemberControllers()).addValueMemberListener(id, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {
                messageModelView.setMember(member);
                if (count.verificateLimit())
                    result.onLoadMessageSuccess(mensajes);
            }


            @Override
            public void onError() {

            }
        });
    }

    public void memberNullPaginate(String id, Mensajes mensajesData, final Count count, final ChatContract.ModelResultListener.OnLoadMessages result) {

        final MessageModelView messageModelView = new MessageModelView(
                mensajesData.getEmisor_mensaje(),
                ChatModel.getTypeMsg(mensajesData.getTipo_mensaje()) == FirebaseManager.MsgTypes.TEXTO ? mensajesData.getTexto_mensaje() : mensajesData.getUrl_imagen(),
                ChatModel.getTypeMsg(mensajesData.getTipo_mensaje()),
                mensajesData.getEmisor_mensaje().equals(SessionManager.getInstance().getUser().getId_usuario()));

        mensajes.add(0, messageModelView);

        (new MemberControllers()).addValueMemberListener(id, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {
                messageModelView.setMember(member);
                if (count.verificateLimit())
                    result.onLoadPaginateMessage(mensajes);
            }


            @Override
            public void onError() {

            }
        });
    }


    public void loadPaginateMessages(final String last, final Conversacion conversacion, final ChatContract.ModelResultListener.OnLoadMessages result) {

        if (lastId.equals(last))
            return;
        lastId = last;
        final Usuario user = FirebaseManager.getInstance().getUsuario();

        FirebaseManager.getInstance().getAllMensajePaginateListener(conversacion.getId(), last, new FirebaseManager.FireListener<List<Mensajes>>() {
            @Override
            public void onDataChanged(List<Mensajes> data) {
                Miembro miembro;
                final Count count = new Count(data.size());
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
                        Log.i("MIEMBRO", " //////---> " + miembro.toString());
                        Log.i("MIEMBRO", " //// --->//// " + mensaje.getTipo_mensaje());
                        mensajes.add(0, new MessageModelView(
                                mensaje.getId(),
                                mensaje.getEmisor_mensaje(),
                                miembro.getApodo(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()) == FirebaseManager.MsgTypes.TEXTO ? mensaje.getTexto_mensaje() : mensaje.getUrl_imagen(),
                                miembro.getFoto(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()),
                                mensaje.getEmisor_mensaje().equals(user.getId())
                                , miembro.getCreated_at()
                                , mensaje.getFecha_mensaje()
                                , mensaje.getVideo_thumbnail()));

                        if (count.verificateLimit())
                            result.onLoadPaginateMessage(mensajes);
                    } else {
                        memberNullPaginate(String.valueOf(mensaje.getEmisor_mensaje()), mensaje, count, result);
                    }
                }
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

    public void loadPrivateMessages(final Conversacion conversacion, final ChatContract.ModelResultListener.OnLoadMessages result) {

        final Usuario user = FirebaseManager.getInstance().getUsuario();

        FirebaseManager.getInstance().getAllMensajesListener(String.valueOf(user.getId()), conversacion.getId(), new FirebaseManager.FireListener<List<Mensajes>>() {
            @Override
            public void onDataChanged(List<Mensajes> data) {
                Miembro miembro;
                final Count count = new Count(data.size() + 1);
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
                        Log.i("MIEMBRO", " //////---> " + miembro.toString());
                        Log.i("MIEMBRO", " //// --->//// " + mensaje.getTipo_mensaje());
                        mensajes.add(new MessageModelView(
                                mensaje.getId(),
                                mensaje.getEmisor_mensaje(),
                                miembro.getApodo(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()) == FirebaseManager.MsgTypes.TEXTO ? mensaje.getTexto_mensaje() : mensaje.getUrl_imagen(),
                                miembro.getFoto(),
                                ChatModel.getTypeMsg(mensaje.getTipo_mensaje()),
                                mensaje.getEmisor_mensaje().equals(user.getId())
                                , miembro.getCreated_at()
                                , mensaje.getFecha_mensaje()
                                , mensaje.getVideo_thumbnail()));
                        count.verificateLimit();
                    } else {
                        memberNull(String.valueOf(mensaje.getEmisor_mensaje()), mensaje, count, result);
                    }
                }
                FirebaseManager.getInstance().getMessage(user.getId(), conversacion.getId(), new FirebaseManager.FireListener<Mensajes>() {
                    @Override
                    public void onDataChanged(Mensajes data) {
                        Miembro miembro;

                        if (data == null || data.getId() == null)
                            return;

                        if (mensajes == null || (mensajes.size() > 0 && mensajes.get(mensajes.size() - 1).getId() == null))
                            return;

                        if (mensajes.size() > 0
                                && mensajes.get(mensajes.size() - 1).getId() != null
                                && mensajes.get(mensajes.size() - 1).getId().equals(data.getId())) {
                            return;
                        }
                        try {
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
                            if (miembro != null) {
                                mensajes.add(new MessageModelView(
                                        data.getId(),
                                        data.getEmisor_mensaje(),
                                        miembro.getApodo(),
                                        ChatModel.getTypeMsg(data.getTipo_mensaje()) == FirebaseManager.MsgTypes.TEXTO ? data.getTexto_mensaje() : data.getUrl_imagen(),
                                        miembro.getFoto(),
                                        ChatModel.getTypeMsg(data.getTipo_mensaje()),
                                        data.getEmisor_mensaje().equals(user.getId())
                                        , miembro.getCreated_at()
                                        , data.getFecha_mensaje()
                                        , data.getVideo_thumbnail()));
                                count.verificateLimit();

                                if (count.verificateLimit())
                                    result.onLoadMessageSuccess(mensajes);
                            } else {
                                memberNull(String.valueOf(data.getEmisor_mensaje()), data, count, result);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
            case "gif":
                return FirebaseManager.MsgTypes.GIF;
            case "video":
                return FirebaseManager.MsgTypes.VIDEO;
            case "info":
                return FirebaseManager.MsgTypes.INFO;
            default:
                return FirebaseManager.MsgTypes.TEXTO;
        }
    }

    public void loadGroupMessages(final ChatContract.ModelResultListener.OnLoadMessages result) {
        Log.i("MESSAGE", " ---> LOAD FROM GROUP ");
        result.onLoadMessageSuccess(mensajes);
    }

    public void sendVideoMessage(final String idConv, final Long senderId, final List<Miembro> idReceiver, final String uri, final String idGrupo, final Bitmap videoThumbnail, final ChatContract.ModelResultListener.OnSendMessages result) {


        FirebaseManager.getInstance().uploadImageThumbnail(videoThumbnail, new FirebaseManager.FireGroupResultListener() {
            @Override
            public void onComplete(String fotoUrl) {
                FirebaseManager.getInstance().enviarMensajeMultimedia(
                        idConv,
                        senderId,
                        idReceiver,
                        "<Video adjunto>",
                        uri,
                        idGrupo,
                        FirebaseManager.MsgTypes.VIDEO
                        , fotoUrl
                        , new FirebaseManager.FireResultListener() {
                            @Override
                            public void onComplete() {
                                result.multimediaSuccess();
                            }

                            @Override
                            public void onError() {
                                result.multimediaFailed();
                            }
                        });
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {
                result.multimediaFailed();
            }
        });


    }


    public void reportUser(ChatReportData chatReportData, final ChatContract.ModelResultListener.OnChatReport result) {

        chatApi.reportarChat(chatReportData).enqueue(new NetworkCallBack<JsonObject>() {
            @Override
            public void onRequestSuccess(JsonObject response) {
                result.onReportSuccess();
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

                result.onReportFailed();
            }
        });
    }
}
