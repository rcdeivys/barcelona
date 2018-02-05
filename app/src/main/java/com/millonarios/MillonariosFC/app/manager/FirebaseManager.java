package com.millonarios.MillonariosFC.app.manager;

import android.content.Context;
import android.net.Uri;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.util.Log;

import com.arasthel.asyncjob.AsyncJob;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.FirebaseControllers.request.SolicitudesControllers;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Conversacion;
import com.millonarios.MillonariosFC.models.firebase.Count;
import com.millonarios.MillonariosFC.models.firebase.FirebaseEvent;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.models.firebase.GrupoUsuarios;
import com.millonarios.MillonariosFC.models.firebase.Mensajes;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.Solicitud;
import com.millonarios.MillonariosFC.models.firebase.UsuarioConversation;
import com.millonarios.MillonariosFC.models.firebase.Usuario;
import com.millonarios.MillonariosFC.models.firebase.UsuarioGrupo;
import com.millonarios.MillonariosFC.ui.chat.requests.RequestModelView;
import com.millonarios.MillonariosFC.utils.PhotoUpload;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    private static final String TAG = FirebaseManager.class.getSimpleName();
    FirebaseDatabase secondaryDatabase;

    public enum MsgTypes {

        TEXTO("texto"), IMAGEN("imagen"), VIDEO("video");

        private String value = "";

        MsgTypes(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    Usuario usuario = null;
    List<RequestModelView> listSugerencias;

    private static FirebaseManager ourInstance;

    private FirebaseManager() {
        initFirebase();
    }

    public static FirebaseManager getInstance() {
        Log.i("FIREBASE", " ---> getInstance");
        if (ourInstance == null)
            return ourInstance = new FirebaseManager();
        else
            return ourInstance;
    }

    public Usuario getUsuario() {
        return usuario;
    }


    public void initFirebase() {
        // Manually configure Firebase Options
        Log.i("FIREBASE", " ---> init");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:528683318056:android:61a3130da854834c") // Required for Analytics.
                .setApiKey("AIzaSyCI58q_-hZwjFctMCniJsOeQPbQsiP137g") // Required for Auth.
                .setDatabaseUrl("https://millos-backend.firebaseio.com") // Required for RTDB.
                .build();

        FirebaseApp.initializeApp(App.get() /* Context */, options, "secondary");

        // Retrieve secondary app.
        FirebaseApp secondary = FirebaseApp.getInstance("secondary");


        // Get the database for the other app.
        secondaryDatabase = FirebaseDatabase.getInstance(secondary);
        secondaryDatabase.setPersistenceEnabled(true);

        new AsyncJob.AsyncJobBuilder<List<RequestModelView>>()
                .doInBackground(new AsyncJob.AsyncAction<List<RequestModelView>>() {
                    @Override
                    public List<RequestModelView> doAsync() {
                        startListener(SessionManager.getInstance().getUser().getId_usuario(), new FireListener<Usuario>() {
                            @Override
                            public void onDataChanged(Usuario data) {

                            }

                            @Override
                            public void onDataDelete(String id) {

                            }

                            @Override
                            public void onCancelled() {

                            }
                        });
                        getSugger(Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()), new FireListener<List<RequestModelView>>() {
                            @Override
                            public void onDataChanged(List<RequestModelView> data) {

                            }

                            @Override
                            public void onDataDelete(String id) {

                            }

                            @Override
                            public void onCancelled() {

                            }
                        });
                        changeUserState(1, new FireResultListener() {
                            @Override
                            public void onComplete() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
                        return null;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<List<RequestModelView>>() {
                    @Override
                    public void onResult(List<RequestModelView> result) {

                    }
                }).create().start();

        Log.i(TAG, "--->IDUSERIO " + SessionManager.getInstance().getUser().getId_usuario());

        //getSugeridos();
    }


    //////////////////////////////FUNCIONES VARIAS////////////////////////////////////
    private String generateKey(String ref) {
        return secondaryDatabase.getReference().child(ref).push().getKey();
    }

    public List<RequestModelView> getListSugerencias() {
        return listSugerencias;
    }

    public void setListSugerencias(List<RequestModelView> listSugerencias) {
        this.listSugerencias = listSugerencias;
    }

    private void verificateResult(DatabaseError databaseError, FireResultListener fireResultListener) {
        if (databaseError != null) {
            fireResultListener.onError();
        } else {
            fireResultListener.onComplete();
        }

    }

    private Long getDate() {
        // return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return new DateTime().getMillis() / 1000;
    }

    private Long getDateInMilis() {
        return new DateTime().getMillis() / 1000;
    }


    ////////////////////////////////REQUEST /////////////////////////////


    // FUNCIONA BIEN
    public void enviarNuevaSolicitud(String usuarioSolicita, String usuarioSolicitado, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita + "/fecha", getDate());
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita + "/status", 0);

        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita + "/" + usuarioSolicitado + "/fecha", getDate());
        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita + "/" + usuarioSolicitado + "/status", 0);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    //FUNCIONA BIEN
    // TODO: 31/01/2018 Revisar la fecha
    public void aceptarSolicitud(String usuarioSolicita, String usuarioSolicitado, final FireResultListener fireResultListener) {


        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita, new Solicitud(getDate(), 1));
        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita + "/" + usuarioSolicitado, new Solicitud(getDate(), 1));
        String keyConv = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION).push().getKey();
        solic.put(ModelKeys.CONVERSATION + "/" + keyConv, new Conversacion(getDateInMilis()));
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicitado + "/" + usuarioSolicita, new Amigos(false, getDate(), keyConv));
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicita + "/" + usuarioSolicitado, new Amigos(false, getDate(), keyConv));
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });

    }

    //FUNCIONA BIEN
    public void rechazarSolicitud(String usuarioSolicita, String usuarioSolicitado, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita, null);
        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita + "/" + usuarioSolicitado, null);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void invitarUsuarioGrupo(Long userId, String idGrupo, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.GROUP_X_USER + "/" + userId + "/" + idGrupo, true);
        solic.put(ModelKeys.USER_X_GROUP + "/" + idGrupo + "/" + userId, true);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void salirUsuarioGrupo(Long userId, String idGrupo, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.GROUP_X_USER + "/" + userId + "/" + idGrupo, null);
        solic.put(ModelKeys.USER_X_GROUP + "/" + idGrupo + "/" + userId, null);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void eliminarAmigo(Long usuarioElimina, Long usuarioEliminado
            , String idConversacion, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.FRIENDS + "/" + usuarioElimina + "/" + usuarioEliminado, null);
        solic.put(ModelKeys.FRIENDS + "/" + usuarioEliminado + "/" + usuarioElimina, null);
        solic.put(ModelKeys.CONVERSATION + "/" + idConversacion, null);
        solic.put(ModelKeys.USER_CONVERSATION + "/" + usuarioElimina + "/" + idConversacion, null);
        solic.put(ModelKeys.USER_CONVERSATION + "/" + usuarioEliminado + "/" + idConversacion, null);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }


    public void enviarMensajeTexto(String idConversacion
            , Long usuarioEmisor, List<Miembro> usuariosDestino
            , String mensaje, boolean esGrupo, MsgTypes type, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> enviarMensaje = new HashMap<>();

        if (!esGrupo) {


            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion
                    , new UsuarioConversation(getDateInMilis(), true, mensaje, Long.valueOf(usuariosDestino.get(0).getId())));

            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion
                    , new UsuarioConversation(getDateInMilis(), true, mensaje, Long.valueOf(getUsuario().getId())));
        }

        databaseReference.updateChildren(enviarMensaje);
        enviarMensaje = new HashMap<>();
        String keyMessage = generateKey(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes");
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage
                , new Mensajes(usuarioEmisor, getDateInMilis(), mensaje, type.getValue(), usuario.getFoto(), SessionManager.getInstance().getUser().getFoto()));
        Log.i(TAG, "--->" + enviarMensaje);
        databaseReference.updateChildren(enviarMensaje, new DatabaseReference.CompletionListener()

        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void enviarMensajeImagen(String idConversacion, Long usuarioEmisor
            , List<Miembro> usuariosDestino, String mensaje
            , String path, boolean esGrupo, MsgTypes type, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> enviarMensaje = new HashMap<>();

        if (!esGrupo) {


            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion
                    , new UsuarioConversation(getDateInMilis(), true, mensaje, usuariosDestino.get(0).getId()));

            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion
                    , new UsuarioConversation(getDateInMilis(), true, mensaje, getUsuario().getId()));
        }

        String keyMessage = generateKey(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes");
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage
                , new Mensajes(usuarioEmisor, getDateInMilis(), "", "imagen", path, SessionManager.getInstance().getUser().getFoto()));
        databaseReference.updateChildren(enviarMensaje);
    }

    public void enviarMensajeVideo(String idConversacion, Long usuarioEmisor, List<String> usuariosDestino, String mensaje, String path) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> enviarMensaje = new HashMap<>();
        for (String usuarioDestino : usuariosDestino) {
            enviarMensaje.put("usuariosConversaciones/" + usuarioDestino + "/" + idConversacion + "/ultimo_mensaje"
                    , new UsuarioConversation(getDateInMilis(), true, mensaje, Long.valueOf(usuarioDestino)));

        }
        String keyMessage = generateKey(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes");
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes+ " + keyMessage
                , new Mensajes(usuarioEmisor, getDateInMilis(), mensaje, "video", path, SessionManager.getInstance().getUser().getFoto()));
        databaseReference.updateChildren(enviarMensaje);
    }

    public void borrarConversacion(final Long usuario, final String conversacion) {


        Query myRef = secondaryDatabase.getReference()
                .child(ModelKeys.CONVERSATION + "/" + conversacion + "/mensajes").limitToLast(1);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                String idUltimoMensaje = dataSnapshot.getKey();
                Map<String, Object> bloquear = new HashMap<>();
                bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/id_primer_mensaje", idUltimoMensaje);
                bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/status", false);
                databaseReference.updateChildren(bloquear);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void verMensajesConversacion(final Long usuario, final String conversacion) {


        Query myRef = secondaryDatabase.getReference()
                .child(ModelKeys.USER_CONVERSATION + "/"
                        + usuario + "/" + ModelKeys.CONVERSATION + "/"
                        + ModelKeys.ID_PRIMER_MENSAJE);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                } else {

                }
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                String idUltimoMensaje = dataSnapshot.getKey();
                Map<String, Object> bloquear = new HashMap<>();
                bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/id_primer_mensaje", idUltimoMensaje);
                bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/status", false);
                databaseReference.updateChildren(bloquear);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void uploadImage(Uri foto, final FireGroupResultListener fireResultListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://millos-backend.appspot.com");
        StorageReference storageRef = storage.getReference();

        StorageReference riversRef = storageRef.child("images/" + foto.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(foto);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                fireResultListener.onError();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fireResultListener.onComplete(taskSnapshot.getDownloadUrl().toString());

            }
        });
    }

    public void updateBloqueo(final Long idUser, final Long UserToBlock, final boolean bloqueo) {


        Query myRef = secondaryDatabase.getReference()
                .child(ModelKeys.FRIENDS + "/" + idUser + "/" + UserToBlock + "/id_conversacion");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                Map<String, Object> bloquear = new HashMap<>();
                bloquear.put(ModelKeys.FRIENDS + "/" + idUser + "/" + UserToBlock + "/bloqueado", !bloqueo);
                bloquear.put(ModelKeys.USER_CONVERSATION + "/" + idUser + "/" + dataSnapshot.getValue() + "/status", bloqueo);
                databaseReference.updateChildren(bloquear);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void isFriendInvited(String myID, String friendsID, final FireValuesListener fireValuesListener) {
        DatabaseReference myRef = secondaryDatabase.getReference();

        myRef.child(ModelKeys.SEND_REQUEST).child(myID).child(friendsID).child("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.getValue().toString().equals("0")) {
                        fireValuesListener.onComplete("0");
                    } else if (dataSnapshot.getValue().toString().equals("1")) {
                        fireValuesListener.onComplete("1");
                    }
                } else {
                    fireValuesListener.onComplete("2");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireValuesListener.onCanceled();
            }
        });
    }


    ///////////////////////////////SOLICITUDES////////////////////////////////////////

    public void getSugeridos(final FireListener<List<Usuario>> fireListener) {


    }


    //FUNCIONA BIEN
    public void getSolicitudesPendientes(String idUser, final FireListener<Usuario> fireListener) {

        addRequestListener(idUser, new FireListener<Solicitud>() {
            @Override
            public void onDataChanged(final Solicitud dataSolicitud) {

                for (Solicitud solicitud1 : usuario.getSolicitudesPendientes()) {
                    if (solicitud1.getKey().equals(dataSolicitud.getKey())) {
                        solicitud1.copy(dataSolicitud);
                        fireListener.onDataChanged(usuario);
                        return;
                    }
                }
                usuario.setSolicitudPendientes(dataSolicitud);

                addMemberListener(dataSolicitud.getKey(), false, new FireListener<Miembro>() {
                    @Override
                    public void onDataChanged(Miembro data) {
                        dataSolicitud.setMiembro(data);
                        fireListener.onDataChanged(usuario);
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
                usuario.deleteSolicitudPendientes(id);
                fireListener.onDataChanged(usuario);
            }

            @Override
            public void onCancelled() {

            }
        });

    }


    //FUNCIONA BIEN
    public void getSolcitudesEnviadas(String idUser, final FireListener<Usuario> fireListener) {


        addSendRequestListener(idUser, new FireListener<Solicitud>() {
            @Override
            public void onDataChanged(final Solicitud dataSolicitud) {

                for (Solicitud solicitud1 : usuario.getSolicitudesEnviadas()) {
                    if (solicitud1.getKey().equals(dataSolicitud.getKey())) {
                        solicitud1.copy(dataSolicitud);
                        fireListener.onDataChanged(usuario);
                        return;
                    }
                }
                usuario.setSolicitudEnviadas(dataSolicitud);

                addMemberListener(dataSolicitud.getKey(), false, new FireListener<Miembro>() {
                    @Override
                    public void onDataChanged(Miembro data) {
                        dataSolicitud.setMiembro(data);
                        fireListener.onDataChanged(usuario);
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
                usuario.deleteSolicitudEnviadas(id);
                fireListener.onDataChanged(usuario);
            }

            @Override
            public void onCancelled() {

            }
        });


    }


    /////////////////////////////////amistad //////////////////////////////////

    private void getFriends(String idUser, final FireListener<Usuario> usuarioFireListener) {


        addFriendsListener(idUser, new FireListener<Amigos>() {
            @Override
            public void onDataChanged(final Amigos dataAmigo) {

                for (Amigos amigo : usuario.getAmigos()) {
                    if (amigo.getId().equals(dataAmigo.getId())) {
                        amigo.copy(dataAmigo);
                        usuarioFireListener.onDataChanged(usuario);
                        return;
                    }
                }

                usuario.setAmigo(dataAmigo);

                addConversationListener(dataAmigo.getId_conversacion(), new FireListener<Conversacion>() {
                    @Override
                    public void onDataChanged(final Conversacion dataConversacion) {


                        if (dataAmigo.getConversacion() != null) {
                            if (dataConversacion != null)
                                dataAmigo.getConversacion().copy(dataConversacion);
                            usuarioFireListener.onDataChanged(usuario);
                            return;
                        }
                        if (dataConversacion == null)
                            return;
                        dataAmigo.setConversacion(dataConversacion);

                        addMemberListener(String.valueOf(dataAmigo.getId()), false, new FireListener<Miembro>() {
                            @Override
                            public void onDataChanged(Miembro data) {

                                dataConversacion.addMiembro(data);
                                usuarioFireListener.onDataChanged(usuario);
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
            }

            @Override
            public void onDataDelete(String id) {
                usuario.deleteFriend(id);
                usuarioFireListener.onDataChanged(usuario);
            }

            @Override
            public void onCancelled() {

            }
        });


    }

    ////////////////////////////////Conversacion////////////////////////////////////

    public void getUserConversation(String idUser, final FireListener<Usuario> fireListener) {

        addUserConversationListener(idUser, new FireListener<List<UsuarioConversation>>() {
            @Override
            public void onDataChanged(List<UsuarioConversation> data) {
                usuario.setUsuarioConversations(data);
                fireListener.onDataChanged(usuario);
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    public void getMessage(Long idUser, String idConv, final FireListener<Mensajes> fireListener) {

        addMensajesListener(String.valueOf(idUser), idConv, new FireListener<Mensajes>() {
            @Override
            public void onDataChanged(Mensajes data) {
                fireListener.onDataChanged(data);
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });

    }


    /////////////////////////////////GRUPOS/////////////////////////////////////////

    public void crearGrupo(final String nombre, Uri foto, final List<Miembro> miembros, final FireGroupResultListener fireResultListener) {

        uploadImage(foto, new FireGroupResultListener() {
            @Override
            public void onComplete(String idGroup) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                Map<String, Object> solic = new HashMap<>();
                Miembro miembro = new Miembro();
                miembro.setId(usuario.getId());

                miembros.add(miembro);
                String keyConv = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION).push().getKey();
                solic.put(ModelKeys.CONVERSATION + "/" + keyConv, new Conversacion(getDateInMilis()));
                final String keyGrupo = secondaryDatabase.getReference().child(ModelKeys.GROUP).push().getKey();
                solic.put(ModelKeys.GROUP + "/" + keyGrupo, new Grupo(getDate(), idGroup, keyConv, nombre));


                databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        for (Miembro miembro : miembros) {
                            invitarUsuarioGrupo(miembro.getId(), keyGrupo, nombre, new FireResultListener() {
                                @Override
                                public void onComplete() {
                                    fireResultListener.onComplete(keyGrupo);
                                }

                                @Override
                                public void onError() {
                                    fireResultListener.onError();
                                }
                            });
                        }


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


    public void changeUserState(int state, final FireResultListener fireResultListener) {

        if (usuario == null)
            return;
        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.USER + "/" + usuario.getId() + "/" + "chat_status", state);

        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void invitarUsuarioGrupo(Long usuario, String grupo, String nombreGrupo, final FireResultListener fireResultListener) {
        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();

        solic.put(ModelKeys.GROUP_X_USER + "/" + grupo + "/" + usuario, true);
        solic.put(ModelKeys.USER_X_GROUP + "/" + usuario + "/" + grupo, nombreGrupo);


        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void getGrupos(String userId, final FireListener<Usuario> usuarioFireListener) {


        addUserXGroupListener(userId, new FireListener<UsuarioGrupo>() {
            @Override
            public void onDataChanged(UsuarioGrupo data) {

                for (int i = 0; i < data.getGrupos().size(); i++) {
                    final String idGrupo = data.getGrupos().get(i);
                    addGroupListener(idGrupo, new FireListener<Grupo>() {
                        @Override
                        public void onDataChanged(final Grupo dataGrupo) {

                            usuario.setGrupos(dataGrupo);
                            addConversationListener(dataGrupo.getId_conversacion(), new FireListener<Conversacion>() {
                                @Override
                                public void onDataChanged(final Conversacion dataConversacion) {

                                    dataGrupo.setConversacion(dataConversacion);
                                    addGroupXUserListener(idGrupo, new FireListener<GrupoUsuarios>() {
                                        @Override
                                        public void onDataChanged(GrupoUsuarios data) {

                                            for (int i = 0; i < data.getUsuarios().size(); i++) {
                                                final String idUser = data.getUsuarios().get(i);
                                                addMemberListener(idUser, true, new FireListener<Miembro>() {
                                                    @Override
                                                    public void onDataChanged(Miembro data) {

                                                        if (dataConversacion != null && data != null)
                                                            dataConversacion.addMiembro(data);
                                                        usuarioFireListener.onDataChanged(usuario);
                                                    }

                                                    @Override
                                                    public void onDataDelete(String id) {
                                                        Log.i("FIREBASE", " ---> 6to nivel DELETED" + id);
                                                    }

                                                    @Override
                                                    public void onCancelled() {
                                                        Log.i("FIREBASE", " ---> 6to nivel CANCELLED");
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onDataDelete(String id) {
                                            Log.i("FIREBASE", " ---> 5to nivel DELETED" + id);
                                        }

                                        @Override
                                        public void onCancelled() {
                                            Log.i("FIREBASE", " ---> 5to nivel CANCELLED");
                                        }
                                    });

                                }

                                @Override
                                public void onDataDelete(String id) {
                                    Log.i("FIREBASE", " ---> 4to nivel DELETED" + id);
                                }

                                @Override
                                public void onCancelled() {
                                    Log.i("FIREBASE", " ---> 4to nivel CANCELLED");
                                }
                            });
                        }

                        @Override
                        public void onDataDelete(String id) {
                            Log.i("FIREBASE", " ---> 3er nivel DELETED" + id);
                        }

                        @Override
                        public void onCancelled() {
                            Log.i("FIREBASE", " ---> 3er nivel CANCELLED");
                        }
                    });
                }
            }

            @Override
            public void onDataDelete(String id) {
                Log.i("FIREBASE", " ---> 2do nivel DELETED" + id);
            }

            @Override
            public void onCancelled() {
                Log.i("FIREBASE", " ---> 2do nivel CANCELLED");
            }
        });

    }


    ///////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////

    public void getAllMensajesListener(final String userId, final String idConv, final FireListener<List<Mensajes>> mensajesFireListener) {

        Query myRef = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION + "/" + idConv + "/mensajes");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Mensajes> mensajes = new ArrayList<>();
                for (DataSnapshot da : dataSnapshot.getChildren()) {
                    Mensajes mensaje = (Mensajes) parseObject(da.getValue(), Mensajes.class);
                    mensaje.setId(da.getKey());
                    if (mensaje != null)
                        mensajes.add(mensaje);

                }
                if (mensajes.size() > 0)
                    mensajes.remove(mensajes.size() - 1);
                mensajesFireListener.onDataChanged(mensajes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mensajesFireListener.onCancelled();
            }
        });
    }

    String seach = "";

    public void buscarUsuario(final String busqueda, final FireListener<List<RequestModelView>> miembros) {

        seach = busqueda;

        DatabaseReference mDatabase = secondaryDatabase.getReference().child(ModelKeys.USER + "/");

        mDatabase.orderByChild("nombre")
                .startAt(busqueda)
                .endAt(busqueda + "\uf8ff").limitToFirst(10)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {

                        Log.i(TAG, "--->" + dataSnapshot.toString());
                        if (!busqueda.equals(seach)) {
                            return;
                        }

                        new AsyncJob.AsyncJobBuilder<List<RequestModelView>>()
                                .doInBackground(new AsyncJob.AsyncAction<List<RequestModelView>>() {
                                    @Override
                                    public List<RequestModelView> doAsync() {

                                        List<RequestModelView> miembros1 = new ArrayList<>();
                                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                                            final Miembro miembro = (Miembro) parseObject(data.getValue(), Miembro.class);
                                            miembro.setId(Long.valueOf(data.getKey()));

                                            boolean isFriend = false;
                                            for (Amigos amigo : usuario.getAmigos()) {
                                                if (amigo.getId().equals(miembro.getId())) {
                                                    isFriend = true;
                                                    break;
                                                }
                                            }
                                            if (!isFriend)
                                                miembros1.add(new RequestModelView(miembro.getId(), miembro.getApodo(), miembro.getFoto(), miembro.getNombre(), false));

                                        }


                                        return miembros1;
                                    }
                                })
                                .doWhenFinished(new AsyncJob.AsyncResultAction<List<RequestModelView>>() {
                                    @Override
                                    public void onResult(List<RequestModelView> result) {
                                        if (busqueda.equals(seach)) {
                                            Log.i(TAG, "--->doWhenFinished true");
                                            miembros.onDataChanged(result);
                                        } else {
                                            Log.i(TAG, "--->doWhenFinished false");
                                        }
                                    }
                                }).create().start();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addMensajesListener(final String userId, final String idConv, final FireListener<Mensajes> mensajesFireListener) {

        Query myRef = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION + "/" + idConv + "/mensajes")
                .limitToLast(1);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot da : dataSnapshot.getChildren()) {
                    Mensajes mensajes = (Mensajes) parseObject(da.getValue(), Mensajes.class);
                    mensajes.setId(da.getKey());

                    mensajesFireListener.onDataChanged(mensajes);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addUserConversationListener(final String idUser, final FireListener<List<UsuarioConversation>> fireListener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.USER_CONVERSATION)
                .child(idUser).orderByChild("status").equalTo(true);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                    fireListener.onDataDelete(null);

                List<UsuarioConversation> usuarioConversations = new ArrayList<>();
                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {
                    UsuarioConversation usuarioConversation = (UsuarioConversation) parseObject(dataSnapsho2t.getValue(), UsuarioConversation.class);
                    if (usuarioConversation != null) {
                        usuarioConversation.setId(dataSnapsho2t.getKey());
                        usuarioConversations.add(usuarioConversation);

                    } else {
                        fireListener.onDataDelete(dataSnapsho2t.getKey());
                    }

                }
                fireListener.onDataChanged(usuarioConversations);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireListener.onCancelled();
            }
        });
    }

    public void getSugger(Long idUser, final FireListener<List<RequestModelView>> fireListener) {


        addGetSugUserListener(idUser, new FireListener<List<RequestModelView>>() {
            @Override
            public void onDataChanged(List<RequestModelView> data) {
                if (data != null) {
                    fireListener.onDataChanged(listSugerencias);
                    EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_SOLICITUDES_ENVIADAS));
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

    private void addSendRequestListener(final String idUser, final FireListener<Solicitud> fireListener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.SEND_REQUEST).child(idUser);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                    fireListener.onDataDelete(null);

                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {
                    Solicitud solicitud = (Solicitud) parseObject(dataSnapsho2t.getValue(), Solicitud.class);
                    if (solicitud != null) {
                        solicitud.setKey(dataSnapsho2t.getKey());
                        fireListener.onDataChanged(solicitud);
                    } else {
                        fireListener.onDataDelete(dataSnapsho2t.getKey());
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireListener.onCancelled();
            }
        });
    }

    private void addRequestListener(final String idUser, final FireListener<Solicitud> fireListener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.REQUEST).child(idUser);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                    fireListener.onDataDelete(null);

                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {
                    Solicitud solicitud = (Solicitud) parseObject(dataSnapsho2t.getValue(), Solicitud.class);
                    if (solicitud != null) {
                        solicitud.setKey(dataSnapsho2t.getKey());
                        fireListener.onDataChanged(solicitud);
                    } else {
                        fireListener.onDataDelete(dataSnapsho2t.getKey());
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireListener.onCancelled();
            }
        });
    }

    private void addGroupListener(final String groupKey, final FireListener<Grupo> fireListener) {
        Query myRef = secondaryDatabase.getReference(ModelKeys.GROUP).child(groupKey);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Grupo grupo = (Grupo) parseObject(dataSnapshot.getValue(), Grupo.class);
                if (grupo != null) {
                    grupo.setKey(dataSnapshot.getKey());
                    fireListener.onDataChanged(grupo);
                } else {
                    fireListener.onDataDelete(dataSnapshot.getKey());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static Object parseObject(Object object, Class t) {
        Log.i(TAG, "//--->parseObject 1: " + object);
        Gson gson = new Gson();
        Object obj = gson.fromJson(gson.toJson(object), t);
        Log.i(TAG, "//--->parseObject 2: " + obj);
        return obj;
    }


    private void addGroupXUserListener(final String groupId, final FireListener<GrupoUsuarios> fireListener) {
        Query myRef = secondaryDatabase.getReference(ModelKeys.GROUP_X_USER).child(groupId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    GrupoUsuarios grupoUsuarios = new GrupoUsuarios();
                    grupoUsuarios.setIdGrupo(groupId);
                    for (DataSnapshot dns : dataSnapshot.getChildren()) {
                        grupoUsuarios.setUsuario(dns.getKey());


                    }
                    fireListener.onDataChanged(grupoUsuarios);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireListener.onCancelled();
            }
        });
    }

    private void addUserXGroupListener(final String userId, final FireListener<UsuarioGrupo> fireListener) {
        Query myRef = secondaryDatabase.getReference(ModelKeys.USER_X_GROUP).child(userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
                    usuarioGrupo.setUserId(userId);
                    for (DataSnapshot dns : dataSnapshot.getChildren()) {
                        usuarioGrupo.setGrupo(dns.getKey());

                    }
                    fireListener.onDataChanged(usuarioGrupo);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void addGetSugUserListener(Long idUser, final FireListener<List<RequestModelView>> listener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.SUGERENCIA + "/" + idUser);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listSugerencias = new ArrayList<>();
                final Count count = new Count(dataSnapshot.getChildrenCount());
                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {

                    Query myRef = secondaryDatabase.getReference(ModelKeys.USER + "/" + dataSnapsho2t.getKey());
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            final Miembro miembro = (Miembro) parseObject(dataSnapshot.getValue(), Miembro.class);

                            if (miembro != null) {
                                miembro.setId(Long.valueOf(dataSnapshot.getKey()));


                                listSugerencias.add(new RequestModelView(miembro.getId(), miembro.getApodo(), miembro.getFoto(), miembro.getNombre(), false));

                            }
                            if (count.verificateLimit())
                                listener.onDataChanged(listSugerencias);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            listener.onCancelled();
                        }
                    });

                }
                listener.onDataChanged(listSugerencias);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void addMyUserListener(final String userId, boolean oneTime, final FireListener<Usuario> listener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.USER).child(userId);

        if (oneTime) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Usuario usuario = (Usuario) parseObject(dataSnapshot.getValue(), Usuario.class);

                    if (usuario != null) {
                        usuario.setId(Long.valueOf(dataSnapshot.getKey()));
                        listener.onDataChanged(usuario);
                    } else {
                        listener.onDataDelete(dataSnapshot.getKey());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onCancelled();
                }
            });
        } else {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    Usuario usuario = (Usuario) parseObject(dataSnapshot.getValue(), Usuario.class);

                    if (usuario != null) {
                        usuario.setId(Long.valueOf(dataSnapshot.getKey()));
                        listener.onDataChanged(usuario);
                    } else {
                        listener.onDataDelete(dataSnapshot.getKey());
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listener.onCancelled();
                }
            });
        }

    }

    public void startListener(final String idUser, final FireListener<Usuario> fireListener) {

        addMyUserListener(idUser, false, new FireListener<Usuario>() {
            @Override
            public void onDataChanged(Usuario data) {

                if (usuario != null) {
                    usuario.copy(usuario);
                    fireListener.onDataChanged(usuario);
                    return;

                }
                usuario = data;
                getFriends(String.valueOf(data.getId()), new FireListener<Usuario>() {
                    @Override
                    public void onDataChanged(Usuario data) {
                        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_AMIGOS));
                        // fireListener.onDataChanged(usuario);

                    }

                    @Override
                    public void onDataDelete(String id) {
                        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_AMIGOS));
                    }

                    @Override
                    public void onCancelled() {

                    }
                });

                getUserConversation(idUser, new FireListener<Usuario>() {
                    @Override
                    public void onDataChanged(Usuario data) {
                        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.USER_CONVERSATION));
                    }

                    @Override
                    public void onDataDelete(String id) {

                    }

                    @Override
                    public void onCancelled() {

                    }
                });

                getGrupos(String.valueOf(data.getId()), new FireListener<Usuario>() {
                    @Override
                    public void onDataChanged(Usuario data) {
                        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_GRUPOS));
                        //     fireListener.onDataChanged(usuario);
                    }

                    @Override
                    public void onDataDelete(String id) {
                        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_GRUPOS));
                    }

                    @Override
                    public void onCancelled() {

                    }
                });
                (new SolicitudesControllers(secondaryDatabase, true)).addValueRequestListener(idUser);
                (new SolicitudesControllers(secondaryDatabase, false)).addValueRequestListener(idUser);

            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    private void addFriendsListener(String userId, final FireListener<Amigos> amigosFireListener) {
        Query myRef = secondaryDatabase.getReference(ModelKeys.FRIENDS).child(userId);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null)
                    amigosFireListener.onDataDelete(null);
                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {
                    Amigos amigos = (Amigos) parseObject(dataSnapsho2t.getValue(), Amigos.class);
                    if (amigos != null) {
                        amigos.setId(Long.valueOf(dataSnapsho2t.getKey()));
                        amigosFireListener.onDataChanged(amigos);
                    } else {
                        amigosFireListener.onDataDelete(dataSnapsho2t.getKey());
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                amigosFireListener.onCancelled();
            }
        });
    }

    private void addConversationListener(String idConversation, final FireListener<Conversacion> fireListener) {

        final Query myRef = secondaryDatabase.getReference(ModelKeys.CONVERSATION).child(idConversation);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
                if (conversacion != null) {
                    List<Mensajes> mensajes = conversacion.getMensajes();
                    conversacion.setId(dataSnapshot.getKey());
                    fireListener.onDataChanged(conversacion);
                } else {
                    //myRef.removeEventListener(this);
                    fireListener.onDataDelete(null);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    private void addMemberListener(final String miembro, boolean oneTime, final FireListener<Miembro> fireListener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.USER).child(miembro);

        if (oneTime) {
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Miembro miembro = (Miembro) parseObject(dataSnapshot.getValue(), Miembro.class);
                    if (miembro != null) {
                        miembro.setId(Long.valueOf(dataSnapshot.getKey()));

                        fireListener.onDataChanged(miembro);

                    } else {
                        myRef.removeEventListener(this);
                        fireListener.onDataDelete(dataSnapshot.getKey());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    final Miembro miembro = (Miembro) parseObject(dataSnapshot.getValue(), Miembro.class);
                    if (miembro != null) {
                        miembro.setId(Long.valueOf(dataSnapshot.getKey()));


                        fireListener.onDataChanged(miembro);

                    } else {
                        myRef.removeEventListener(this);
                        fireListener.onDataDelete(dataSnapshot.getKey());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }


    private interface ModelKeys {

        String GROUP = "grupos";
        String REQUEST = "solicitudes";
        String SEND_REQUEST = "solicitudesEnviadas";
        String GROUP_X_USER = "gruposUsuarios";
        String USER_X_GROUP = "usuarioGrupos";
        String USER = "usuarios";
        String USER_CONVERSATION = "usuariosConversaciones";
        String FRIENDS = "amigos";
        String CONVERSATION = "conversaciones";
        String BLOQUEADO = "bloqueado";

        String ID_PRIMER_MENSAJE = "id_primer_mensaje";
        String SUGERENCIA = "sugerencias";
    }


//////////////////////////////////////////////////////////////////////////////////////


    public interface FireListener<T> {


        void onDataChanged(T data);

        void onDataDelete(String id);

        void onCancelled();
    }

    public interface FireResultListener {


        void onComplete();

        void onError();

    }


    public interface FireGroupResultListener extends FireResultListener {


        void onComplete(String idGroup);


    }

    public interface FireValuesListener {


        void onComplete(String value);

        void onCanceled();
    }


}
