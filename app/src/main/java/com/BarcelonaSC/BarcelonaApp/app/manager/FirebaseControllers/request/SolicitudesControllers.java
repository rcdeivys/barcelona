package com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.request;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.member.MemberControllers;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Count;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Solicitud;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;


import static com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager.parseObject;

/**
 * Created by Carlos on 04/02/2018.
 */

public class SolicitudesControllers {

    public static final String TAG = SolicitudesControllers.class.getSimpleName();
    private String REQUEST = "solicitudes";
    private String SEND_REQUEST = "solicitudesEnviadas";
    private Count count;

    private MemberControllers memberControllers;
    private FirebaseDatabase databaseReference;
    private boolean isSendRequest = false;

    List<Solicitud> request;


    public SolicitudesControllers(FirebaseDatabase databaseReference, boolean isSendRequest) {
        this.databaseReference = databaseReference;
        this.memberControllers = new MemberControllers(databaseReference);
        this.isSendRequest = isSendRequest;
        if (isSendRequest)
            request = FirebaseManager.getInstance().getUsuario().getSolicitudesEnviadas();
        else
            request = FirebaseManager.getInstance().getUsuario().getSolicitudesPendientes();
    }

    public SolicitudesControllers() {
    }

    public void addValueRequestListener(final String userId) {

        final Query myRef = databaseReference.getReference(isSendRequest ? SEND_REQUEST : REQUEST).child(userId);

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

        final Solicitud solicitud = (Solicitud) parseObject(dataSnapshot.getValue(), Solicitud.class);
        if (solicitud != null) {
            solicitud.setKey(dataSnapshot.getKey());

            if (solicitud.getStatus() == 1) {
                removeRequest(solicitud.getKey(), userId);
                count.verificateLimit();
                return;
            }

            if (childAdded) {

                request.add(solicitud);
                getMember(dataSnapshot.getKey(), solicitud);
            } else {

                updateSolicitudes(solicitud);
            }

        }
    }


    private void getMember(String idUser, final Solicitud solicitud) {

        memberControllers.addValueMemberListener(idUser, new MemberControllers.MemberListener() {
            @Override
            public void onMemberDataChange(Miembro member) {

                solicitud.setMiembro(member);
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
        if (isSendRequest)
            EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_SOLICITUDES_ENVIADAS));
        else
            EventBus.getDefault().post(new FirebaseEvent(FirebaseEvent.EVENT.REFRESCAR_SOLICITUDES_PENDIENTES));
    }

    private void updateSolicitudes(Solicitud solicitud) {
        if (request.isEmpty())
            return;

        for (Solicitud sol : request) {
            if (sol.getKey().equals(solicitud.getKey())) {
                sol.copy(solicitud);
                break;
            }

        }
        count.verificateLimit();
        sendEvent();
    }

    private void removeRequest(String idSolicitud, String userId) {
        if (idSolicitud.equals(userId))
            return;
        int id = 0;
        if (request.isEmpty())
            return;
        for (Solicitud sol : request) {
            if (sol.getKey().equals(idSolicitud)) {
                break;
            }
            id++;
        }
        if (id < request.size())
            request.remove(id);
        sendEvent();
    }

}
