package com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.models.ApplauseData;
import com.millonarios.MillonariosFC.models.ChooseProfileData;
import com.millonarios.MillonariosFC.models.News;
import com.millonarios.MillonariosFC.models.PlayerData;
import com.millonarios.MillonariosFC.ui.playerdetails.PlayerProfile.di.DaggerPlayerProfileComponent;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.di.DaggerChooseProfileComponent;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfileActivity;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.di.ChooseProfileModule;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp.ChooseProfileContract;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.mvp.ChooseProfilePresenter;
import com.millonarios.MillonariosFC.utils.Constants.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 13/10/2017.
 */

public class ChooseProfileFragment extends BaseFragment
        implements ChooseProfileContract.View, ChooseProfileAdapter.OnItemClickListener {

    public static final String TAG = ChooseProfileFragment.class.getSimpleName();
    @BindView(R.id.rv_PlayerNews)
    RecyclerView rvPlayerNews;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @Inject
    ChooseProfilePresenter presenter;

    private ChooseProfileAdapter playerProfileAdapter;
    private LinearLayoutManager linearLayoutManager;
    String type;
    boolean showVotes;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    public static ChooseProfileFragment newInstance(int profileId, boolean showVotes) {

        Bundle args = new Bundle();
        args.putInt(Constant.Key.ID_RESPUESTA, profileId);
        args.putBoolean(Constant.Key.SHOW_VOTES, showVotes);

        ChooseProfileFragment fragment = new ChooseProfileFragment();

        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);
        final int playerId = getArguments().getInt(Constant.Key.ID_RESPUESTA);
        showVotes = getArguments().getBoolean(Constant.Key.SHOW_VOTES);
        Log.i("TAG", "/--->" + playerId);
        presenter.getChooseProfileData(String.valueOf(playerId));

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);

                presenter.getChooseProfileData(String.valueOf(playerId));

            }
        });

        if (playerProfileAdapter == null) {
            showProgress();

            presenter.getChooseProfileData(String.valueOf(playerId));

        } else {
            rvPlayerNews.setAdapter(playerProfileAdapter);
            rvPlayerNews.setLayoutManager(linearLayoutManager);
        }

        return view;
    }

    public void initComponent() {
        DaggerChooseProfileComponent.builder()
                .appComponent(App.get().component())
                .chooseProfileModule(new ChooseProfileModule(this))
                .build().inject(ChooseProfileFragment.this);
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
    public void setChooseProfileData(ChooseProfileData chooseProfileData) {
        initRvAndAdapter();
        playerProfileAdapter.setData(chooseProfileData, showVotes);
        notifyDataSetChanged();
    }


    private void initRvAndAdapter() {
        if (playerProfileAdapter == null) {
            playerProfileAdapter = new ChooseProfileAdapter(this, type);
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
