package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalPoll;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.adapters.MonumentalAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.di.DaggerMonumentalComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.di.MonumentalModule;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp.MonumentalContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.mvp.MonumentalPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.MonumentalProfileActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalFragment extends BaseFragment implements MonumentalContract.View, MonumentalAdapter.OnItemClickListener {

    public static final String TAG = MonumentalFragment.class.getSimpleName();

    @BindView(R.id.btn_top)
    ImageButton btnTop;
    @BindView(R.id.news_swipe)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.news_rv)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    MonumentalAdapter monumentalAdapter;

    LinearLayoutManager mLayoutManager;

    @Inject
    public MonumentalPresenter presenter;

    public static MonumentalFragment getInstance() {
        return new MonumentalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        refresh();
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        return view;
    }

    private void initComponent() {
        DaggerMonumentalComponent.builder()
                .appComponent(App.get().component())
                .monumentalModule(new MonumentalModule(this))
                .build().inject(MonumentalFragment.this);
    }

    @Override
    public void setMonumentals(MonumentalPoll monumentals) {
        if (monumentalAdapter == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.VERTICAL, false);

            monumentalAdapter = new MonumentalAdapter(getActivity(), monumentals);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setAdapter(monumentalAdapter);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refresh();
                    progressBar.setVisibility(View.VISIBLE);

                }
            });
        }
        monumentalAdapter.setData(monumentals);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRefreshing(boolean state) {
        if (state) {
            presenter.getMonumentals();
        }
    }

    @Override
    public void showToastError() {

    }

    @Override
    public void navigateToMonumentalProfile(String monumentalId, String PollId) {
        Intent intent = new Intent(getActivity(), MonumentalProfileActivity.class);
        intent.putExtra(Constant.Key.MONUMETAL_ID, monumentalId);
        intent.putExtra(Constant.Key.SURVEY_ID, PollId);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void refresh() {
        presenter.getMonumentals();
        setRefreshing(false);
    }

    @Override
    public void onClickItem(int position) {
        //Aquí mover a monumentalProfile, pendiente.
        presenter.onClickItem(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

}