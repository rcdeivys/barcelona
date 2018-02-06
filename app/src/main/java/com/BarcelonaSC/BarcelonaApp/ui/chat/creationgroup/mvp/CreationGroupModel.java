package com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.mvp;

import android.net.Uri;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

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
