package com.BarcelonaSC.BarcelonaApp.app.manager;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend.FriendControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.group.GroupControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.media.MediaController;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.models.firebase.GroupValueListenerModel;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.GrupoUsuarios;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Media;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Mensajes;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioConversation;
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioGrupo;
import com.BarcelonaSC.BarcelonaApp.models.response.WallSearchResponse;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.arasthel.asyncjob.AsyncJob;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {

    private static final String TAG = FirebaseManager.class.getSimpleName();

    public Boolean fistTime = true;
    public FirebaseDatabase secondaryDatabase;
    public List<ValueEventListener> valueEventListeners = new ArrayList<>();
    public List<ChildEventListener> valueChildremEventListeners = new ArrayList<>();
    public List<GroupValueListenerModel> groupValueListenerModels = new ArrayList<>();

    public void clearFirebaseManager() {
        fistTime = true;
        Query query = secondaryDatabase.getReference();
        usuario = null;
        for (ValueEventListener value : valueEventListeners) {
            query.removeEventListener(value);
        }
        for (ChildEventListener value : valueChildremEventListeners) {
            query.removeEventListener(value);
        }
        groupValueListenerModels.clear();
        ourInstance = null;
    }

    public void setUser(UserItem user) {
        if (usuario != null && user != null)
            usuario.setUser(user);
    }

    public enum MsgTypes {

        TEXTO("texto"), IMAGEN("imagen"), GIF("gif"), VIDEO("video"), INFO("info");

        private String value = "";

        MsgTypes(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

    Usuario usuario = null;

    private static FirebaseManager ourInstance;

    private FirebaseManager() {
        Log.i("tag", "/*/*/*--->FirebaseManager");
        secondaryDatabase = FirebaseDatabase.getInstance();
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

        if (usuario == null) {
            initFirebase();
            return new Usuario();
        }
        return usuario;
    }


    public void getBlockUsers(FriendControllers.BlockMemberListener blockMemberListener) {
        new FriendControllers(secondaryDatabase).getBlockUsers(SessionManager.getInstance().getUser().getId_usuario(), blockMemberListener);
    }

    public void initFirebase() {
        // Manually configure Firebase Options
        Log.i("FIREBASE", " ---> init");
        /*FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:528683318056:android:61a3130da854834c") // Required for Analytics.
                .setApiKey("AIzaSyCI58q_-hZwjFctMCniJsOeQPbQsiP137g") // Required for Auth.
                .setDatabaseUrl("https://millos-backend.firebaseio.com") // Required for RTDB.
                .build();*/


        // FirebaseApp.initializeApp(App.get() /* Context */,, );
/*
        //.setDatabaseUrl("https://millos-fc.firebaseio.com") // Esta es la nueva url segun Julio. La dejo comentada por que con la otra funciono


        FirebaseApp.initializeApp(App.get() */
/* Context *//*
, options, "secondary");
*/

        // Retrieve secondary app.
        //      FirebaseApp secondary = FirebaseApp.getInstance();


        // Get the database for the other app.
        if (!fistTime)
            return;

        fistTime = false;
        new AsyncJob.AsyncJobBuilder<String>()
                .doInBackground(new AsyncJob.AsyncAction<String>() {
                    @Override
                    public String doAsync() {
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
                      /*  getSugger(Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()), new FireListener<List<RequestModelView>>() {
                            @Override
                            public void onDataChanged(List<RequestModelView> data) {

                            }

                            @Override
                            public void onDataDelete(String id) {

                            }

                            @Override
                            public void onCancelled() {

                            }
                        });*/
                        return null;
                    }
                })
                .doWhenFinished(new AsyncJob.AsyncResultAction<String>() {
                    @Override
                    public void onResult(String result) {

                    }
                }).create().start();

        Log.i(TAG, "--->IDUSERIO " + SessionManager.getInstance().getUser().getId_usuario());

        //getSugeridos();
    }


    //////////////////////////////FUNCIONES VARIAS////////////////////////////////////
    private String generateKey(String ref) {
        return secondaryDatabase.getReference().child(ref).push().getKey();
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
/*
    private Long getDateInMilis() {
        return new DateTime().getMillis() / 1000;
    }*/


    private Map<String, String> getFirebaseDate() {
        return ServerValue.TIMESTAMP;
    }

    public void getCreatedAtFromUser(long id, final FireValuesListener fireValuesListener) {
        DatabaseReference myRef = secondaryDatabase.getReference();

        myRef.child(ModelKeys.USER).child(String.valueOf(id)).child("created_at").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fireValuesListener.onComplete(dataSnapshot.getValue().toString());
                } else {
                    fireValuesListener.onComplete("0");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireValuesListener.onCanceled();
            }
        });
    }


    ////////////////////////////////REQUEST /////////////////////////////

    public void getNumberHinchas(final FireValuesListener fireValuesListener) {
        DatabaseReference databaseReference = secondaryDatabase.getReference();
        FirebaseManager.getInstance().valueEventListeners.add(databaseReference.child("estadisticas/total_usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.exists())
                    fireValuesListener.onComplete(dataSnapshot.getValue().toString());
                else
                    fireValuesListener.onCanceled();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireValuesListener.onCanceled();
            }
        }));
    }

    // FUNCIONA BIEN
    public void enviarNuevaSolicitud(String usuarioSolicita, String usuarioSolicitado, final FireResultListener fireResultListener) {

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita + "/fecha", getFirebaseDate());
        solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita + "/status", 0);

        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita + "/" + usuarioSolicitado + "/fecha", getFirebaseDate());
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
    public void aceptarSolicitud(final FriendsModelView usuarioSolicita, final String usuarioSolicitado, final FireFriendsResultListener fireResultListener) {

        Log.i(TAG, "111--->usuarioSolicita: " + usuarioSolicita.getLongId() + "  " + usuarioSolicitado);
        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> solic = new HashMap<>();
      /*  solic.put(ModelKeys.REQUEST + "/" + usuarioSolicitado + "/" + usuarioSolicita.getId(), new Solicitud(getDate(), 1));
        solic.put(ModelKeys.SEND_REQUEST + "/" + usuarioSolicita.getId() + "/" + usuarioSolicitado, new Solicitud(getDate(), 1));*/
        String keyConv = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION).push().getKey();
        if (keyConv == null) {
            Log.i(TAG, "1113213131--->usuarioSolicita: " + usuarioSolicita.getLongId() + "  " + usuarioSolicitado);
            fireResultListener.onError();
            return;
        }

        final Amigos amigos = new Amigos(usuarioSolicita.getLongId(), getDate(), keyConv, new Conversacion(getDate()));
        Miembro miembro = new Miembro();
        miembro.setFoto(usuarioSolicita.getPhoto());
        miembro.setId(usuarioSolicita.getLongId());
        miembro.setApodo(usuarioSolicita.getApodo());
        amigos.getConversacion().setMiembros(Collections.singletonList(miembro));
        amigos.getConversacion().setId(keyConv);
        solic.put(ModelKeys.CONVERSATION + "/" + keyConv + "/fecha", getFirebaseDate());

        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicitado + "/" + usuarioSolicita.getLongId() + "/bloqueado", false);
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicitado + "/" + usuarioSolicita.getLongId() + "/fecha_amistad", getFirebaseDate());
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicitado + "/" + usuarioSolicita.getLongId() + "/id_conversacion", keyConv);

        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicita.getLongId() + "/" + usuarioSolicitado + "/bloqueado", false);
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicita.getLongId() + "/" + usuarioSolicitado + "/fecha_amistad", getFirebaseDate());
        solic.put(ModelKeys.FRIENDS + "/" + usuarioSolicita.getLongId() + "/" + usuarioSolicitado + "/id_conversacion", keyConv);


        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.i(TAG, "1111111--->usuarioSolicita: " + usuarioSolicita + "  " + usuarioSolicitado);
                if (databaseError != null) {
                    Log.i(TAG, "1111111--->usuarioSolicita: " + databaseError.getMessage() + "   " + databaseError.getDetails());
                    fireResultListener.onError();
                } else {
                    fireResultListener.onComplete(amigos);
                }
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
        solic.put(ModelKeys.GROUP_X_USER + "/" + idGrupo + "/" + userId, true);
        solic.put(ModelKeys.USER_X_GROUP + "/" + userId + "/" + idGrupo, true);
        databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void salirUsuarioGrupo(final Long userId, final String idGrupo, final boolean isAdmin, final FireResultListener fireResultListener) {

        final DatabaseReference databaseReference = secondaryDatabase.getReference();
        Query myRef = secondaryDatabase.getReference(ModelKeys.GROUP_X_USER)
                .child(idGrupo);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null)
                    return;
                Map<String, Object> solic = new HashMap<>();
                if (isAdmin) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        if (!userId.equals(Long.valueOf(data.getKey()))) {
                            solic.put(ModelKeys.GROUP + "/" + idGrupo + "/admin", data.getKey());
                            break;
                        }
                    }
                }
                solic.put(ModelKeys.GROUP_X_USER + "/" + idGrupo + "/" + userId, null);
                solic.put(ModelKeys.USER_X_GROUP + "/" + userId + "/" + idGrupo, null);

                for (GroupValueListenerModel group : groupValueListenerModels) {
                    if (idGrupo.equals(group.getId())) {
                        group.setListener(false);
                        break;
                    }
                }

                databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        verificateResult(databaseError, fireResultListener);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void eliminarGrupo(Grupo grupo, final FireResultListener fireResultListener) {
        Log.i(TAG, "--->eliminando grupo " + grupo.getKey());
        DatabaseReference databaseReference = secondaryDatabase.getReference();

        databaseReference.child(ModelKeys.GROUP).child(grupo.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                fireResultListener.onComplete();
            }
        });
    }

    public void enviarMensajeTexto(String idConversacion
            , Long usuarioEmisor, List<Miembro> usuariosDestino
            , String mensaje, String idGrupo, MsgTypes type, final FireResultListener fireResultListener) {

        if (getUsuario().getId() == null
                || usuariosDestino.get(0).getId() == null
                || idConversacion == null)
            return;

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> enviarMensaje = new HashMap<>();

        if (idGrupo == null) {


            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/status", true);
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/fecha", getFirebaseDate());
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/ultimo_mensaje", mensaje);
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/id_participante", usuariosDestino.get(0).getId());

            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/status", true);
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/fecha", getFirebaseDate());
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/ultimo_mensaje", mensaje);
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/id_participante", getUsuario().getId());

        } else {
            enviarMensaje.put(ModelKeys.GROUP + "/" + idGrupo + "/ultimo_mensaje", mensaje);
            enviarMensaje.put(ModelKeys.GROUP + "/" + idGrupo + "/fecha_ultimo_mensaje", getFirebaseDate());
        }


        databaseReference.updateChildren(enviarMensaje);
        enviarMensaje = new HashMap<>();
        String keyMessage = generateKey(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes");
        if (keyMessage == null) {
            fireResultListener.onError();
            return;
        }

        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/emisor_mensaje", usuarioEmisor);
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/fecha_mensaje", getFirebaseDate());
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/texto_mensaje", mensaje);
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/tipo_mensaje", type.getValue());

        Log.i(TAG, "--->" + enviarMensaje);
        databaseReference.updateChildren(enviarMensaje, new DatabaseReference.CompletionListener()

        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                verificateResult(databaseError, fireResultListener);
            }
        });
    }

    public void enviarMensajeMultimedia(String idConversacion, Long usuarioEmisor
            , List<Miembro> usuariosDestino, String mensaje
            , String path, String idGrupo, MsgTypes type, String videoThumbnailUrl, final FireResultListener fireResultListener) {

        if (getUsuario().getId() == null
                || usuariosDestino.get(0).getId() == null
                || idConversacion == null)
            return;

        DatabaseReference databaseReference = secondaryDatabase.getReference();
        Map<String, Object> enviarMensaje = new HashMap<>();

        String keyMessage = generateKey(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes");
        if (keyMessage == null) {
            fireResultListener.onError();
            return;
        }

        if (idGrupo == null) {

            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + SessionManager.getInstance().getUser().getId_usuario() + "/" + keyMessage + "/url_media", path);
            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + SessionManager.getInstance().getUser().getId_usuario() + "/" + keyMessage + "/tipo_media", type.getValue());

            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + usuariosDestino.get(0).getId() + "/" + keyMessage + "/url_media", path);
            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + usuariosDestino.get(0).getId() + "/" + keyMessage + "/tipo_media", type.getValue());

            if (type == MsgTypes.VIDEO && videoThumbnailUrl != null) {
                enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + SessionManager.getInstance().getUser().getId_usuario() + keyMessage + "/video_thumbnail", videoThumbnailUrl);
                enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + usuariosDestino.get(0).getId() + "/" + keyMessage + "/video_thumbnail", videoThumbnailUrl);

                enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/video_thumbnail", videoThumbnailUrl);
            }

            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/status", true);
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/ultimo_mensaje", mensaje);
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/fecha", getFirebaseDate());
            enviarMensaje.put("usuariosConversaciones/" + getUsuario().getId() + "/" + idConversacion + "/id_participante", usuariosDestino.get(0).getId());

            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/status", true);
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/fecha", getFirebaseDate());
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/ultimo_mensaje", mensaje);
            enviarMensaje.put("usuariosConversaciones/" + usuariosDestino.get(0).getId() + "/" + idConversacion + "/id_participante", getUsuario().getId());
        } else {
            enviarMensaje.put(ModelKeys.GROUP + "/" + idGrupo + "/ultimo_mensaje", mensaje);
            enviarMensaje.put(ModelKeys.GROUP + "/" + idGrupo + "/fecha_ultimo_mensaje", getFirebaseDate());

            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + keyMessage + "/url_media", path);
            enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + keyMessage + "/tipo_media", type.getValue());
            if (type == MsgTypes.VIDEO && videoThumbnailUrl != null) {
                enviarMensaje.put("usuariosMedia/" + idConversacion + "/" + keyMessage + "/video_thumbnail", videoThumbnailUrl);
                enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/video_thumbnail", videoThumbnailUrl);
            }
        }


        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/emisor_mensaje", usuarioEmisor);
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/fecha_mensaje", getFirebaseDate());
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/texto_mensaje", mensaje);
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/tipo_mensaje", type.getValue());
        enviarMensaje.put(ModelKeys.CONVERSATION + "/" + idConversacion + "/mensajes/" + keyMessage + "/url_imagen", path);

        databaseReference.updateChildren(enviarMensaje, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null)
                    fireResultListener.onComplete();
                else
                    fireResultListener.onError();
            }
        });
    }


    public void borrarConversacion(final Long usuario, final String conversacion, final boolean estado) {


        Query myRef = secondaryDatabase.getReference()
                .child(ModelKeys.CONVERSATION + "/" + conversacion + "/mensajes").limitToLast(1);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String idUltimoMensaje = data.getKey();
                    Map<String, Object> bloquear = new HashMap<>();
                    bloquear.put("usuariosMedia/" + conversacion + "/" + usuario, null);
                    bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/id_primer_mensaje", idUltimoMensaje);
                    bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/ultimo_mensaje", "");
                    bloquear.put(ModelKeys.USER_CONVERSATION + "/" + usuario + "/" + conversacion + "/status", estado);
                    databaseReference.updateChildren(bloquear);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void uploadImageThumbnail(Bitmap foto, final FireGroupResultListener fireResultListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://barcelona-sc-oficial.appspot.com");
        StorageReference storageRef = storage.getReference();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference riversRef = storageRef.child("video_thumbnail/" + foto);
        UploadTask uploadTask = riversRef.putBytes(data);

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

    public void uploadImage(Uri foto, final FireGroupResultListener fireResultListener) {
        FirebaseStorage storage = FirebaseStorage.getInstance("gs://barcelona-sc-oficial.appspot.com");
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


    ///

    ////////////////////////////////Conversacion////////////////////////////////////
    private List<UsuarioConversation> usuarioConversations = new ArrayList<>();

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

    public void getMessage(Long idUser, final String idConv, final FireListener<Mensajes> fireListener) {

        addMensajesListener(String.valueOf(idUser), idConv, new FireListener<Mensajes>() {
            @Override
            public void onDataChanged(Mensajes data) {

                for (UsuarioConversation usuCon : usuario.getUsuarioConversations()) {
                    if (usuCon.getId().equals(idConv)) {
                        if (data.getId().equals(usuCon.getId_primer_mensaje()))
                            return;
                    }
                }

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
        Log.i("dawdad", "--->QWDQDQD");
        uploadImage(foto, new FireGroupResultListener() {
            @Override
            public void onComplete(String foto) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                Map<String, Object> solic = new HashMap<>();
                Miembro miembro = new Miembro();
                miembro.setId(usuario.getId());

                miembros.add(miembro);
                String keyConv = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION).push().getKey();

                solic.put(ModelKeys.CONVERSATION + "/" + keyConv + "/fecha", getFirebaseDate());
                final String keyGrupo = secondaryDatabase.getReference().child(ModelKeys.GROUP).push().getKey();

                solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/foto", foto);
                solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/id_conversacion", keyConv);
                solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/nombre", nombre);
                solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/admin", usuario.getId());
                solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/fecha_creacion", getFirebaseDate());

                if (keyGrupo == null || keyConv == null) {
                    fireResultListener.onError();
                    return;
                }
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


    public void actualizarGrupo(final String nombre, final Uri foto, final String keyGrupo, final FireResultListener fireResultListener) {

        uploadImage(foto, new FireGroupResultListener() {
            @Override
            public void onComplete(String idGroup) {
                DatabaseReference databaseReference = secondaryDatabase.getReference();
                Map<String, Object> solic = new HashMap<>();

                if (foto != null)
                    solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/foto", idGroup);
                if (nombre != null && !nombre.isEmpty())
                    solic.put(ModelKeys.GROUP + "/" + keyGrupo + "/nombre", nombre);

                databaseReference.updateChildren(solic, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        verificateResult(databaseError, fireResultListener);
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


    ///////////////////////////////////////////////////////////////////////////


    //////////////////////////////////////////////////////////////////////////
    public void getAllMensajePaginateListener(final String idConv, String last, final FireListener<List<Mensajes>> mensajesFireListener) {

        secondaryDatabase.getReference(ModelKeys.CONVERSATION).child(idConv + "/mensajes").orderByKey().endAt(last).limitToLast(6)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Mensajes> mensajes = new ArrayList<>();

                        String idPrimerMensaje = null;

                        for (UsuarioConversation usuCon : usuario.getUsuarioConversations()) {
                            if (usuCon.getId().equals(idConv)) {
                                idPrimerMensaje = usuCon.getId_primer_mensaje();
                            }
                        }

                        for (DataSnapshot da : dataSnapshot.getChildren()) {
                            Mensajes mensaje = (Mensajes) parseObject(da.getValue(), Mensajes.class);
                            mensaje.setId(da.getKey());
                            if (mensaje != null) {
                                mensajes.add(mensaje);
                                if (idPrimerMensaje != null && idPrimerMensaje.equals(mensaje.getId())) {
                                    mensajes.clear();
                                }
                            }
                        }
                        if (mensajes.size() > 0)
                            mensajes.remove(mensajes.size() - 1);
                        Collections.reverse(mensajes);
                        mensajesFireListener.onDataChanged(mensajes);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        mensajesFireListener.onCancelled();
                    }
                });
    }


    public void getAllMensajesListener(final String userId, final String idConv, final FireListener<List<Mensajes>> mensajesFireListener) {

        secondaryDatabase.getReference(ModelKeys.CONVERSATION).child(idConv + "/mensajes").limitToLast(6)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Mensajes> mensajes = new ArrayList<>();

                        String idPrimerMensaje = null;

                        for (UsuarioConversation usuCon : usuario.getUsuarioConversations()) {
                            if (usuCon.getId().equals(idConv)) {
                                idPrimerMensaje = usuCon.getId_primer_mensaje();
                            }
                        }

                        for (DataSnapshot da : dataSnapshot.getChildren()) {
                            Mensajes mensaje = (Mensajes) parseObject(da.getValue(), Mensajes.class);
                            mensaje.setId(da.getKey());
                            if (mensaje != null) {
                                mensajes.add(mensaje);
                                if (idPrimerMensaje != null && idPrimerMensaje.equals(mensaje.getId())) {
                                    mensajes.clear();
                                }
                            }
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

    public void buscarUsuarioEnGrupo(Long id_amigo, String id_grupo, final FireValuesListener fireValuesListener) {
        DatabaseReference mDatabase = secondaryDatabase.getReference().child(ModelKeys.GROUP_X_USER).child(id_grupo).child(String.valueOf(id_amigo));

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fireValuesListener.onComplete(String.valueOf(dataSnapshot.exists()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireValuesListener.onCanceled();
            }
        });
    }

    public void buscar(final DataSnapshot dataSnapshot, final FireListener<List<FriendsModelView>> miembros) {


        List<FriendsModelView> miembros1 = new ArrayList<>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {
            Log.i(TAG, "--->" + data.toString());
            if (data.getKey() != null) {
                final Miembro miembro = (Miembro) parseObject(data.getValue(), Miembro.class);
                miembro.setId(Long.valueOf(data.getKey()));

                boolean isFriend = false;
                             /*   for (Amigos amigo : usuario.getAmigos()) {
                                    if (amigo.getId().equals(miembro.getId())) {
                                        isFriend = true;
                                        break;
                                    }
                                }
                                if (!isFriend)*/
                miembros1.add(new FriendsModelView(miembro.getId(), miembro.getApodo(), miembro.getFoto(), miembro.getNombre(), miembro.getApellido()));
            }
        }
        if (miembros1.size() > 0)
            miembros1.remove(miembros1.get(0));
        miembros.onDataChanged(miembros1);
    }

    public void buscarUsuario(Long pagination, final FireListener<List<FriendsModelView>> miembros) {

        DatabaseReference mDatabase = secondaryDatabase.getReference().child(ModelKeys.USER + "/");

        if (pagination == 0) {
            mDatabase.orderByKey().limitToFirst(11).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    Log.i(TAG, "--->" + dataSnapshot.toString());
                    buscar(dataSnapshot, miembros);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            mDatabase.orderByKey().startAt(String.valueOf(pagination + 1)).limitToFirst(11).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(final DataSnapshot dataSnapshot) {
                    Log.i(TAG, "--->" + dataSnapshot.toString());

                    buscar(dataSnapshot, miembros);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    String seach = "";


    public void buscarUsuario(final String busqueda, final FireListener<List<FriendsModelView>> miembros) {
        Log.i("TAG", "--->buscador 1: " + busqueda);
        seach = busqueda;

                App.get().component().wallSearchApi().searchProfile(busqueda, "1").enqueue(new NetworkCallBack<WallSearchResponse>() {
                    @Override
                    public void onRequestSuccess(WallSearchResponse response) {
                        List<FriendsModelView> miembros1 = new ArrayList<>();
                        for (WallSearchItem data : response.getData()) {
                            miembros1.add(new FriendsModelView((long) data.getId()
                                    , data.getApodo()
                                    , data.getFoto()
                                    , data.getNombre()
                                    , data.getNombre().toUpperCase()
                                    , data.getApellido()));
                        }


                        miembros.onDataChanged(miembros1);
                    }

                    @Override
                    public void onRequestFail(String errorMessage, int errorCode) {
                        miembros.onCancelled();
                    }
                });

      /*  if (runnable != null)
            handler.removeCallbacks(runnable);

        runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("TAG", "--->buscador 2: " + busqueda);
                DatabaseReference mDatabase = secondaryDatabase.getReference().child(ModelKeys.USER + "/");

                mDatabase.orderByChild("nombre_uppercase")
                        .startAt(busqueda)
                        .endAt(busqueda + "\uf8ff").limitToFirst(25)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(final DataSnapshot dataSnapshot) {

                                Log.i(TAG, "--->" + dataSnapshot.toString());
                                if (!busqueda.equals(seach)) {
                                    return;
                                }
                                new AsyncJob.AsyncJobBuilder<List<FriendsModelView>>()
                                        .doInBackground(new AsyncJob.AsyncAction<List<FriendsModelView>>() {
                                            @Override
                                            public List<FriendsModelView> doAsync() {


                                                List<FriendsModelView> miembros1 = new ArrayList<>();
                                                for (DataSnapshot data : dataSnapshot.getChildren()) {

                                                    final Miembro miembro = (Miembro) parseObject(data.getValue(), Miembro.class);
                                                    miembro.setId(Long.valueOf(data.getKey()));

                                                    boolean isFriend = false;
                                         *//*   for (Amigos amigo : usuario.getAmigos()) {
                                                if (amigo.getId().equals(miembro.getId())) {
                                                    isFriend = true;
                                                    break;
                                                }
                                            }
                                            if (!isFriend)*//*
                                                    miembros1.add(new FriendsModelView(miembro.getId()
                                                            , miembro.getApodo()
                                                            , miembro.getFoto()
                                                            , miembro.getNombre()
                                                            , miembro.getNombre_uppercase()
                                                            , miembro.getApellido()));

                                                }


                                                return miembros1;
                                            }
                                        })
                                        .doWhenFinished(new AsyncJob.AsyncResultAction<List<FriendsModelView>>() {
                                            @Override
                                            public void onResult(List<FriendsModelView> result) {
                                                Log.i("TAG", "--->buscador 3: " + result.size());
                                                if (busqueda.equals(seach)) {
                                                    Log.i(TAG, "--->doWhenFinished true");
                                                    List<FriendsModelView> filterSeach = new ArrayList<>();
                                                    for (FriendsModelView friends : result) {
                                                        if (friends.getNombre_uppercase().toUpperCase().trim().startsWith(seach.toUpperCase().trim())) {
                                                            if (!(friends.getId_amigo() + "").equals(SessionManager.getInstance().getUser().getId_usuario()))
                                                                filterSeach.add(friends);
                                                        }
                                                    }
                                                    miembros.onDataChanged(filterSeach);
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
        };
        handler.postDelayed(runnable, 1000);*/

    }


    private void addMensajesListener(final String userId, final String idConv, final FireListener<Mensajes> mensajesFireListener) {

        Query myRef = secondaryDatabase.getReference().child(ModelKeys.CONVERSATION + "/" + idConv + "/mensajes")
                .limitToLast(1);

        FirebaseManager.getInstance().valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
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
        }));
    }

    private void addUserConversationListener(final String idUser, final FireListener<List<UsuarioConversation>> fireListener) {
        final Query myRef = secondaryDatabase.getReference(ModelKeys.USER_CONVERSATION)
                .child(idUser);


        valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                valueEventListeners.add(this);
                if (dataSnapshot.getValue() == null)
                    fireListener.onDataDelete(null);


                for (DataSnapshot dataSnapsho2t : dataSnapshot.getChildren()) {
                    UsuarioConversation usuarioConversation = (UsuarioConversation) parseObject(dataSnapsho2t.getValue(), UsuarioConversation.class);
                    if (usuarioConversation != null) {
                        usuarioConversation.setId(dataSnapsho2t.getKey());
                        if (!updateUsuarioConversacion(usuarioConversation, usuarioConversations)) {
                            usuarioConversations.add(usuarioConversation);
                        }


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
        }));
    }

    private boolean updateUsuarioConversacion(UsuarioConversation usuarioConversation, List<UsuarioConversation> usuarioConversations) {


        for (UsuarioConversation gru : usuarioConversations) {
            if (gru.getId().equals(usuarioConversation.getId())) {
                gru.copy(usuarioConversation);
                return true;
            }

        }
        return false;
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
            FirebaseManager.getInstance().valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
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
            }));
        }

    }

    public void startListener(final String idUser, final FireListener<Usuario> fireListener) {
        if (idUser == null)
            return;

        addMyUserListener(idUser, false, new FireListener<Usuario>() {
            @Override
            public void onDataChanged(Usuario data) {

                if (usuario != null) {
                    usuario.copy(usuario);
                    fireListener.onDataChanged(usuario);
                    return;

                }
                usuario = data;

                new FriendControllers(secondaryDatabase).addValueFriendListener(idUser);

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
                (new GroupControllers(secondaryDatabase)).addValueGroupListener(idUser);
             /*   (new SolicitudesControllers(secondaryDatabase, true)).addValueRequestListener(idUser);
                (new SolicitudesControllers(secondaryDatabase, false)).addValueRequestListener(idUser);
*/
            }

            @Override
            public void onDataDelete(String id) {

            }

            @Override
            public void onCancelled() {

            }
        });
    }

    public void getMediaConversation(String idConversation, boolean isGrupo, final MediaController.OnGetMediaListener listener) {
        (new MediaController(secondaryDatabase)).getMediaFromConversation(idConversation, isGrupo, new MediaController.OnGetMediaListener() {
            @Override
            public void success(List<Media> urlMedia) {
                listener.success(urlMedia);
            }

            @Override
            public void failed() {
                listener.failed();
            }
        });
    }

    public void getUserGroup(String idUser, final FireListener<UsuarioGrupo> listener) {
        (new GroupControllers(secondaryDatabase)).getUserGroup(idUser, new FireListener<UsuarioGrupo>() {
            @Override
            public void onDataChanged(UsuarioGrupo data) {
                listener.onDataChanged(data);
            }

            @Override
            public void onDataDelete(String id) {
                listener.onDataDelete(id);
            }

            @Override
            public void onCancelled() {
                listener.onCancelled();
            }
        });
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


    public interface FireFriendsResultListener {


        void onComplete(Amigos amigos);

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
