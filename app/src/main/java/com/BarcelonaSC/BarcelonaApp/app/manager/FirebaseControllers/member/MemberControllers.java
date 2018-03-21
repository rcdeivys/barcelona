package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

import java.util.ArrayList;
import java.util.List;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 04/02/2018.
 */

public class MemberControllers {

    public static final String TAG = MemberControllers.class.getSimpleName();
    private String USER = "usuarios";
    private FirebaseDatabase databaseReference;

    public MemberControllers(FirebaseDatabase databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void addValueMemberListener(final String idUser, final MemberListener memberListener) {

        final Query myRef = databaseReference.getReference(USER).child(idUser);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    removeMember(idUser, memberListener);
                    myRef.removeEventListener(this);
                    return;
                }
                addMember(dataSnapshot, memberListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                memberListener.onError();
            }
        });

    }




    private void addMember(final DataSnapshot dataSnapshot, final MemberListener memberListener) {
        final Miembro miembro = (Miembro) parseObject(dataSnapshot.getValue(), Miembro.class);
        miembro.setId(Long.valueOf(dataSnapshot.getKey()));
        memberListener.onMemberDataChange(miembro);

    }

    public List<Miembro> getAllMember() {
        return new ArrayList<>();
    }

    private void removeMember(final String userId, final MemberListener memberListener) {

    }

    public interface MemberListener {

        void onMemberDataChange(Miembro member);

        void onRemoveMember(String id);

        void onError();

    }
}
