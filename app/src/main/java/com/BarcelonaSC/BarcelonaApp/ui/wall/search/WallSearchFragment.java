package com.BarcelonaSC.BarcelonaApp.ui.wall.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.WallFragmentList;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.adapter.WallSearchAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.di.DaggerWallSearchComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.di.WallSearchModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp.WallSearchContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.search.mvp.WallSearchPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 3/17/18.
 */

public class WallSearchFragment extends Fragment implements WallSearchContract.View, WallSearchAdapter.OnItemClickListener {

    public static final String TAG = WallSearchFragment.class.getSimpleName();

    LinearLayoutManager linearLayoutManager;
    WallSearchAdapter wallSearchAdapter;

    @BindView(R.id.rv_friends)
    RecyclerView rv_friends;

    @BindView(R.id.swipe_friends)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.iv_msg_search)
    ImageView searchFriends;

    @BindView(R.id.et_msg_search)
    EditText searchKey;

    @Inject
    WallSearchPresenter presenter;

    @BindView(R.id.img_profile)
    CircleImageView imgProgile;

    Unbinder unbinder;

    EndlessScrollListener endlessScrollListener;

    private LinearLayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    public static WallSearchFragment newInstance() {
        Bundle args = new Bundle();
        WallSearchFragment fragment = new WallSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall_search, container, false);
        Log.i(TAG, "--->onCreateView " + TAG);
        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);

        Glide.with(getContext()).load(SessionManager.getInstance().getUser().getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProgile);

        Commons.hideKeyboard(getActivity());

        initRecyclerView();
        return view;
    }

    public void initRecyclerView() {
        endlessScrollListener = null;
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        rv_friends.setLayoutManager(mLayoutManager);

        List<WallSearchItem> itemList = new ArrayList<>();

        wallSearchAdapter = new WallSearchAdapter(itemList, getContext());
        wallSearchAdapter.setOnItemClickListener(this);
        rv_friends.setAdapter(wallSearchAdapter);

        presenter.search(searchKey.getText().toString());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        if (endlessScrollListener == null) {
            endlessScrollListener = new EndlessScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current_page) {
                    if (!swipeRefreshLayout.isRefreshing()) {
                        presenter.searchPage(searchKey.getText().toString(), String.valueOf(current_page));
                    }
                }
            };
        }
        return endlessScrollListener;
    }

    @OnClick(R.id.btn_back)
    public void returnWall() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .remove(getFragmentManager().findFragmentByTag(WallSearchFragment.TAG))
                .show(getFragmentManager().findFragmentByTag(WallFragmentList.TAG))
                .commit();
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        if (searchKey.getText().toString().length() > 0)
            presenter.search(searchKey.getText().toString());
        else
            refresh();
    }

    @OnClick(R.id.iv_msg_search)
    void onFriendSearch() {
        presenter.search(searchKey.getText().toString());
    }

    public void initComponent() {
        DaggerWallSearchComponent.builder()
                .appComponent(App.get().component())
                .wallSearchModule(new WallSearchModule(this))
                .build().inject(WallSearchFragment.this);
    }

    @Override
    public void search(List<WallSearchItem> friends) {

        swipeRefreshLayout.setRefreshing(false);
        wallSearchAdapter.clearAll();
        wallSearchAdapter.updateAll(friends);
        rv_friends.addOnScrollListener(initRecyclerViewScroll());
    }

    @Override
    public void searchPage(List<WallSearchItem> friends) {
        wallSearchAdapter.updateAll(friends);
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
        presenter.search("");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }


    @Override
    public void onClickProfile(WallSearchItem wallSearchItem) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, WallFragmentList.newInstance("profile", String.valueOf(wallSearchItem.getId())), WallCommentFragment.TAG)
                .commitAllowingStateLoss();
    }
}
