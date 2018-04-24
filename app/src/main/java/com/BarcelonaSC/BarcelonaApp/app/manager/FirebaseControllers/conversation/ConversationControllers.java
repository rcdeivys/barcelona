package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.conversation;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend.FriendControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Mensajes;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

import java.util.List;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 04/02/2018.
 */

public class ConversationControllers {


    public static final String TAG = ConversationControllers.class.getSimpleName();
    private String CONVERSATION = "conversaciones";

    private Count count;

    private MemberControllers memberControllers;
    private ConversationControllers conversationControllers;
    private FirebaseDatabase databaseReference;

    List<Conversacion> conversacionList;


    public ConversationControllers(FirebaseDatabase databaseReference) {
        this.databaseReference = databaseReference;
        this.memberControllers = new MemberControllers(databaseReference);

    }

    public ConversationControllers() {
    }

    public void addValueConversationListener(final String idConversation, Amigos amigos) {

        final Query myRef = databaseReference.getReference(CONVERSATION).child(idConversation);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = new Count(dataSnapshot.getChildrenCount());

                myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        updateOrCreate(dataSnapshot, idConversation, true);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        updateOrCreate(dataSnapshot, idConversation, false);

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    //    removeRequest(dataSnapshot.getKey(), idConversation);
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void updateOrCreate(DataSnapshot dataSnapshot, String userId, boolean childAdded) {
        if (dataSnapshot.getKey().equals(userId))
            return;

        final Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
        if (conversacion != null) {
            conversacion.setId(dataSnapshot.getKey());

            if (childAdded) {

                conversacionList.add(conversacion);
                //    getMember(dataSnapshot.getKey(), amigos);
            } else {

              //  updateSolicitudes(conversacion);
            }

        }
    }

/*
    private void getConversation(String idUser, final Amigos amigos) {


        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                //   amigos.setConversacion(member);
                if (count.verificateLimit()) {
                    sendEvent();
                }
            }

            @Override
            public void onRemoveMember(String id) {

            }

            @Override
            public void onError() {

            }
        });
    }


    private void getMember(String idUser, final Conversacion conversacion) {


        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                conversacion.getMiembros().add(member);
                if (count.verificateLimit()) {
                    sendEvent();
                }
            }

            @Override
            public void onRemoveMember(String id) {

            }

            @Override
            public void onError() {

            }
        });
    }


    private void sendEvent() {
        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_AMIGOS));
    }

    private void updateSolicitudes(Amigos amigos) {
        if (friendsList.isEmpty())
            return;

        for (Amigos amig : friendsList) {
            if (amig.getId().equals(amigos.getId())) {
                amig.copy(amigos);
                break;
            }

        }
        count.verificateLimit();
        sendEvent();
    }

    private void removeRequest(String idAmistad, String userId) {
        if (idAmistad.equals(userId))
            return;
        int id = 0;
        if (friendsList.isEmpty())
            return;
        for (Amigos amig : friendsList) {
            if (amig.getId().equals(Long.valueOf(idAmistad))) {
                break;
            }
            id++;
        }
        if (id < friendsList.size())
            friendsList.remove(id);
        sendEvent();
    }


    private void removeMember(final String userId, final ConversationControllers memberListener) {

    }

    public interface ConversationListener {

        void onConversationDataChange(Miembro member);

        void onRemoveConversation(String id);

        void onError();

    }*/
}
