package com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp;

import android.net.Uri;
import android.util.Log;

import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */

public class CreationGroupModel {

    private static final String TAG = CreationGroupModel.class.getSimpleName();

    private FirebaseManager firebaseManager;

    public CreationGroupModel(FirebaseManager firebaseManager) {
        this.firebaseManager = firebaseManager;
    }

    public void createGroup(String groupName, Uri encodedImage, List<Miembro> groupMembers, final CreationGroupContract.ModelResultListener result) {

        firebaseManager.crearGrupo(groupName, encodedImage,groupMembers, new FirebaseManager.FireGroupResultListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete(String id) {
                Log.i("GRUPOCREACION"," ---> " +id);
                Grupo devolver = null;
                for(Grupo grupo : firebaseManager.getUsuario().getGrupos()){
                    if(grupo.getKey().equals(id)){
                        devolver = grupo;
                    }
                }
                if(devolver!=null)
                    Log.i("GRUPOCREACION"," ---> " +devolver.toString());
                result.onCreationGroupSuccess(devolver);
            }
        });

    }





}
