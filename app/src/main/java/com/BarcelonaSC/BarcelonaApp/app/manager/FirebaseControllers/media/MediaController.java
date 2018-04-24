package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.media;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.conversation.ConversationControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 05/04/2018.
 */

public class MediaController {

    private FirebaseDatabase databaseReference;
    private String USUARIOS_MEDIA = "usuariosMedia";

    public MediaController(FirebaseDatabase databaseReference) {
        this.databaseReference = databaseReference;
    }


    public void getMediaFromConversation(final String idConversation, boolean isGrupo, final OnGetMediaListener onGetMediaListener) {
        Query myRef;
        if (isGrupo) {
            myRef = databaseReference.getReference(USUARIOS_MEDIA)
                    .child(idConversation);
        } else {
            myRef = databaseReference.getReference(USUARIOS_MEDIA)
                    .child(idConversation)
                    .child(SessionManager.getInstance().getUser().getId_usuario());
        }
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {
                    List<Media> mediaList = new ArrayList<>();
                    for (DataSnapshot dns : dataSnapshot.getChildren()) {
                        Media media = (Media) parseObject(dns.getValue(), Media.class);
                        mediaList.add(media);

                    }

                    onGetMediaListener.success(mediaList);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onGetMediaListener.failed();
            }
        });
    }

    public interface OnGetMediaListener {

        void success(List<Media> urlMedia);

        void failed();
    }
}
