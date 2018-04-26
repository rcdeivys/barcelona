package com.BarcelonaSC.BarcelonaApp.ui.chat.groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseEventBusFragment;
import com.BarcelonaSC.BarcelonaApp.models.firebase.FirebaseEvent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.CreateGroupActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.adapter.GroupsAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di.DaggerGroupsComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.di.GroupsModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.mvp.GroupsPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Pedro Gomez on 15/01/2018.
 */

public class GroupsFragment extends BaseEventBusFragment implements GroupsContract.View, GroupsAdapter.OnItemClickListener {

    public final static String TAG = GroupsFragment.class.getSimpleName();

    private static final String CATEGORY = "category";

    @BindView(R.id.grupos_recycler_view)
    RecyclerView mosaicoGrupos;

    @BindView(R.id.groups_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    private GroupsAdapter groupsAdapter;

    private LinearLayoutManager mLayoutManager;

    private String mActiveFragmentTag;

    Unbinder unbinder;

    private boolean fragmentResume = false;
    private boolean fragmentVisible = false;
    private boolean fragmentOnCreated = false;

    @Inject
    public GroupsPresenter presenter;

    public static GroupsFragment getInstance() {
        GroupsFragment fragment = new GroupsFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    public void initComponent() {
        DaggerGroupsComponent.builder()
                .appComponent(App.get().component())
                .groupsModule(new GroupsModule(this))
                .build().inject(GroupsFragment.this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        initRecyclerView();
        refresh();
        Log.d(TAG, "--->onCreateView() pedro");
        if ((fragmentResume && fragmentOnCreated) || (fragmentOnCreated && fragmentVisible)) {
            Log.i("FIREBASE", " ---> REFRESHDATA");
        }
        return view;
    }

    public void onTextChange(String text) {
        if (text.length() > 0) {
            //   msgNuevo.setVisibility(View.GONE);
            presenter.findByName(text);
        } else {
            //    msgNuevo.setVisibility(View.VISIBLE);
            refresh();
        }
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {   // only at fragment screen is resumed
            fragmentResume = true;
            fragmentVisible = false;
            fragmentOnCreated = true;
            Log.i(TAG, "--->setUserVisibleHint alineacion 1: ");
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

    void goToSelectionActivity() {

        Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

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
    public void goToSelectedGroup(String id_group) {

    }

    @Override
    public void showMessage(String message) {

    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mosaicoGrupos.setLayoutManager(mLayoutManager);
        List<GroupModelView> itemList = new ArrayList<>();
        groupsAdapter = new GroupsAdapter(itemList, getActivity());
        groupsAdapter.setOnItemClickListener(this);
        mosaicoGrupos.setAdapter(groupsAdapter);
        mosaicoGrupos.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                refresh();
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

    @Override
    public void onClickItem(GroupModelView group) {
        //TODO: ESTA REGRESANDO EL GRUPO VACIO
        getActivity().startActivity(ChatActivity.intent(group.getIdGroup(), getContext()));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(FirebaseEvent event) {
        Log.i("tag", "--->onMessageEvent update fragment");
        if (event.getEvent() == FirebaseEvent.EVENT.REFRESCAR_GRUPOS) {
            presenter.loadGroups();
        }
    }

}
