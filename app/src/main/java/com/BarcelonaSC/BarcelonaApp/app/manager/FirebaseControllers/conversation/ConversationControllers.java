package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.conversation;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
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
    private String CONVERSATION = "amigos";

    private Count count;

    private MemberControllers memberControllers;
    private ConversationControllers conversationControllers;
    private FirebaseDatabase databaseReference;

    private void addConversationListener(String idConversation, final  ConversationListener conversationListener) {


        final Query myRef = databaseReference.getReference(CONVERSATION).child(idConversation);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "*/*/---->onChildAdded" + dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.i(TAG, "*/*/---->onChildAdded" + dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.i(TAG, "*/*/---->onChildAdded" + dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Conversacion conversacion = (Conversacion) parseObject(dataSnapshot.getValue(), Conversacion.class);
                if (conversacion != null) {
                    List<Mensajes> mensajes = conversacion.getMensajes();
                    conversacion.setId(dataSnapshot.getKey());

                } else {
                    //myRef.removeEventListener(this);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }


    public interface ConversationListener {

        void onConverDataChange(Miembro member);

        void onRemoveConver(String id);

        void onError();

    }
}
