package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile;

import android.content.Intent;
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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.MonumentalItem;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.GalleryListActivity;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.adapters.MonumentalProfileAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.di.DaggerMProfileComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.di.MProfileModule;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfileContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.fragments.monumentalprofile.mvp.MProfilePresenter;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsDetailsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsVideoActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MProfileFragment extends BaseFragment implements MProfileContract.View, MonumentalProfileAdapter.OnItemClickListener {

    public static final String TAG = MProfileFragment.class.getSimpleName();
    @BindView(R.id.rv_PlayerNews)
    RecyclerView rvPlayerNews;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;

    @Inject
    MProfilePresenter presenter;

    private MonumentalProfileAdapter adapter;
    private LinearLayoutManager linearLayoutManager;

    String idMonumental;
    String idEncuesta;
    String imei;

    public static MProfileFragment newInstance(String monumental, String survey) {
        MProfileFragment fragment = new MProfileFragment();
        Bundle args = new Bundle();
        args.putString(Constant.Key.MONUMETAL_ID, monumental);
        args.putString(Constant.Key.SURVEY_ID, survey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        linearLayoutManager = new LinearLayoutManager(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_monumental_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        idMonumental = getArguments().getString(Constant.Key.MONUMETAL_ID);
        idEncuesta = getArguments().getString(Constant.Key.SURVEY_ID);
        initComponent();
        presenter.loadMonumental(idMonumental);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.loadMonumental(idMonumental);
            }
        });

        if (adapter == null) {
            presenter.loadMonumental(idMonumental);
        } else {
            adapter.setOnItemClickListener(this);
            rvPlayerNews.setAdapter(adapter);
            rvPlayerNews.setLayoutManager(linearLayoutManager);
        }

        return view;
    }

    public void initComponent() {
        DaggerMProfileComponent.builder()
                .appComponent(App.get().component())
                .mProfileModule(new MProfileModule(this))
                .build().inject(MProfileFragment.this);
    }

    private void initRvAndAdapter() {
        if (adapter == null) {
            adapter = new MonumentalProfileAdapter(this);
            adapter.setOnItemClickListener(this);
            rvPlayerNews.setAdapter(adapter);
            rvPlayerNews.setLayoutManager(linearLayoutManager);
        }
    }

    @Override
    public void showMonumental(MonumentalItem itemList) {
        initRvAndAdapter();
        adapter.setData(itemList);
        notifyDataSetChanged();
    }

    @Override
    public void updateVotes() {
        adapter.setVote();
        showToast("¡Votos agregados con éxito!", 2000);
    }

    @Override
    public void onFailed(String msg) {
        swipeContainer.setRefreshing(false);
        showToast("¡Ya has votado en esta encuesta!", 2000);
    }

    @Override
    public void navigateToVideoNewsActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsVideoActivity.class);
        intent.putExtra(Constant.Key.URL, news.getLink());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToInfografiaActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsInfografyActivity.class);
        intent.putExtra(Constant.Key.URL, news.getLink());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constant.Key.TITLE, news.getTitulo());
        intent.putExtra(Constant.Key.DESC_NEW, news.getDescripcion());
        intent.putExtra(Constant.Key.IMG, news.getFoto());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToGalleryActivity(News news) {
        Intent intent = new Intent(getActivity(), GalleryListActivity.class);
        intent.putExtra(Constant.Key.ID, news.getId());
        startActivity(intent);
    }

    @Override
    public void onClickItem(News news) {
        presenter.clickItem(news);
    }

    @Override
    public void onClickHeader() {

    }

    @Override
    public void onClickVote(MonumentalItem monumentalItem) {
        presenter.voteMonumental(idMonumental, idEncuesta, Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID));
    }

    private void notifyDataSetChanged() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancel();
    }
}