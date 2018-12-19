package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.di.DaggerIdealElevenRankingComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.di.IdealElevenRankingModule;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp.IdealElevenRankingContract;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking.mvp.IdealElevenRankingPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IdealElevenRankingFragment extends ShareBaseFragment implements IdealElevenRankingContract.View {

    private String TAG = IdealElevenRankingFragment.class.getSimpleName();

    @BindView(R.id.rv_ranking)
    RecyclerView recyclerView;
    @BindView(R.id.content_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayoutManager mLayoutManager;
    IdealElevenRankingAdapter adapter;

    @Inject
    IdealElevenRankingPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_idealeleven_ranking, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
        initRecyclerView();
    }

    public void initComponent() {
        DaggerIdealElevenRankingComponent.builder()
                .appComponent(App.get().component())
                .idealElevenRankingModule(new IdealElevenRankingModule(this))
                .build().inject(IdealElevenRankingFragment.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        List<Object> itemList = new ArrayList<>();
        adapter = new IdealElevenRankingAdapter(getActivity(), itemList);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
            }
        });

        presenter.getIdealElevenRankingData();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showIdealElevenRankingData(List<Object> ranking) {
        adapter.updateAll(ranking);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void share() {
        Log.i(TAG, "---> ideal eleven");
        if (!new SessionManager(App.getAppContext()).getUrlLineUpShare().equals("")) {
            new ShareSection(App.getAppContext()).shareIdealEleven(new SessionManager(App.getAppContext()).getUrlLineUpShare());
        } else {
            showToast(App.getAppContext().getString(R.string.ideal_eleven), Toast.LENGTH_LONG);
        }
    }
}
