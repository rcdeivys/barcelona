package com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.adapter.GroupDetailAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.di.DaggerGroupDetailComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.di.GroupDetailModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.mvp.GroupDetailContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groupdetail.mvp.GroupDetailPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class GroupDetailActivity extends AppCompatActivity implements GroupDetailContract.View, GroupDetailAdapter.OnItemClickListener{

    public final static String TAG = GroupsFragment.class.getSimpleName();
    public final static String TAG_ADD_NEW_FRIEND = "add_new_friend";
    public final static String TAG_GROUP_INFO = "another";

    @BindView(R.id.rv_group_members)
    RecyclerView listaMembers;

    @BindView(R.id.tv_exit_group)
    TextView leaveGroup;

    @BindView(R.id.tv_delete_group)
    TextView deleteGroup;

    @BindView(R.id.activity_cant_members_group)
    TextView cantMembers;

    @BindView(R.id.activity_name_group)
    TextView nameGroup;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.iv_img_group)
    CircleImageView imgGroup;

    @BindView(R.id.iv_edit_group)
    ImageView editGroup;

    @BindView(R.id.iv_add_friend_to_group)
    ImageView addMoreFriend;

    private GroupDetailAdapter groupDetailAdapter;

    private LinearLayoutManager mLayoutManager;

    private GroupModelView group;

    Unbinder unbinder;

    @Inject
    GroupDetailPresenter presenter;

    private Grupo grupo = null;

    public static Intent intent(Grupo grupo, Context context) {
        Intent intent = new Intent(context, GroupDetailActivity.class);
        intent.putExtra(TAG_GROUP_INFO, grupo);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        unbinder = ButterKnife.bind(this);
        initComponent();
        try {
            //TODO: no estoy recibiendo el grupo
            grupo = (Grupo)getIntent().getParcelableExtra(TAG_GROUP_INFO);
            Log.i("GROUPDETAIL"," ---> "+group.toString());
        } catch (Exception e) {
            Log.i("GROUPDETAIL"," ---> "+e.getMessage());
        }
        initRecyclerView();
        if(group!=null)
            presenter.loadMembers(group.getIdGroup());

    }

    public void initComponent() {
        DaggerGroupDetailComponent.builder()
                .appComponent(App.get().component())
                .groupDetailModule(new GroupDetailModule(this))
                .build().inject(GroupDetailActivity.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        listaMembers.setLayoutManager(mLayoutManager);
        List<FriendsModelView> selectedList = new ArrayList<>();
        groupDetailAdapter = new GroupDetailAdapter(selectedList,this);
        groupDetailAdapter.setOnItemClickListener(this);
        listaMembers.setAdapter(groupDetailAdapter);
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }

    @OnClick(R.id.iv_add_friend_to_group)
    void addMoreFriends(){
        //TODO: launch FriendActivity
        Intent intent = new Intent(this, FriendsActivity.class);
        //TODO: Put id group to add a new friend
        Log.i("MESSAGE"," ID GROUP ---> "+group.getIdGroup());
        intent.putExtra(TAG_ADD_NEW_FRIEND,group.getIdGroup());
        startActivity(intent);
    }

    @OnClick(R.id.iv_edit_group)
    void editGroupData(){
        //TODO: launch a dialog to change group data(image/name)
        Log.i("GROUPDETAIL"," ---> EDITING");
    }

    @OnClick(R.id.tv_delete_group)
    void deleteThisGroup(){
        //TODO: we call to api to delete this group. Lunch a dialog firts (only group's creator)
        Log.i("GROUPDETAIL"," ---> DELETING");
    }

    @OnClick(R.id.tv_exit_group)
    void leaveThisGroup(){
        //TODO: we call to api to leave this group. Lunch a dialog firts
        Log.i("GROUPDETAIL"," ---> LEAVING");
    }

    @Override
    public void updateMembers(List<FriendsModelView> friends) {
        if (groupDetailAdapter != null) {
            groupDetailAdapter.updateAll(friends);
        }
    }

    @Override
    public void refresh() {
        initRecyclerView();
        presenter.loadMembers(group.getIdGroup());
    }

    @Override
    public void onClickFriend(FriendsModelView friend) {
        //TODO: make a petition for to erased this selected friend from the group
        presenter.onClickMemberToDelete(friend);
    }
}
