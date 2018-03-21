package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

import org.greenrobot.eventbus.EventBus;

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
    private FirebaseDatabase databaseReference;


    List<Amigos> friendsList;


    public FriendControllers(FirebaseDatabase databaseReference, boolean isSendRequest) {
        this.databaseReference = databaseReference;
        this.memberControllers = new MemberControllers(databaseReference);

        friendsList = FirebaseManager.getInstance().getUsuario().getAmigos();
    }

    public FriendControllers() {
    }

    public void addValueRequestListener(final String userId) {

        final Query myRef = databaseReference.getReference(FRIENDS).child(userId);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = new Count(dataSnapshot.getChildrenCount());

                myRef.addChildEventListener(new ChildEventListener() {
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

        final Amigos amigos = (Amigos) parseObject(dataSnapshot.getValue(), Amigos.class);
        if (amigos != null) {
            amigos.setId(Long.valueOf(dataSnapshot.getKey()));


            if (childAdded) {

                friendsList.add(amigos);
                //    getMember(dataSnapshot.getKey(), amigos);
            } else {

                updateSolicitudes(amigos);
            }

        }
    }


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

}
