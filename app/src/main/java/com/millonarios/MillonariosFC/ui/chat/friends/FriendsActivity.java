package com.millonarios.MillonariosFC.ui.chat.friends;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.friends.adapter.FriendsAdapter;
import com.millonarios.MillonariosFC.ui.chat.friends.di.DaggerFriendComponent;
import com.millonarios.MillonariosFC.ui.chat.friends.di.FriendModule;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsContract;
import com.millonarios.MillonariosFC.ui.chat.friends.mvp.FriendsPresenter;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.GroupDetailActivity;
import com.millonarios.MillonariosFC.utils.EndlessScrollListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class FriendsActivity extends AppCompatActivity implements FriendsContract.View, FriendsAdapter.OnItemClickListener {

    @BindView(R.id.rv_friends)
    RecyclerView listaAmigos;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.iv_msg_search)
    ImageView searchFriends;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @BindView(R.id.tv_result_search)
    TextView searchNoResult;

    private LinearLayoutManager mLayoutManager;

    private FriendsAdapter friendsAdapter;

    private String id_group;

    private static String FRIENDS = "friends";

    Unbinder unbinder;

    @Inject
    FriendsPresenter presenter;

    public static Intent intent(ArrayList<Amigos> amigos, Context context) {
        Intent intent = new Intent(context, FriendsActivity.class);
        intent.putParcelableArrayListExtra(FRIENDS, amigos);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        unbinder = ButterKnife.bind(this);

        initComponent();

        try {
            id_group = getIntent().getStringExtra(GroupDetailActivity.TAG_ADD_NEW_FRIEND);
            Log.i("MESSAGE", " ---> FROM GROUP " + id_group);
        } catch (Exception e) {
            Log.i("MESSAGE", "EXCEPTION ---> " + e.getMessage());
        }
        initRecyclerView();
    }

    public void initComponent() {
        DaggerFriendComponent.builder()
                .appComponent(App.get().component())
                .friendModule(new FriendModule(this))
                .build().inject(FriendsActivity.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        listaAmigos.setLayoutManager(mLayoutManager);
        List<FriendsModelView> itemList = new ArrayList<>();
        friendsAdapter = new FriendsAdapter(itemList, this);
        friendsAdapter.setOnItemClickListener(this);
        listaAmigos.setAdapter(friendsAdapter);
        listaAmigos.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchKey.setText("");
                initRecyclerView();
                refresh();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
        presenter.loadFriends();
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    //refresh(current_page);
                    //progressBar.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }


    @OnClick(R.id.iv_msg_search)
    void onFriendSearch() {
        presenter.findByName(searchKey.getText().toString());
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        if (searchKey.getText().toString().length() >= 0) {
            presenter.findByName(searchKey.getText().toString());
        } else {
            refresh();
        }
    }

    @Override
    public void updateFriends(List<FriendsModelView> friends) {
        if (friendsAdapter != null) {
            if (friends.size() == 0) {
                searchNoResult.setVisibility(View.VISIBLE);
            } else {
                searchNoResult.setVisibility(View.GONE);
            }
            friendsAdapter.updateAll(friends);
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
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
    public void onClickFriend(FriendsModelView friend) {
        if (id_group != null) {
            //TODO: call to preseter to add the user to the group
            //TODO: in a success exit this activity
            presenter.addSelectedFriendAGroup(id_group, "");
        } else {
            //TODO: Go to activity chat with the selected friend
            Intent intent = new Intent(this, ChatActivity.class);
            //TODO: send data to set a conversation
            //Log.i("MESSAGE"," ID GROUP ---> "+group.getIdGroup());
            //intent.putExtra(TAG_ADD_NEW_FRIEND,group.getIdGroup());
            startActivity(ChatActivity.intent(presenter.findAmistad(friend.getId_amigo()), this));
        }
    }

    @Override
    public void refresh() {
        presenter.loadFriends();
    }

    @Override
    public void addFriendSuccess() {
        this.finish();
    }

    @Override
    public void addFriendFailed() {
        Toast.makeText(getBaseContext(), getText(R.string.unexpected_error), Toast.LENGTH_SHORT).show();
    }
}
