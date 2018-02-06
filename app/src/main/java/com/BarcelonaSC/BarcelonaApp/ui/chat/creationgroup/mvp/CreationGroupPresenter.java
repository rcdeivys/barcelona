package com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.mvp;

import android.net.Uri;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */

public class CreationGroupPresenter implements CreationGroupContract.Presenter,CreationGroupContract.ModelResultListener{

    private CreationGroupContract.View view;
    private CreationGroupModel creationGroupModel;

    private ArrayList<Amigos> newMembers = new ArrayList<Amigos>();

    public CreationGroupPresenter(CreationGroupContract.View view, CreationGroupModel creationGroupModel) {
        this.view = view;
        this.creationGroupModel = creationGroupModel;
        init();
    }

    private void init() {

    }


    @Override
    public void onAttach(CreationGroupContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void createGroupChat(String groupName,Uri encodedImage) {
        creationGroupModel.createGroup(groupName, encodedImage, getHowMiembroList(), this);
    }

    @Override
    public void onFutureMemberPress(Amigos friendData) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> to erased"+friendData.getId());
        if(newMembers.size()>0)
            newMembers.remove(newMembers.indexOf(friendData));
        else
            newMembers.clear();
        view.updateSelectedMembers(newMembers);
    }

    @Override
    public void initGroupMembers(List<Amigos> groupMembers) {
        if(groupMembers.size()>0)
            this.newMembers.addAll(groupMembers);
    }

    @Override
    public void onCreationGroupSuccess(Grupo group) {
        view.lunchChatActivity(group);
    }

    @Override
    public void onCreationGroupFailed() {

    }

    private List<Miembro> getHowMiembroList(){
        List<Miembro> miembros = new ArrayList<Miembro>();
        for(Amigos nuevo : newMembers){
            miembros.add(nuevo.getConversacion().getMiembros().get(0));
        }
        return miembros;
    }
}
