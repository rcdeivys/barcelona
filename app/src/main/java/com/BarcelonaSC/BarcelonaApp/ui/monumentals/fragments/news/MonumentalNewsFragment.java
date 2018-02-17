package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news;

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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.GalleryListActivity;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.di.DaggerMonumentalNewsComponent;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.di.MonumentalNewsModule;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp.MonumentalNewsContract;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.news.mvp.MonumentalNewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsDetailsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsVideoActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.adapters.NewsAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 16/02/2018.
 */

public class MonumentalNewsFragment extends BaseFragment implements MonumentalNewsContract.View, NewsAdapter.OnItemClickListener {

    public static final String TAG = MonumentalNewsFragment.class.getSimpleName();

    @BindView(R.id.btn_top)
    ImageButton btnTop;
    @BindView(R.id.news_swipe)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.news_rv)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    NewsAdapter newsAdapter;

    LinearLayoutManager mLayoutManager;

    @Inject
    public MonumentalNewsPresenter presenter;

    public static MonumentalNewsFragment getInstance() {
        return new MonumentalNewsFragment();
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
        ButterKnife.bind(this, view);
        presenter.onAttach(this);
        initRecyclerView();
        refresh();
        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.smoothScrollToPosition(0);
            }
        });
        return view;
    }

    public void initComponent() {
        /* DaggerMonumentalNewsComponent.builder()
                .appComponent(App.get().component())
                .MonumentalNewsModule(new MonumentalNewsModule(this))
                .build().inject(MonumentalNewsFragment.this); */
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        List<News> itemList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), itemList);
        newsAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                refresh();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    refresh(current_page);
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @Override
    public void onClickItem(News news) {
        presenter.onClickNewsItem(news);
    }

    private void refresh() {
        presenter.loadNews();
    }

    private void refresh(int page) {
        presenter.loadNewsPage(page);
    }

    public void updateNews(List<News> newsList) {
        if (newsAdapter != null) {
            newsAdapter.updateAll(newsList);
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMonumentalNews(List<News> news) {
        updateNews(news);
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
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToastError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}