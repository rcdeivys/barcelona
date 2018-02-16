package com.BarcelonaSC.BarcelonaApp.ui.chat.groups;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter.GroupsActivityAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di.DaggerGroupsComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di.GroupsModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

public class GroupsActivity extends AppCompatActivity implements GroupsContract.View, GroupsActivityAdapter.OnItemClickListener{

    public static final String TAG = GroupsActivity.class.getSimpleName();
    public static final String TAG_AMIGO = "amigo";

    @BindView(R.id.rv_groups)
    RecyclerView listGroups;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.et_group_search)
    EditText groupSearch;

    private GroupsActivityAdapter groupsAdapter;

    private LinearLayoutManager mLayoutManager;

    Unbinder unbinder;

    private Amigos amigo = null;

    @Inject
    public GroupsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        unbinder = ButterKnife.bind(this);
        initComponent();
        initRecyclerView();
        try {
            amigo = (Amigos) getIntent().getParcelableExtra(TAG_AMIGO);
            Log.i(TAG, "--->" + amigo);
        } catch (Exception e) {
            Log.i(TAG, "--->" + e);
        }
        presenter.loadGroups();
    }

    public void initComponent() {
        DaggerGroupsComponent.builder()
                .appComponent(App.get().component())
                .groupsModule(new GroupsModule(this))
                .build().inject(GroupsActivity.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        listGroups.setLayoutManager(mLayoutManager);
        List<GroupModelView> selectedList = new ArrayList<>();
        groupsAdapter = new GroupsActivityAdapter(selectedList,this);
        groupsAdapter.setOnItemClickListener(this);
        listGroups.setAdapter(groupsAdapter);
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }


    @OnTextChanged( value = R.id.et_group_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged(){
        if (groupSearch.getText().toString().length() > 0) {
            presenter.findByName(groupSearch.getText().toString());
        } else {
            refresh();
        }
    }

    @Override
    public void updateGroups(List<GroupModelView> groups) {
        if (groupsAdapter != null) {
            groupsAdapter.updateAll(groups);
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void refresh() {
        presenter.loadGroups();
    }

    @Override
    public void onClickItem(GroupModelView group) {
        if(amigo!=null){
            presenter.addSelectedFriendToAGroup(amigo.getId(),group.getIdGroup());
        }
    }
}
