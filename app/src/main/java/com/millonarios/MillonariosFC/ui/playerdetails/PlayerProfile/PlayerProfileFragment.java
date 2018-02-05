package com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.eventbus.PlayerEvent;
import com.millonarios.MillonariosFC.models.ApplauseData;
import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.models.PlayerData;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerActivity;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.di.DaggerPlayerProfileComponent;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.di.PlayerProfileModule;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfileContract;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.mvp.PlayerProfilePresenter;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerProfileFragment extends BaseFragment
        implements PlayerProfileContract.View, PlayerProfileAdapter.OnItemClickListener {

    public static final String TAG = PlayerProfileFragment.class.getSimpleName();
    @BindView(R.id.rv_PlayerNews)
    RecyclerView rvPlayerNews;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @Inject
    PlayerProfilePresenter presenter;

    private PlayerProfileAdapter playerProfileAdapter;
    private LinearLayoutManager linearLayoutManager;
    String type;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    public static PlayerProfileFragment newInstance(int playerId, String type) {

        Bundle args = new Bundle();
        args.putInt(Constant.Key.PLAYER_ID, playerId);
        args.putString(Constant.Key.TYPE, type);
        PlayerProfileFragment fragment = new PlayerProfileFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        final int playerId = getArguments().getInt(Constant.Key.PLAYER_ID);
        type = getArguments().getString(Constant.Key.TYPE);
        //presenter.getPlayer(String.valueOf(playerId));
        if (type.equals(Constant.Key.GAME_FB)) {
            presenter.getPlayerFB(String.valueOf(playerId));
        } else {
            presenter.getPlayer(String.valueOf(playerId));
        }
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                //presenter.getPlayer();
                if (type.equals(Constant.Key.GAME_FB)) {
                    presenter.getPlayerFB(String.valueOf(playerId));
                } else {
                    presenter.getPlayer(String.valueOf(playerId));
                }
            }
        });

        if (playerProfileAdapter == null) {
            showProgress();
            if (type.equals(Constant.Key.GAME_FB)) {
                presenter.getPlayerFB(String.valueOf(playerId));
            } else {
                presenter.getPlayer(String.valueOf(playerId));
            }
            //presenter.getPlayer(String.valueOf(playerId));
        } else {
            rvPlayerNews.setAdapter(playerProfileAdapter);
            rvPlayerNews.setLayoutManager(linearLayoutManager);
        }

        return view;
    }

    public void initComponent() {
        DaggerPlayerProfileComponent.builder()
                .appComponent(App.get().component())
                .playerProfileModule(new PlayerProfileModule(this))
                .build().inject(PlayerProfileFragment.this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
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
    public String getImei() {
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToast(String error) {
        setRefreshing(false);
        hideProgress();
        showToast(error, Toast.LENGTH_LONG);
    }

    @Override
    public void setPlayerData(PlayerData player) {
        if (playerProfileAdapter == null)
            ((PlayerActivity) getActivity()).initSubToolBar(player.getNombre());
        initRvAndAdapter();
        playerProfileAdapter.setData(player);
        notifyDataSetChanged();
    }

    @Override
    public void setPlayerApplause(ApplauseData applauseData) {
        presenter.setPlayerApplause();
    }

    private void initRvAndAdapter() {
        if (playerProfileAdapter == null) {
            playerProfileAdapter = new PlayerProfileAdapter(this, type);
            rvPlayerNews.setAdapter(playerProfileAdapter);
            rvPlayerNews.setLayoutManager(linearLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        playerProfileAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void onClickItem(int position) {
        presenter.clickItem(position);
    }

    @Override
    public void onClickHeader() {
        presenter.setPlayerApplause();
    }

    @Override
    public void navigateToVideoNewsActivity(News news) {
        navigator.navigateToVideoNewsActivity(news);
    }

    @Override
    public void navigateToInfografiaActivity(News news) {
        navigator.navigateToInfografiaActivity(news);
    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {
        navigator.navigateToNewsDetailsActivity(news);
    }

    @Override
    public void navigateToGalleryActivity(int id) {
        navigator.navigateToGalleryActivity(id);
    }
}
