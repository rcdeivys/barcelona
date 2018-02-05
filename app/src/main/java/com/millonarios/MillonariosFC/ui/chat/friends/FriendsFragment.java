package com.millonarios.MillonariosFC.ui.chat.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.commons.BaseEventBusFragment;
import com.millonarios.MillonariosFC.commons.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

import android.support.v4.app.DialogFragment;
import android.widget.EditText;
import android.widget.ImageView;

import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.firebase.FirebaseEvent;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.Dialogs.FriendsPopup;
import com.millonarios.MillonariosFC.ui.chat.friends.adapters.FriendsAdapter;
import com.millonarios.MillonariosFC.ui.chat.friends.di.DaggerFriendComponent;
import com.millonarios.MillonariosFC.ui.chat.friends.di.FriendModule;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsContract;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsPresenter;
import com.millonarios.MillonariosFC.utils.Commons;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Carlos on 22/01/2018.
 */

public class FriendsFragment extends BaseEventBusFragment implements FriendsContract.View, FriendsAdapter.OnItemClickListener, FriendsPopup.OnItemClickListener {

    public static final String TAG = FriendsFragment.class.getSimpleName();
    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    LinearLayoutManager linearLayoutManager;
    FriendsAdapter friendsAdapter;
    List<FriendsModelView> listFriends;

    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;

    @BindView(R.id.swipe_friends)
    SwipeRefreshLayout swipe_friends;

    @BindView(R.id.iv_msg_search)
    ImageView searchFriends;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @Inject
    FriendsPresenter presenter;

    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    public static FriendsFragment newInstance() {
        Bundle args = new Bundle();
        FriendsFragment fragment = new FriendsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        Log.i(TAG, "--->onCreateView " + TAG);
        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);

        isFragmentResumeOrCreate();

        swipe_friends.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (friendsAdapter != null) {
                    presenter.loadFriends();
                }
            }
        });


        return view;
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        if (searchKey.getText().toString().length() > 0)
            presenter.findByName(searchKey.getText().toString());
        else
            refresh();
    }

    @OnClick(R.id.iv_msg_search)
    void onFriendSearch() {
        presenter.findByName(searchKey.getText().toString());
    }

    public void isFragmentResumeOrCreate() {
        if ((fragmentResume && fragmentOnCreated) || (fragmentOnCreated && fragmentVisible)) {
            Log.i(TAG, "--->Friends update");
            refresh();
            Commons.hideKeyboard(getActivity());
        }
    }


    private void fetchFakeFriends(List<FriendsModelView> listFriends) {
        this.listFriends = listFriends;
        friendsAdapter = new FriendsAdapter(getContext(), listFriends, this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_friends.setLayoutManager(linearLayoutManager);
        rv_friends.setAdapter(friendsAdapter);
    }

    public void initComponent() {
        DaggerFriendComponent.builder()
                .appComponent(App.get().component())
                .friendModule(new FriendModule(this))
                .build().inject(FriendsFragment.this);
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;
            Log.i(TAG, "--->setUserVisibleHint alineacion 1: ");
            refresh();
            Commons.hideKeyboard(getActivity());
        } else if (visible) {        // only at fragment onCreated
            fragmentResume = false;
            fragmentVisible = true;
            fragmentOnCreated = true;
            Log.i(TAG, "--->setUserVisibleHint alineacion 2: ");
        } else if (!visible && fragmentOnCreated) {// only when you go out of fragment screen
            fragmentVisible = false;
            fragmentResume = false;
            Log.i(TAG, "--->setUserVisibleHint alineacion 3: ");

        }
    }

    @Override
    public void updateFriends(List<FriendsModelView> friends) {
        if (swipe_friends.isRefreshing())
            swipe_friends.setRefreshing(false);
        fetchFakeFriends(friends);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToastError(String errror) {

    }

    @Override
    public void refresh() {
        Log.i(TAG, "--->refresh()");
        presenter.loadFriends();
    }

    @Override
    public void addFriendSuccess() {

    }

    @Override
    public void addFriendFailed() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

    @Override
    public void onClickItem(FriendsModelView friendsModelView, String TAG) {
        Log.i(TAG, "--->onClickItem TAG: " + TAG);

        switch (TAG) {
            case FriendsConsts.TAG_ON_CLICK_ITEM_CONTENT: {
                if (isLongItemSelected()) {
                    clearAllListFalse();
                    friendsAdapter.notifyDataSetChanged();
                } else {
                    if(!friendsModelView.getBloqueado())
                        startActivity(ChatActivity.intent(presenter.findAmistad(friendsModelView.getId_amigo()), getContext()));
                }
                break;
            }
            case FriendsConsts.TAG_ON_CLICK_VIEW_POPUP: {
                FriendsPopup friendsPopup = new FriendsPopup();
                friendsPopup.setParams(friendsModelView, presenter.findAmistad(friendsModelView.getId_amigo()), this);
                showDialogFragment(friendsPopup);
                break;
            }
            case FriendsConsts.TAG_ON_CLICK_VIEW_TRASH: {
                presenter.removeFriends(friendsModelView);
                break;
            }
        }
        friendsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClickItem(FriendsModelView friendsModelView) {
        Log.i(TAG, "--->onLongClickItem: " + friendsModelView.getApodo());
        clearAllListFalse();
        for (int i = 0; i < listFriends.size(); i++) {
            if (listFriends.get(i).getId_amigo().equals(friendsModelView.getId_amigo())) {
                if (listFriends.get(i).isPressed()) {
                    listFriends.get(i).setPressed(false);
                } else {
                    listFriends.get(i).setPressed(true);
                }
                break;
            }
        }
        friendsAdapter.notifyDataSetChanged();
    }

    private void clearAllListFalse() {
        for (int i = 0; i < listFriends.size(); i++) {
            listFriends.get(i).setPressed(false);
        }
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    public boolean isLongItemSelected() {
        for (int i = 0; i < listFriends.size(); i++) {
            if (listFriends.get(i).isPressed()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClickItemPopup(String TAG) {

    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(FirebaseEvent event) {
        Log.i(TAG, "--->onMessageEvent FRIENDSFRAGMENT");
        if (event.getEvent() == FirebaseEvent.EVENT.REFRESCAR_AMIGOS) {
            presenter.loadFriends();
        }


    }
}
