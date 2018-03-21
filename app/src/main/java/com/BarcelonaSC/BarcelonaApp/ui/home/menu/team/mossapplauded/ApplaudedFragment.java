package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.di.ApplaudedModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.mvp.ApplaudedContract;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.mvp.ApplaudedPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.models.MostApplaudedPlayerData;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.di.DaggerApplaudedComponent;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 14/10/2017.
 */

public class ApplaudedFragment extends BaseFragment implements ApplaudedContract.View, ApplauseAdapter.OnItemClickListener {

    private static final String TAG = ApplaudedFragment.class.getSimpleName();
    @BindView(R.id.rvApplause)
    RecyclerView rvApplause;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @Inject
    ApplaudedPresenter presenter;

    private String type;
    private ApplauseAdapter applauseAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static ApplaudedFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        ApplaudedFragment fragment = new ApplaudedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initComponent() {
        DaggerApplaudedComponent.builder()
                .appComponent(App.get().component())
                .applaudedModule(new ApplaudedModule(this))
                .build().inject(ApplaudedFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(Constant.Key.TYPE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initComponent();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applauded, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.getPlayerApplause();
            }
        });

        if (applauseAdapter == null) {
            showProgress();
            presenter.getPlayerApplause();
        } else {
            rvApplause.setAdapter(applauseAdapter);
            rvApplause.setLayoutManager(linearLayoutManager);
        }

        return view;
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToastError(String error) {
        setRefreshing(false);
        hideProgress();
        showToast(error, Toast.LENGTH_LONG);
    }

    @Override
    public void setApplauseData(MostApplaudedPlayerData mostApplaudedPlayer) {
        initRvAndAdapter();
        if (type.equals(Constant.Key.ACUMULATED))
            applauseAdapter.setData(mostApplaudedPlayer.getAcumulado());
        else
            applauseAdapter.setData(mostApplaudedPlayer.getPartido_actual());
        notifyDataSetChanged();
    }


    private void initRvAndAdapter() {
        if (applauseAdapter == null) {
            applauseAdapter = new ApplauseAdapter(this);
            rvApplause.setAdapter(applauseAdapter);
            rvApplause.setLayoutManager(linearLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        setRefreshing(false);
        hideProgress();
        applauseAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void onClickItem(int position) {
        if (type.equals(Constant.Key.ACUMULATED))
            navigator.navigateToPlayerActivity(Integer.parseInt(presenter.getAcumulatePlayer(position)), "");
        else
            navigator.navigateToPlayerActivity(Integer.parseInt(presenter.getActualPlayer(position)), "");
    }
}
