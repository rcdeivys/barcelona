package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.group;

import android.nfc.Tag;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.conversation.ConversationControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.GrupoUsuarios;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Mensajes;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioGrupo;

import org.greenrobot.eventbus.EventBus;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 06/02/2018.
 */

public class GroupControllers {


    public static final String TAG = GroupControllers.class.getSimpleName();
    String GROUP = "grupos";
    String GROUP_X_USER = "gruposUsuarios";
    String USER_X_GROUP = "usuarioGrupos";
    String USER = "usuarios";


    private Count count;

    private MemberControllers memberControllers;
    private ConversationControllers conversationControllers;
    private FirebaseDatabase databaseReference;
    private String CONVERSATION = "conversaciones";

    List<Grupo> groupsList;


    public GroupControllers(FirebaseDatabase databaseReference) {
        this.databaseReference = databaseReference;
        this.memberControllers = new MemberControllers(databaseReference);
        this.conversationControllers = new ConversationControllers(databaseReference);
        groupsList = FirebaseManager.getInstance().getUsuario().getGrupos();
    }

    public GroupControllers() {
    }


    private void addUserXGroupListener(final String userId, final FirebaseManager.FireListener<UsuarioGrupo> fireListener) {
        Query myRef = databaseReference.getReference(USER_X_GROUP).child(userId);
        FirebaseManager.getInstance().valueEventListeners.add(  myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
                    usuarioGrupo.setUserId(userId);
                    for (DataSnapshot dns : dataSnapshot.getChildren()) {
                        usuarioGrupo.setGrupo(dns.getKey());

                    }

                    if (usuarioGrupo.getGrupos().size() < groupsList.size()) {
                        List<String> aux = new ArrayList<>();
                        for (Grupo grupo : groupsList) {
                            aux.add(grupo.getKey());
                        }
                        aux.removeAll(usuarioGrupo.getGrupos());


                        List<Grupo> auxGrupo = new ArrayList<>();
                        for (String groupId : aux) {
                            for (Grupo gru : groupsList) {
                                if (groupId.equals(gru.getKey())) {
                                    auxGrupo.add(gru);
                                }
                            }

                        }
                        groupsList.removeAll(auxGrupo);
                        sendEvent();
                        return;
                    }

                    fireListener.onDataChanged(usuarioGrupo);
                } else {
                    groupsList.clear();
                    sendEvent();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }));
    }



    public void getUserGroup(final String userId, final FirebaseManager.FireListener<UsuarioGrupo> fireListener) {
        Query myRef = databaseReference.getReference(USER_X_GROUP).child(userId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void addGroupListener(final String groupKey, final FirebaseManager.FireListener<Grupo> fireListener) {
        Query myRef = databaseReference.getReference(GROUP).child(groupKey);
        FirebaseManager.getInstance().valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
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
        }));
    }


    private void addConversationListener(String idConversation, final FirebaseManager.FireListener<Conversacion> fireListener) {

        final Query myRef = databaseReference.getReference(CONVERSATION).child(idConversation);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void addGroupXUserListener(final String groupId, final Conversacion dataConversacion, final FirebaseManager.FireListener<GrupoUsuarios> fireListener) {
        final Query myRef = databaseReference.getReference(GROUP_X_USER).child(groupId);


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Count count1 = new Count(dataSnapshot.getChildrenCount());

                FirebaseManager.getInstance().valueChildremEventListeners.add( myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        final String idUser = dataSnapshot.getKey();
                        addMemberListener(idUser, true, new FirebaseManager.FireListener<Miembro>() {
                            @Override
                            public void onDataChanged(Miembro data) {

                                if (dataConversacion != null && data != null)
                                    dataConversacion.addMiembro(data);
                                if (count1.verificateLimit())
                                    sendEvent();
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

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        if (count1.verificateLimit()) ;

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        if (count1.verificateLimit()) ;
                        dataConversacion.removeMiembro(Long.valueOf(dataSnapshot.getKey()));
                        sendEvent();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                fireListener.onCancelled();
            }
        });
    }


    private void addMemberListener(final String miembro, boolean oneTime, final FirebaseManager.FireListener<Miembro> fireListener) {
        final Query myRef = databaseReference.getReference(USER).child(miembro);

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
            FirebaseManager.getInstance().valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
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
            }));
        }

    }


    public void addValueGroupListener(String userId) {


        addUserXGroupListener(userId, new FirebaseManager.FireListener<UsuarioGrupo>() {
            @Override
            public void onDataChanged(UsuarioGrupo data) {

                for (int i = 0; i < data.getGrupos().size(); i++) {
                    final String idGrupo = data.getGrupos().get(i);
                    addGroupListener(idGrupo, new FirebaseManager.FireListener<Grupo>() {
                        @Override
                        public void onDataChanged(final Grupo dataGrupo) {

                            if (updateGroup(dataGrupo))
                                return;
                            groupsList.add(dataGrupo);
                            addConversationListener(dataGrupo.getId_conversacion(), new FirebaseManager.FireListener<Conversacion>() {
                                @Override
                                public void onDataChanged(final Conversacion dataConversacion) {

                                    dataGrupo.setConversacion(dataConversacion);
                                    addGroupXUserListener(idGrupo, dataConversacion, new FirebaseManager.FireListener<GrupoUsuarios>() {
                                        @Override
                                        public void onDataChanged(GrupoUsuarios data) {

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

    private boolean updateGroup(Grupo grupo) {


        for (Grupo gru : groupsList) {
            if (gru.getKey().equals(grupo.getKey())) {
                gru.copy(grupo);
                sendEvent();
                return true;
            }

        }
        return false;
    }


    private void sendEvent() {
        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_GRUPOS));
    }


}
