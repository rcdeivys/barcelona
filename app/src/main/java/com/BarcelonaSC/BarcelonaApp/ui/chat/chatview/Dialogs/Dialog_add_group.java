package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.adapter.FriendSelectedAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.adapter.FriendSelectionAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di.CreateGroupModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di.DaggerCreateGroupComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter.GroupsActivityAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsModel;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by cesar on 30/3/2018.
 */

public class Dialog_add_group extends DialogFragment implements CreateGroupContract.View, FriendSelectionAdapter.OnItemClickListener, FriendSelectedAdapter.OnItemClickListener, GroupsContract.View, GroupsActivityAdapter.OnItemClickListener {

    @BindView(R.id.friend_selection_recycler_view)
    RecyclerView friend_selection_recycler_view;

    @BindView(R.id.friend_selected_recycler_view)
    RecyclerView friend_selected_recycler_view;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.et_msg_search)
    EditText et_msg_search;

    @BindView(R.id.friend_selection_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.iv_go_creation)
    Button iv_go_creation;

    @Inject
    public CreateGroupPresenter presenter;

    public GroupsPresenter groupsPresenter;
    @BindView(R.id.iv_closet)
    ImageView ivCloset;
    Unbinder unbinder;

    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGLayoutManager;
    private static final String TAG = Dialog_add_group.class.getSimpleName();

    GroupsActivityAdapter groupsActivityAdapter;
    FriendSelectedAdapter friendSelectedAdapter;
    private FriendSelectionAdapter friendSelectionAdapter;

    public static final String TAG_FRIENDS = "SELECT_FRIENDS";
    public static final String TAG_GROUP = "SELECT_GROUP";
    public static final String TAG_BLOCK = "SELECT_BLOCK";
    private String TAG_KEY = "";
    private OnItemClickListenerDialog onItemClickListener;

    List<FriendsModelView> friends = new ArrayList<>();
    GroupsActivityAdapter groupsAdapter;
    private Amigos amigo;
    private Grupo grupo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initComponentFriendsSelection() {
        DaggerCreateGroupComponent.builder()
                .appComponent(App.get().component())
                .createGroupModule(new CreateGroupModule(this))
                .build().inject(Dialog_add_group.this);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_add_member);
        dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialogo);
        Log.i(TAG, "--->onCreateDialog");
        initComponentFriendsSelection();

        if (TAG_KEY.equals(TAG_FRIENDS)) {
            initRecyclerViewSelected();
            presenter.onAttach(this);
            presenter.loadFriends(0);
        } else if (TAG_KEY.equals(TAG_GROUP)) {
            iv_go_creation.setVisibility(View.GONE);
            groupsPresenter = new GroupsPresenter(this, new GroupsModel());
            initRecyclerViewGroups();
            groupsPresenter.onAttach(this);
            groupsPresenter.loadGroups();
        } else {
            FirebaseManager.getInstance().getBlockUsers(new com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.friend.FriendControllers.BlockMemberListener() {
                @Override
                public void onBlockMemberDataChange(List<Miembro> member) {
                    initRecyclerViewSelected();
                    presenter.onAttach(Dialog_add_group.this);

                }

                @Override
                public void onError() {

                }
            });
        }

        ivCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return dialogo;
    }

    private void initRecyclerViewSelected() {
        mGLayoutManager = new GridLayoutManager(getContext(), 4);
        friend_selected_recycler_view.setLayoutManager(mGLayoutManager);
        List<FriendsModelView> itemList = new ArrayList<>();
        friendSelectedAdapter = new FriendSelectedAdapter(itemList, getContext());
        friendSelectedAdapter.setOnItemClickListener(this);
        friend_selected_recycler_view.setAdapter(friendSelectedAdapter);
        initRecyclerViewFriends();
    }

    public void initRecyclerViewFriends() {
        mLayoutManager = new LinearLayoutManager(getContext());
        friend_selection_recycler_view.setLayoutManager(mLayoutManager);
        friends = new ArrayList<>();
        friendSelectionAdapter = new FriendSelectionAdapter(friends, getContext());
        friendSelectionAdapter.setOnItemClickListener(this);
        friend_selection_recycler_view.setAdapter(friendSelectionAdapter);
        friend_selection_recycler_view.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                et_msg_search.setText("");
                initRecyclerViewFriends();
                refresh();

            }
        });
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        if (TAG_KEY.equals(TAG_FRIENDS)) {
            presenter.findByName(et_msg_search.getText().toString());
        } else {
            groupsPresenter.findByName(et_msg_search.getText().toString());
        }

    }

    public void initRecyclerViewGroups() {
        mLayoutManager = new LinearLayoutManager(getContext());
        friend_selection_recycler_view.setLayoutManager(mLayoutManager);
        List<GroupModelView> selectedList = new ArrayList<>();
        groupsAdapter = new GroupsActivityAdapter(selectedList, getContext());
        groupsAdapter.setOnItemClickListener(this);
        friend_selection_recycler_view.setAdapter(groupsAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @OnClick(R.id.iv_go_creation)
    public void addGroup() {
        showProgress();
        List<FriendsModelView> listFriendsInvite = presenter.getAllNewGroupMembers();
        for (int i = 0; i < listFriendsInvite.size(); i++) {
            FirebaseManager.getInstance().invitarUsuarioGrupo(listFriendsInvite.get(i).getLongId(), grupo.getKey(), new FirebaseManager.FireResultListener() {
                @Override
                public void onComplete() {

                }

                @Override
                public void onError() {

                }
            });
        }
        hideProgress();
        showMessage("Participantes agregados");
        this.dismiss();
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (et_msg_search.getText().toString().equals("")) {
                    presenter.loadFriends(current_page);
                }
            }
        };
    }

    public static ArrayList<GroupModelView> getAsGroupModelArrayLis(List<Grupo> grupos) {
        ArrayList<GroupModelView> newGrupo = new ArrayList<GroupModelView>();
        for (Grupo grupo : grupos) {
            ArrayList<FriendsModelView> FriendsModelView = new ArrayList<>();
            for (Miembro miembro : grupo.getConversacion().getMiembros()) {
                FriendsModelView.add(new FriendsModelView(miembro.getId(),
                        miembro.getApodo(), miembro.getFoto(), miembro.getStatusChat(),
                        false, grupo.getId_conversacion(), miembro.getNombre(), miembro.getCreated_at()));
            }
            newGrupo.add(new GroupModelView(grupo.getKey(), grupo.getNombre(), grupo.getFoto(), FriendsModelView));
        }
        return newGrupo;
    }

    public void setParams(String tagFriends, OnItemClickListenerDialog onItemClickListener) {
        switch (tagFriends) {
            case TAG_FRIENDS: {
                TAG_KEY = tagFriends;
                break;
            }
            case TAG_GROUP: {
                TAG_KEY = tagFriends;
                break;
            }
            case TAG_BLOCK: {
                TAG_KEY = tagFriends;
                break;
            }
        }
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClickFriend(FriendsModelView friend) {
        presenter.onClickSelectedFriendToInvitedGroup(friend, grupo.getKey());
    }

    @Override
    public void onClickSelectedFriend(FriendsModelView friend) {
        presenter.onClickSelectedFriend(friend);
    }

    @Override
    public void showNoResultText(boolean show) {

    }

    @Override
    public void showProgress() {
        if (progressbar != null)
            progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (progressbar != null)
            progressbar.setVisibility(View.GONE);
    }

    @Override
    public void updateFriends(List<FriendsModelView> friends) {
        hideProgress();
        if (friendSelectionAdapter != null) {
            if (friends.size() == 0) {
                Toast.makeText(getContext(), getText(R.string.group_search), Toast.LENGTH_SHORT).show();
            }

            friendSelectionAdapter.updateAll(friends);
        }
    }

    @Override
    public void updateSelectedFriends(List<FriendsModelView> friends) {
        Crashlytics.log(Log.DEBUG, TAG, "---> updateSelectedFriends " + friends.size());
        if (friendSelectedAdapter != null) {
            if (friends.size() > 0) {

            } else {

            }
            Crashlytics.log(Log.DEBUG, TAG, "---> friendSelectedAdapter no null " + friends.size());
            friendSelectedAdapter.updateAll(friends);
        }
    }

    @Override
    public void updateGroups(List<GroupModelView> groups) {
        if (groupsAdapter != null) {
            groupsAdapter.updateAll(groups);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void refresh() {
        presenter.loadFriends(0);
    }

    @Override
    public void goToSelectedGroup(String id_group) {
        hideProgress();
        if (id_group != null) {
            Toast.makeText(getContext(), getText(R.string.group_added_member), Toast.LENGTH_SHORT).show();
            Intent intent = ChatActivity.intent(id_group, getContext());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void showMessage(String message) {
        hideProgress();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lunchChatActivity(Grupo newGroup) {

    }

    @Override
    public void onClickItem(GroupModelView group) {
        showProgress();
        groupsPresenter.addSelectedFriendToAGroup(amigo.getId(), group.getIdGroup());
    }

    public void setamigo(Amigos amigo) {
        this.amigo = amigo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public interface OnItemClickListenerDialog {
        void onClickDialogAddGroup(List<Amigos> friends, List<Grupo> grupos, String TAG);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (groupsPresenter != null)
            groupsPresenter.onDetach();
        presenter.onDetach();
    }
}
