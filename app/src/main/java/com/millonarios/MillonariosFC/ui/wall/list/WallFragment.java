package com.millonarios.MillonariosFC.ui.wall.list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.eventbus.WallCreateCommentEvent;
import com.millonarios.MillonariosFC.eventbus.WallCreatePostEvent;
import com.millonarios.MillonariosFC.models.UserItem;
import com.millonarios.MillonariosFC.ui.home.HomeActivity;
import com.millonarios.MillonariosFC.ui.wall.WallProfileDialog;
import com.millonarios.MillonariosFC.ui.wall.comment.WallCommentActivity;
import com.millonarios.MillonariosFC.ui.wall.list.di.DaggerWallComponent;
import com.millonarios.MillonariosFC.ui.wall.list.di.WallModule;
import com.millonarios.MillonariosFC.ui.wall.list.mvp.WallContract;
import com.millonarios.MillonariosFC.ui.wall.list.mvp.WallPresenter;
import com.millonarios.MillonariosFC.ui.wall.list.views.adapters.WallAdapter;
import com.millonarios.MillonariosFC.ui.wall.post.WallCreatePostActivity;
import com.millonarios.MillonariosFC.utils.EndlessScrollListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallFragment extends Fragment implements WallContract.View, WallAdapter.WallClickListener {


    @BindView(R.id.rv_wall)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    EndlessScrollListener endlessScrollListener;

    private LinearLayoutManager mLayoutManager;
    private WallAdapter wallAdapter;
    private List<Object> itemList;

    private int showCommentPosition = 0;

    @Inject
    public WallPresenter presenter;

    public static WallFragment newInstance() {

        Bundle args = new Bundle();

        WallFragment fragment = new WallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        return view;
    }

    public void initComponent() {
        DaggerWallComponent.builder()
                .appComponent(App.get().component())
                .wallModule(new WallModule(this))
                .build().inject(WallFragment.this);
    }

    public void initRecyclerView() {
        endlessScrollListener = null;
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        itemList = new ArrayList<>();
        itemList.add(SessionManager.getInstance().getUser());

        wallAdapter = new WallAdapter(getContext(), itemList);

        wallAdapter.setWallClickListener(this);
        recyclerView.setAdapter(wallAdapter);
        presenter.load();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });
    }

    @OnClick(R.id.btn_top)
    public void btnTop() {
        recyclerView.smoothScrollToPosition(0);
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        if (endlessScrollListener == null) {
            endlessScrollListener = new EndlessScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        presenter.loadPage(current_page);
                    }
                }
            };
        }
        return endlessScrollListener;
    }

    @Override
    public void setLoad(List<Object> list, boolean pagination) {

        if (pagination) {
            wallAdapter.addWall(list);
        } else {
            wallAdapter.initList(list);
        }
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        wallAdapter.showNoMoreDataToDisplay();
        swipeRefreshLayout.setRefreshing(false);

    }

    @Override
    public void showProgress() {
        wallAdapter.showLoading();
    }

    @Override
    public void hideProgress() {
        wallAdapter.hideLoading();
    }

    @Override
    public void showToastError() {
        wallAdapter.showNoMoreDataToDisplay();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreatePostListener() {
        Intent intent = new Intent(getActivity(), WallCreatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivityForResult(intent, 2000);
    }

    @Override
    public void onProfileListener() {
        ((HomeActivity) getActivity()).presenter.onItemMenuSelected(com.millonarios.MillonariosFC.utils.Constants.Constant.Menu.PROFILE);
    }

    @Override
    public void onDeletePost(String id_post) {
        presenter.delete(id_post);
    }

    @Override
    public void onShowProfileListener(UserItem userItem) {
        WallProfileDialog.instance(userItem).show(getChildFragmentManager(), "WallProfileDialog");
    }

    @Override

    public void onClickLikeListener(String id_post) {
        presenter.clap(id_post);
    }

    @Override
    public void onClickCommentListener(String id, Integer cout_clap, int position) {
        showCommentPosition = position;
        Intent intent = new Intent(getActivity(), WallCommentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(WallCommentActivity.ID_POST, id);
        intent.putExtra(WallCommentActivity.COUNT_CLAP, String.valueOf(cout_clap));
        getActivity().startActivity(intent);
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(WallCreatePostEvent event) {
        if (event.isCreate()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    initRecyclerView();
                }
            }, 1000);
        }
    }

    @Subscribe
    public void onMessageEvent(WallCreateCommentEvent event) {
        wallAdapter.updateCountComment(event.getCountComment(), showCommentPosition);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
    }
}
