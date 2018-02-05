package com.millonarios.MillonariosFC.ui.chat.friends.mvp;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class FriendsPresenter implements FriendsContract.Presenter, FriendsContract.ModelResultListener.OnGetFriendsListener, FriendsContract.ModelResultListener.OnAddAGroupListener {


    private static final String TAG = FriendsPresenter.class.getSimpleName();
    private FriendsContract.View view;
    private FriendsModel friendsModel;
    private List<Amigos> amigos;
    private ArrayList<FriendsModelView> friendList = new ArrayList<FriendsModelView>();

    public FriendsPresenter(FriendsContract.View view, FriendsModel friendsModel) {
        this.view = view;
        this.friendsModel = friendsModel;
    }

    @Override
    public void onAttach(FriendsContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    @Override
    public void addSelectedFriendAGroup(String id_group, String id_friend) {
        //TODO: we call the model to add a friend to a selected group
        friendsModel.addASelectedFriendToAGroup(id_group, id_friend, this);
    }

    @Override
    public void onClickFriend(FriendsModelView friendData) {

    }

    @Override
    public void loadFriends() {
        friendsModel.loadFriends("2", this);
    }

    @Override
    public void findByName(String name) {
        ArrayList<FriendsModelView> auxList = new ArrayList<FriendsModelView>();
        if (name.length() >= 0) {
            for (FriendsModelView friend : friendList) {
                if (friend.getApodo().toLowerCase().contains(name.toLowerCase())) {
                    auxList.add(friend);
                }
            }
            view.updateFriends(auxList);
        }
    }

    @Override
    public void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos) {
        if (friendList != null) friendList.clear();
        if (view == null) return;
        this.amigos = amigos;
        for (FriendsModelView friend : friends) {
            Crashlytics.log(Log.DEBUG, "LISTA AMIGOS onGetFriendsSuccess()", " ---> " + friend.getApodo());
            friendList.add(friend);
        }

        orderByStatus(friendList);
        view.updateFriends(friendList);
    }

    private void orderByStatus(ArrayList<FriendsModelView> friendList) {
        Collections.sort(friendList, new Comparator<FriendsModelView>() {
            @Override
            public int compare(FriendsModelView o1, FriendsModelView o2) {
                return o2.getIsonline().getValue() - o1.getIsonline().getValue(); // Descending
            }
        });
    }

    @Override
    public void onGetFriendsFailed() {

    }


    @Override
    public void onAddSuccess() {
        view.addFriendSuccess();
    }

    @Override
    public void onAddFailed() {
        view.addFriendFailed();
    }

    public Amigos findAmistad(Long idAmistad) {
        for (Amigos amigo : amigos) {
            if (amigo.getId().equals(idAmistad))
                return amigo;
        }
        return new Amigos();

    }

    public void removeFriends(FriendsModelView friendsModelView) {
        friendsModel.removeFriend(findAmistad(friendsModelView.getId_amigo()));
    }
}
