package com.millonarios.MillonariosFC.ui.chat.friendselection.mvp;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendSelectionPresenter implements FriendSelectionContract.Presenter, FriendSelectionContract.ModelResultListener {

    private FriendSelectionContract.View view;
    private FriendSelectionModel friendSelectionModel;

    private ArrayList<Amigos> friendList = new ArrayList<Amigos>();
    private ArrayList<Amigos> friendSelectedList = new ArrayList<Amigos>();

    public FriendSelectionPresenter(FriendSelectionContract.View view, FriendSelectionModel friendSelectionModel) {
        this.view = view;
        this.friendSelectionModel = friendSelectionModel;
        init();
    }

    private void init() {

    }


    @Override
    public void onAttach(FriendSelectionContract.View view) {

    }

    @Override
    public void onDetach() {

    }

    @Override
    public void onClickFriend(Amigos friendData) {
        if (view==null) return;
        Crashlytics.log(Log.DEBUG,"AMIGO","---> RECIBIENDO "+ friendData.getId());
        boolean exists = false;
        for (Amigos friend : friendSelectedList) {
            Crashlytics.log(Log.DEBUG,"LISTA_GRUPOS"," ---> "+friend.toString());
            //TODO: Pendiente si cambia el modelo cambio la validacion
            if(friend.getId()== friendData.getId())
                exists = true;
        }
        if(!exists)
            friendSelectedList.add(friendData);
        view.updateSelectedFriends(friendSelectedList);
    }

    @Override
    public void onClickSelectedFriend(Amigos friendData) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> to erased"+friendData.getId());
        if(friendSelectedList.size()>0)
            friendSelectedList.remove(friendSelectedList.indexOf(friendData));
        else
            friendSelectedList.clear();
        view.updateSelectedFriends(friendSelectedList);
    }

    @Override
    public void loadFriends() {
        friendSelectionModel.loadFriends(1,this);
    }

    @Override
    public ArrayList<Amigos> getAllNewGroupMembers() {
        //TODO: no estamos validando si la lista es vacia
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> enviando "+friendSelectedList.size()+" miembros");
        return friendSelectedList;
    }

    @Override
    public void findByName(String name) {
        ArrayList<Amigos> auxList = new ArrayList<Amigos>();
        if(name.length()>0){
            for (Amigos friend : friendList) {
                if(friend.getConversacion().getMiembros().get(0).getApodo().toLowerCase().contains(name.toLowerCase())){
                    auxList.add(friend);
                }
            }
            view.updateFriends(auxList);
        }
    }

    @Override
    public void onGetFriendsSuccess(List<Amigos> friends) {
        if(friendList !=null) friendList.clear();
        if (view==null) return;
        for (Amigos friend : friends) {
            Crashlytics.log(Log.DEBUG,"LISTA_GRUPOS"," ---> "+friend.toString());
            friendList.add(friend);
        }
        view.updateFriends(friendList);
    }

    @Override
    public void onGetFriendsFailed() {

    }
}
