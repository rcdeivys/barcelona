package com.millonarios.MillonariosFC.ui.chat.friendselection;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.CreationGroupActivity;
import com.millonarios.MillonariosFC.ui.chat.friendselection.adapter.FriendSelectedAdapter;
import com.millonarios.MillonariosFC.ui.chat.friendselection.adapter.FriendSelectionAdapter;
import com.millonarios.MillonariosFC.ui.chat.friendselection.di.DaggerFriendSelectionComponent;
import com.millonarios.MillonariosFC.ui.chat.friendselection.di.FriendSelectionModule;
import com.millonarios.MillonariosFC.ui.chat.friendselection.mvp.FriendSelectionContract;
import com.millonarios.MillonariosFC.ui.chat.friendselection.mvp.FriendSelectionPresenter;
import com.millonarios.MillonariosFC.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class FriendSelectionActivity extends AppCompatActivity implements FriendSelectionContract.View, FriendSelectionAdapter.OnItemClickListener, FriendSelectedAdapter.OnItemClickListener{

    public final static String TAG = FriendSelectionActivity.class.getSimpleName();

    private static final String CATEGORY = "category";

    @BindView(R.id.friend_selection_recycler_view)
    RecyclerView listaAmigos;

    @BindView(R.id.friend_selected_recycler_view)
    RecyclerView listaSeleccionados;

    @BindView(R.id.friend_selection_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.iv_go_creation)
    ImageButton btnGoToCreationGoup;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.iv_friend_search)
    ImageView searchFriends;

    @BindView(R.id.et_friend_search)
    EditText searchKey;

    @BindView(R.id.tv_result_search)
    TextView searchNoResult;

    private FriendSelectionAdapter friendSelectionAdapter;
    private FriendSelectedAdapter friendSelectedAdapter;

    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGLayoutManager;

    Unbinder unbinder;

    @Inject
    public FriendSelectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_selection);
        unbinder = ButterKnife.bind(this);
        initComponent();
        mGLayoutManager = new GridLayoutManager(this,4);
        listaSeleccionados.setLayoutManager(mGLayoutManager);
        List<Amigos> selectedList = new ArrayList<>();
        friendSelectedAdapter = new FriendSelectedAdapter(selectedList,this);
        friendSelectedAdapter.setOnItemClickListener(this);
        listaSeleccionados.setAdapter(friendSelectedAdapter);
        initRecyclerView();
        presenter.loadFriends();
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        listaAmigos.setLayoutManager(mLayoutManager);
        List<Amigos> itemList = new ArrayList<>();
        friendSelectionAdapter = new FriendSelectionAdapter(itemList,this);
        friendSelectionAdapter.setOnItemClickListener(this);
        listaAmigos.setAdapter(friendSelectionAdapter);
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

    @OnClick(R.id.iv_go_creation)
    void goCreationGroup(){
        Crashlytics.log(Log.DEBUG,"AMIGO","---> CREACION");
        Intent intent = new Intent(this, CreationGroupActivity.class);
        intent.putParcelableArrayListExtra(CreationGroupActivity.TAG_MEMBERS,presenter.getAllNewGroupMembers());
        startActivity(intent);
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }


    @OnClick(R.id.iv_friend_search)
    void onFriendSearch(){
        presenter.findByName(searchKey.getText().toString());
    }

    @OnTextChanged(value = R.id.et_friend_search,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(){
        if(searchKey.getText().toString().length()>0)
            presenter.findByName(searchKey.getText().toString());
        else
            refresh();
    }

    public void initComponent() {
        DaggerFriendSelectionComponent.builder()
                .appComponent(App.get().component())
                .friendSelectionModule(new FriendSelectionModule(this))
                .build().inject(FriendSelectionActivity.this);
    }

    @Override
    public void updateFriends(List<Amigos> friends) {
        if (friendSelectionAdapter != null) {
            if(friends.size()==0) {
                searchNoResult.setVisibility(View.VISIBLE);
            }else{
                searchNoResult.setVisibility(View.GONE);
            }
            friendSelectionAdapter.updateAll(friends);
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateSelectedFriends(List<Amigos> friends) {
        if (friendSelectedAdapter != null) {
            if (friends.size() > 0) {
                Crashlytics.log(Log.DEBUG,"AMIGO"," ---> SIZE "+friends.size());
                btnGoToCreationGoup.setVisibility(View.VISIBLE);
            }
            else
                btnGoToCreationGoup.setVisibility(View.GONE);
            friendSelectedAdapter.updateAll(friends);
        }
    }

    @Override
    public void refresh() {
        presenter.loadFriends();
    }

    @Override
    public void onClickFriend(Amigos friend) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," presione ---> to select "+friend.getId());
        presenter.onClickFriend(friend);
    }

    @Override
    public void onClickSelectedFriend(Amigos friend) {
        Crashlytics.log(Log.DEBUG,"AMIGO"," ---> selected "+friend.getId());
        presenter.onClickSelectedFriend(friend);
    }
}
