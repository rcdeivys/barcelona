package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.adapter.FriendsAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.di.DaggerNewConversationComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.di.NewConversationModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp.NewConversationContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.mvp.NewConversationPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.utils.constant.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class NewConversationActivity extends BaseSideMenuActivity implements NewConversationContract.View, FriendsAdapter.OnItemClickListener {

    @BindView(R.id.rv_friends)
    RecyclerView listaAmigos;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @BindView(R.id.tv_result_search)
    TextView searchNoResult;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.iv_more_conv)
    ImageView ivMoreConv;
    @BindView(R.id.tv_find_friend)
    TextView tvFindFind;
    @BindView(R.id.iv_logo_friend)
    ImageView ivLogoFriend;


    private LinearLayoutManager mLayoutManager;

    private FriendsAdapter friendsAdapter;

    private static String FRIENDS = "friends";

    Unbinder unbinder;
    int mOffset = 0;
    int mPageSize = 10;


    @Inject
    NewConversationPresenter presenter;

    public static Intent intent(ArrayList<Amigos> amigos, Context context) {
        Intent intent = new Intent(context, NewConversationActivity.class);
        intent.putParcelableArrayListExtra(FRIENDS, amigos);
        return intent;
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        if (searchKey.getText().length() > 0)
            showProgress();
        presenter.findByName(searchKey.getText().toString());

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        unbinder = ButterKnife.bind(this);
        super.initMenu();
        ivMoreConv.setVisibility(View.INVISIBLE);
        super.setSubTitle(ConfigurationManager.getInstance().getConfiguration().getTit163());
        initComponent();

        presenter.onAttach(this);
        initRecyclerView();
    }

    public void initComponent() {
        DaggerNewConversationComponent.builder()
                .appComponent(App.get().component())
                .newConversationModule(new NewConversationModule(this))
                .build().inject(NewConversationActivity.this);
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
        presenter.loadFriends(0);
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (searchKey.getText().toString().equals("")) {
                    presenter.loadFriends(current_page);
                    showProgress();
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }


    @Override
    public void updateFriends(List<FriendsModelView> friends) {
        hideProgress();
        listaAmigos.setVisibility(View.VISIBLE);
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
    public void onCompleteFriends(Amigos amigos) {
        suiche = false;
        startActivity(ChatActivity.intent(amigos.getId(), getActivity()));
        finish();
    }

    @Override
    public void showProgress() {
        searchNoResult.setVisibility(View.GONE);
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoResultText(boolean show) {
        hideProgress();
        if (show)
            Toast.makeText(this, getText(R.string.group_search), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showToastError(String errror) {
        hideProgress();
    }

    boolean suiche = false;

    @Override
    public void onClickFriend(FriendsModelView friend) {
        if (suiche)
            return;
        suiche = true;
        presenter.onClickAcceptUser(SessionManager.getInstance().getUser().getId_usuario(), friend);
     /*   if (id_group != null) {
            //TODO: call to preseter to add the user to the group
            //TODO: in a success exit this activity
            Log.i("FRIENDADD", "---> id_group: " + id_group + " id_friend: " + friend.getId_amigo());
            presenter.addSelectedFriendAGroup(id_group, friend.getId_amigo());
        } else {
            //TODO: Go to activity chat with the selected friend
            Intent intent = new Intent(this, ChatActivity.class);
            //TODO: send data to set a conversation
            //Log.i("MESSAGE"," ID GROUP ---> "+group.getIdGroup());
            //intent.putExtra(TAG_ADD_NEW_FRIEND,group.getIdGroup());
            startActivityForResult(ChatActivity.intent(presenter.findAmistad(friend.getId_amigo()), this), ChatActivity.CHAT_REQUEST_CODE);
        }*/
    }

    @Override
    public void refresh() {
        presenter.loadFriends(0);
    }

    @Override
    public void addFriendSuccess() {
        Toast.makeText(getBaseContext(), getText(R.string.group_added_member), Toast.LENGTH_SHORT).show();
        this.finish();
    }

    @Override
    public void addFriendFailed() {
        suiche = false;
        Toast.makeText(getBaseContext(), getText(R.string.unexpected_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFindFriend(boolean visibility) {
        hideProgress();
        listaAmigos.setVisibility(View.INVISIBLE);
        ivMoreConv.setVisibility(View.INVISIBLE);
        tvFindFind.setVisibility(visibility?View.VISIBLE:View.GONE);
        ivLogoFriend.setVisibility(visibility?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClickMenuItem(String fragment) {
        Intent resultIntent = new Intent(this, HomeActivity.class);

        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.putExtra(Constant.Key.SECCION_SELECTED, fragment);
        startActivity(resultIntent);
        finish();
    }
}
