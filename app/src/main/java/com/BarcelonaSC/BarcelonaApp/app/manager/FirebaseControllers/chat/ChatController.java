package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.chat;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.conversation.ConversationControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend.FriendControllers;
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
import com.BarcelonaSC.BarcelonaApp.models.firebase.UsuarioGrupo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 12/04/2018.
 */

public class ChatController {

    public static final String TAG = FriendControllers.class.getSimpleName();
    private String FRIENDS = "amigos";
    String GROUP = "grupos";
    String GROUP_X_USER = "gruposUsuarios";
    private Count count;

    private MemberControllers memberControllers;
    private ConversationControllers conversationControllers;
    private FirebaseDatabase databaseReference;
    private String CONVERSATION = "conversaciones";

    public ChatController() {
        this.databaseReference = FirebaseDatabase.getInstance();
        this.memberControllers = new MemberControllers(databaseReference);
        this.conversationControllers = new ConversationControllers(databaseReference);
    }


    public void getFriendById(final String friendId, final ChatFriendListener chatFriendListener) {

        final String myId = SessionManager.getInstance().getUser().getId_usuario();

        final Query myRef = databaseReference.getReference(FRIENDS)
                .child(myId + "/" + friendId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Amigos amigos = (Amigos) parseObject(dataSnapshot.getValue(), Amigos.class);
                if (amigos != null) {
                    amigos.setId(Long.valueOf(dataSnapshot.getKey()));
                    getConversation(amigos, chatFriendListener);

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatFriendListener.onGetFriendError();
            }
        });


    }

    private void getConversation(final Amigos amigos, final ChatFriendListener chatFriendListener) {

        final Query myRef = databaseReference.getReference(CONVERSATION).child(amigos.getId_conversacion());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
                if (conversacion != null) {

                    amigos.setConversacion(conversacion);
                    conversacion.setId(dataSnapshot.getKey());
                    getMember(String.valueOf(amigos.getId()), amigos, chatFriendListener);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatFriendListener.onGetFriendError();
            }
        });
    }

    private void getMember(String idUser, final Amigos amigos, final ChatFriendListener chatFriendListener) {

        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                amigos.getConversacion().addMiembro(member);
                Log.i(TAG, "---> PRUEBA: " + amigos);
                chatFriendListener.onGetFriendReady(amigos);
            }


            @Override
            public void onError() {
                chatFriendListener.onGetFriendError();
            }
        });
    }


    public interface ChatFriendListener {

        void onGetFriendReady(Amigos amigo);

        void onGetFriendError();
    }

    public interface ChatGroupListener {

        void onGetGroupReady(Grupo grupo);

        void onGetGroupError();
    }


    ///////////////////////////////


    public void getGroupById(String idGrupo, final ChatGroupListener chatGroupListener) {

        Query myRef = databaseReference.getReference(GROUP).child(idGrupo);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Grupo grupo = (Grupo) parseObject(dataSnapshot.getValue(), Grupo.class);
                if (grupo != null) {
                    grupo.setKey(dataSnapshot.getKey());
                    addConversationListener(grupo, chatGroupListener);
                } else {
                    chatGroupListener.onGetGroupError();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatGroupListener.onGetGroupError();
            }
        });
    }

    private void addConversationListener(final Grupo grupo, final ChatGroupListener chatGroupListener) {

        final Query myRef = databaseReference.getReference(CONVERSATION).child(grupo.getId_conversacion());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
                if (conversacion != null) {

                    conversacion.setId(dataSnapshot.getKey());
                    grupo.setConversacion(conversacion);
                    addGroupXUserListener(grupo, chatGroupListener);

                } else {
                    chatGroupListener.onGetGroupError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatGroupListener.onGetGroupError();
            }
        });
    }


    private void addGroupXUserListener(final Grupo grupo, final ChatGroupListener chatGroupListener) {
        final Query myRef = databaseReference.getReference(GROUP_X_USER).child(grupo.getKey());


        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final Count count1 = new Count(dataSnapshot.getChildrenCount());

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    getMember(data.getKey(), grupo, count1, chatGroupListener);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                chatGroupListener.onGetGroupError();
            }
        });
    }


    private void getMember(String idUser, final Grupo grupo, final Count count, final ChatGroupListener chatGroupListener) {

        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                grupo.getConversacion().addMiembro(member);
                Log.i(TAG, "---> PRUEBA: " + grupo);
                if (count.verificateLimit())
                    chatGroupListener.onGetGroupReady(grupo);
            }


            @Override
            public void onError() {
                chatGroupListener.onGetGroupError();
            }
        });
    }


}
