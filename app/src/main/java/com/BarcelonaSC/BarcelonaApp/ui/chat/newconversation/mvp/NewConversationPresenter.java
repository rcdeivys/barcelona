package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Cesar on 17/01/2018.
 */

public class NewConversationPresenter extends NewConversationContract.ModelResultListener implements NewConversationContract.Presenter, NewConversationContract.ModelResultListener.OnGetFriendsListener, NewConversationContract.ModelResultListener.OnAddAGroupListener {


    private static final String TAG = NewConversationPresenter.class.getSimpleName();
    private NewConversationContract.View view;
    private NewConversationModel newConversationModel;

    private ArrayList<FriendsModelView> friendList = new ArrayList<FriendsModelView>();

    public NewConversationPresenter(NewConversationContract.View view, NewConversationModel newConversationModel) {
        this.view = view;
        this.newConversationModel = newConversationModel;
    }

    @Override
    public void onAttach(NewConversationContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        view = null;
    }


    @Override
    public void addSelectedFriendAGroup(String id_group, Long id_friend) {
        //TODO: we call the model to add a friend to a selected group
        newConversationModel.addASelectedFriendToAGroup(id_group, id_friend, this);
    }

    @Override
    public void onClickFriend(FriendsModelView friendData) {

    }

    @Override
    public void loadFriends(int current_page) {

        if (friendList.size() == 0) {
            friendList = new ArrayList<>();
            newConversationModel.loadFriends((long) 0, this);
        } else
            newConversationModel.loadFriends(friendList.get(friendList.size() - 1).getId_amigo(), this);
    }

    private boolean suiche = false;
    String seach = "";

    public boolean isViewNull() {
        return view == null;
    }


    @Override
    public void findByName(final String name) {
        seach = name;

        if (!seach.isEmpty()) {
            if (!suiche) {
                view.showProgress();
                suiche = true;
            }
            FirebaseManager.getInstance().buscarUsuario(seach.toUpperCase(), new FirebaseManager.FireListener<List<FriendsModelView>>() {
                @Override
                public void onDataChanged(final List<FriendsModelView> data) {
                    Log.i(TAG, "--->onDataChanged PASO POR AQUI");
                    if (isViewNull()) return;
                    Log.i(TAG, "--->onDataChanged PASO POR AQUI TAMBIEN : size:" + data.size());

                    if (suiche) {
                        if (data.size() == 0)
                            view.showNoResultText(true);
                        else
                            view.showNoResultText(false);

                        view.updateFriends(data);

                    }
                }

                @Override
                public void onDataDelete(String id) {

                }

                @Override
                public void onCancelled() {

                }
            });


        } else {
            suiche = false;
            loadFriends(0);
        }
    }


    public void onClickAcceptUser(String id, FriendsModelView friendsModelView) {
        Log.i(TAG, "111--->14124 ");
        Amigos amigos = findAmistad(friendsModelView.getId_amigo());
        if (amigos == null)
            newConversationModel.acceptUser(id, friendsModelView, this);
        else {
            if (isViewNull())
                return;
            view.onCompleteFriends(amigos);
        }
    }


    @Override
    public void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos) {

        if (isViewNull())
            return;


        for (FriendsModelView friend : friends) {
            Crashlytics.log(Log.DEBUG, "LISTA AMIGOS onGetSearchSuccess()", " ---> " + friend.getApodo());
            friendList.add(friend);
        }

        //  orderByStatus(friendList);
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
        if (view == null) return;
        view.showToastError("Error inesperado");
    }

    @Override
    public void onAcceptSuccess(Amigos amigos) {
        if (view == null) return;
        view.onCompleteFriends(amigos);
    }

    @Override
    public void onAcceptFailed() {

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

        List<Amigos> amigos = FirebaseManager.getInstance().getUsuario().getAmigos();
        for (Amigos amigo : amigos) {
            if (amigo.getId().equals(idAmistad))
                return amigo;
        }
        return null;

    }

    public void removeFriends(FriendsModelView friendsModelView) {
        newConversationModel.removeFriend(findAmistad(friendsModelView.getId_amigo()));
    }
}
