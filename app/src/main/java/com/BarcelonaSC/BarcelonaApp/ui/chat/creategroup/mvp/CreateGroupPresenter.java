package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp;

import android.net.Uri;
import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class CreateGroupPresenter implements CreateGroupContract.Presenter, CreateGroupContract.ModelResultListener {

    private CreateGroupContract.View view;
    private CreateGroupModel createGroupModel;

    private ArrayList<FriendsModelView> friendList = new ArrayList<>();
    private ArrayList<FriendsModelView> friendSelectedList = new ArrayList<>();
    private ArrayList<FriendsModelView> newMembers = new ArrayList<>();

    public CreateGroupPresenter(CreateGroupContract.View view, CreateGroupModel createGroupModel) {
        this.view = view;
        this.createGroupModel = createGroupModel;
        init();
    }

    private void init() {

    }


    @Override
    public void onAttach(CreateGroupContract.View view) {
        this.view = view;
    }

    @Override
    public void onDetach() {
        this.view = null;
    }

    @Override
    public void onClickFriend(FriendsModelView friendData) {
        if (view == null) return;
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> RECIBIENDO " + friendData.getId());
        boolean exists = false;
        for (FriendsModelView friend : friendSelectedList) {
            Crashlytics.log(Log.DEBUG, "LISTA_GRUPOS", " ---> " + friend.toString());
            //TODO: Pendiente si cambia el modelo cambio la validacion
            if (friend.getId_amigo().equals(friendData.getId_amigo()))
                exists = true;
        }
        if (!exists)
            friendSelectedList.add(friendData);
        view.updateSelectedFriends(friendSelectedList);
    }

    @Override
    public void onClickSelectedFriend(FriendsModelView friendData) {
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> to erased" + friendData.getId());
        if(view==null) return;

        if (friendSelectedList.size() > 0)
            friendSelectedList.remove(friendSelectedList.indexOf(friendData));
        else
            friendSelectedList.clear();
        view.updateSelectedFriends(friendSelectedList);
    }

    @Override
    public void onClickSelectedFriendToInvitedGroup(final FriendsModelView friendData, String id_grupo) {
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> to erased" + friendData.getId());
        if(view==null) return;

        FirebaseManager.getInstance().buscarUsuarioEnGrupo(friendData.getId_amigo(), id_grupo, new FirebaseManager.FireValuesListener() {
            @Override
            public void onComplete(String value) {
                Log.i("onComplete","--->value: "+value);
                if(value.equals("false")){
                    boolean exists = false;
                    for (FriendsModelView friend : friendSelectedList) {
                        Crashlytics.log(Log.DEBUG, "LISTA_GRUPOS", " ---> " + friend.toString());
                        if (friend.getId_amigo().equals(friendData.getId_amigo()))
                            exists = true;
                    }
                    if (!exists)
                        friendSelectedList.add(friendData);
                    view.updateSelectedFriends(friendSelectedList);
                }else{
                    view.showMessage("Este usuario ya pertecene al grupo");
                }
            }

            @Override
            public void onCanceled() {

            }
        });

    }

    @Override
    public void loadFriends(int i) {

        if (isViewNull()) return;
        view.showFindFriend(true);


      /*  view.showProgress();
        if (friendList.size() == 0) {
            friendList = new ArrayList<>();
            createGroupModel.loadFriends((long) 0, this);
        } else
            createGroupModel.loadFriends(friendList.get(friendList.size() - 1).getId_amigo(), this);*/

    }

    @Override
    public ArrayList<FriendsModelView> getAllNewGroupMembers() {
        //TODO: no estamos validando si la lista es vacia
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> enviando " + friendSelectedList.size() + " miembros");
        return friendSelectedList;
    }

    private boolean suiche = false;
    String seach = "";

    public boolean isViewNull() {
        return view == null;
    }


    @Override
    public void findByName(final String name) {
        if(isViewNull()) return;
        seach = name;

        if (!seach.isEmpty()) {

            if (!suiche) {
                view.showFindFriend(false);
                view.showProgress();
                suiche = true;
            }
            FirebaseManager.getInstance().buscarUsuario(seach.toUpperCase(), new FirebaseManager.FireListener<List<FriendsModelView>>() {
                @Override
                public void onDataChanged(final List<FriendsModelView> data) {

                    if (isViewNull()) return;


                    if (suiche) {
                        if (data.size() == 0)
                            view.showNoResultText(true);
                        else
                            view.showNoResultText(false);

                        view.updateFriends(data);

                    }
                    view.hideProgress();
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


    @Override
    public void initGroupMembers(List<FriendsModelView> groupMembers) {
        if (groupMembers.size() > 0)
            this.newMembers.addAll(groupMembers);
    }

    @Override
    public void onGetFriendsSuccess(List<FriendsModelView> friends, List<Amigos> amigos) {
        if (view == null) return;


        for (FriendsModelView friend : friends) {
            Crashlytics.log(Log.DEBUG, "LISTA AMIGOS onGetFriendsSuccess()", " ---> " + friend.getApodo());
            friendList.add(friend);
        }
        view.hideProgress();
        //  orderByStatus(friendList);
        view.updateFriends(friendList);
    }


    @Override
    public void onGetFriendsFailed() {

    }

    @Override
    public void onCreateGroupSuccess(Grupo group) {
        if (view == null) return;
        view.hideProgress();
        view.lunchChatActivity(group);
    }

    @Override
    public void onCreateGroupFailed() {

    }

    @Override
    public void createGroupChat(String groupName, Uri encodedImage) {
        if (view == null) return;

        view.showProgress();
        createGroupModel.createGroup(groupName, encodedImage, getHowMiembroList(), this);
    }

    private List<Miembro> getHowMiembroList() {
        List<Miembro> miembros = new ArrayList<Miembro>();
        for (FriendsModelView nuevo : newMembers) {
            miembros.add(nuevo.getMemberFromFriend());
        }
        return miembros;
    }
}
