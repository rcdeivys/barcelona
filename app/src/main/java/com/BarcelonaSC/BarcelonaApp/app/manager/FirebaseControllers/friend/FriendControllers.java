package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend;

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
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 04/02/2018.
 */

public class FriendControllers {


    public static final String TAG = FriendControllers.class.getSimpleName();
    private String FRIENDS = "amigos";

    private Count count;

    private MemberControllers memberControllers;
    private ConversationControllers conversationControllers;
    private FirebaseDatabase databaseReference;
    private String CONVERSATION = "conversaciones";

    List<Amigos> friendsList;


    public FriendControllers(FirebaseDatabase databaseReference) {
        this.databaseReference = databaseReference;
        this.memberControllers = new MemberControllers(databaseReference);
        this.conversationControllers = new ConversationControllers(databaseReference);
        friendsList = FirebaseManager.getInstance().getUsuario().getAmigos();
    }

    public FriendControllers() {
    }

    public void addValueFriendListener(final String userId) {

        final Query myRef = databaseReference.getReference(FRIENDS).child(userId);

        FirebaseManager.getInstance().valueEventListeners.add(myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = new Count(dataSnapshot.getChildrenCount());

                FirebaseManager.getInstance().valueChildremEventListeners.add(myRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        updateOrCreate(dataSnapshot, userId, true);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        updateOrCreate(dataSnapshot, userId, false);

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        removeRequest(dataSnapshot.getKey(), userId);
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

            }
        }));


    }


    public void getBlockUsers(final String userId, final BlockMemberListener blockMemberListener) {

        final Query myRef = databaseReference.getReference(FRIENDS).child(userId).orderByChild("bloqueado").equalTo(true);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = new Count(dataSnapshot.getChildrenCount());
                final List<Miembro> miembros = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    memberControllers.addValueMemberListener(data.getKey(), new MemberControllers.MemberListener() {
                        @Override
                        public void onMemberDataChange(Miembro member) {
                            miembros.add(member);
                            if (count.verificateLimit())
                                blockMemberListener.onBlockMemberDataChange(miembros);

                        }

                        @Override
                        public void onError() {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public interface BlockMemberListener {

        void onBlockMemberDataChange(List<Miembro> member);

        void onError();

    }

    private void updateOrCreate(DataSnapshot dataSnapshot, String userId, boolean childAdded) {
        if (dataSnapshot.getKey().equals(userId))
            return;

        final Amigos amigos = (Amigos) parseObject(dataSnapshot.getValue(), Amigos.class);
        if (amigos != null) {
            amigos.setId(Long.valueOf(dataSnapshot.getKey()));


            if (!updateAmistad(amigos)) {
                friendsList.add(amigos);
                getConversation(amigos.getId_conversacion(), amigos);
            }

        }

    }

    private void getConversation(String idConversation, final Amigos amigos) {

        final Query myRef = databaseReference.getReference(CONVERSATION).child(idConversation);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    myRef.removeEventListener(this);
                    return;
                }
                Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
                if (conversacion != null) {

                    amigos.setConversacion(conversacion);
                    conversacion.setId(dataSnapshot.getKey());
                    getMember(String.valueOf(amigos.getId()), conversacion);
                } else {
                    count.verificateLimit();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void getMember(String idUser, final Conversacion conversacion) {

        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                conversacion.addMiembro(member);
                Log.i(TAG, "---> PRUEBA: " + friendsList);
                if (count.verificateLimit()) {
                    sendEvent();
                }
            }


            @Override
            public void onError() {

            }
        });
    }


    private void sendEvent() {
        EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_AMIGOS));
    }

    private boolean updateAmistad(Amigos amigos) {
        if (friendsList.isEmpty())
            return false;

        for (Amigos amig : friendsList) {
            if (amig.getId().equals(amigos.getId())) {
                amig.copy(amigos);
                count.verificateLimit();
                sendEvent();
                return true;
            }

        }
        return false;


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

}
