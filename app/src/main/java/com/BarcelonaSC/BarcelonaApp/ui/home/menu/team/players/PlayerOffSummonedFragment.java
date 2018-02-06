package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.di.PlayerModule;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.di.DaggerPlayerComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.mvp.PlayerContract;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players.mvp.PlayerPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerOffSummonedFragment extends BaseFragment implements PlayerContract.View, PlayerAdapter.OnItemClickListener {

    public static final String TAG = PlayerOffSummonedFragment.class.getSimpleName();
    @BindView(R.id.rv_players)
    RecyclerView rvPlayers;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.banner)
    RelativeLayout banner;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.btn_top)
    ImageButton btnTop;
    Unbinder unbinder;

    @Inject
    PlayerPresenter presenter;

    private int height;
    private PlayerAdapter playerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private String type;

    public static PlayerOffSummonedFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        PlayerOffSummonedFragment fragment = new PlayerOffSummonedFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(Constant.Key.TYPE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        linearLayoutManager = new LinearLayoutManager(getContext());
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_off_summoned, container, false);

        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });

        if (playerAdapter == null) {
            showProgress();
            getData();
        } else {
            rvPlayers.setAdapter(playerAdapter);
            rvPlayers.setLayoutManager(linearLayoutManager);
        }

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvPlayers.smoothScrollToPosition(0);
            }
        });

        return view;
    }


    public void getData() {
        if (type.equals(Constant.Key.GAME_SUPPONNED)) {
            presenter.getGameSummoned();
        } else if (type.equals(Constant.Key.GAME_FB)) {
            presenter.getGameFB();
        } else {
            presenter.getPlayeroff();
        }
    }

    public void initComponent() {
        DaggerPlayerComponent.builder()
                .appComponent(App.get().component())
                .playerModule(new PlayerModule(this, type))
                .build().inject(PlayerOffSummonedFragment.this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

    @Override
    public void setPlayer(List<NominaItem> players) {

        initRvAndAdapter();
        playerAdapter.setData(players);
        notifyDataSetChanged();
    }

    @Override
    public void setPlayerWithHeader(GameSummonedData gameSummoned) {
        initRvAndAdapter();
        playerAdapter.setData(gameSummoned);
        notifyDataSetChanged();
    }


    private void initRvAndAdapter() {
        if (playerAdapter == null) {
            playerAdapter = new PlayerAdapter(this);
            rvPlayers.setAdapter(playerAdapter);
            rvPlayers.setLayoutManager(linearLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        playerAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }


    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToastError(String error) {
        showToast(error, Toast.LENGTH_LONG);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void navigateToPlayerActivity(int playerId, String type1) {
        Log.i(TAG, "--->player id " + playerId);
        navigator.navigateToPlayerActivity(playerId, type);
    }

    @Override
    public void navigateToLineUp(String calendaryId) {

    }

    @Override
    public void onClickItem(int position) {
        presenter.onClickItem(position);
    }

    @Override
    public void onClickHeader() {
        presenter.onClickHeader();
    }
}
