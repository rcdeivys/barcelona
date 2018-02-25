package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.eventbus.MonumentalRankingEvent;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalRankingItem;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.adapters.MonumentalRankingAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.di.DaggerMonumentalRankComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.di.MonumentalRankModule;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp.MonumentalRankContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.ranking.mvp.MonumentalRankPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalRankingFragment extends BaseFragment implements MonumentalRankContract.View, MonumentalRankingAdapter.OnMonumentalRankingClickListener {

    public static final String TAG = MonumentalRankingFragment.class.getSimpleName();

    @BindView(R.id.rv_monumental_ranking)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    MonumentalRankingAdapter adapter;
    Unbinder unbinder;

    @Inject
    public MonumentalRankPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_monumental_ranking, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
        initRecyclerView();
        initComponent();
        presenter.loadMonumentalRank();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                presenter.loadMonumentalRank();
            }
        });
    }

    public void initComponent() {
        DaggerMonumentalRankComponent.builder()
                .appComponent(App.get().component())
                .monumentalRankModule(new MonumentalRankModule(this))
                .build().inject(MonumentalRankingFragment.this);
    }

    private void initRecyclerView() {
        List<MonumentalRankingItem> rankingItemList = new ArrayList<>();
        adapter = new MonumentalRankingAdapter(rankingItemList);
        adapter.setOnMonumentalRankingClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showRankingList(List<MonumentalRankingItem> rankingItemList) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.update(rankingItemList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancel();
    }

    @Override
    public void onClickListener(String id) {

    }

    @Subscribe
    public void onMessageEvent(MonumentalRankingEvent event) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadMonumentalRank();
            }
        }, 1000);
    }
}