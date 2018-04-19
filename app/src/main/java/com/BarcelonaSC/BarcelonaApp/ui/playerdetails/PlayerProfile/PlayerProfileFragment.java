package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.ApplauseData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerActivity;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.di.DaggerPlayerProfileComponent;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.di.PlayerProfileModule;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp.PlayerProfileContract;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile.mvp.PlayerProfilePresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

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
        if (type.equals(Constant.Key.GAME_FB)) {
            presenter.getPlayerFB(String.valueOf(playerId));
        } else {
            presenter.getPlayer(String.valueOf(playerId));
        }
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
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
        notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
        showToast(error, Toast.LENGTH_SHORT);
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
        if(playerProfileAdapter!=null) {
            playerProfileAdapter.notifyDataSetChanged();
            setRefreshing(false);
            hideProgress();
        }
    }

    @Override
    public void onClickItem(News news) {
        presenter.clickItem(news);
    }

    @Override
    public void onClickHeader() {
        presenter.setPlayerApplause();
    }

    @Override
    public void onClickVideoItem(News news, int currentPosition) {
        navigator.navigateToVideoNewsActivity(news, currentPosition);
    }

    @Override
    public void onVideoIsDorado() {
        // showDialogDorado();
    }

    @Override
    public void navigateToVideoNewsActivity(News news, int currentPosition) {
        navigator.navigateToVideoNewsActivity(news, currentPosition);
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
    public void navigateToGalleryActivity(News news) {
        navigator.navigateToGalleryActivity(news);
    }

    @Override
    public void showDialogDorado() {

    }

    @Override
    public void showShareApplause(final String id) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_share_applause, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();

        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_return);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_submit);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual("jugador", id);
                alertDialog.dismiss();
            }
        });
    }
}
