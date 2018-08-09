package com.BarcelonaSC.BarcelonaApp.ui.news;

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
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.SingleCalendarListFragment;
import com.BarcelonaSC.BarcelonaApp.ui.news.di.DaggerNewsComponent;
import com.BarcelonaSC.BarcelonaApp.ui.news.di.NewsModule;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsContract;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.adapters.NewsAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/31/17.
 */

public class NewsFragment extends BaseFragment implements NewsContract.View, NewsAdapter.OnItemClickListener {

    public final static String TAG = NewsFragment.class.getSimpleName();

    private static final String CATEGORY = "category";
    public static final String NEWS_PROFESSIONAL = "news_professional";
    public static final String NEWS_FOOTBALL_BASE = "news_football_base";

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

    News news;

    List<Integer> videoPosition;

    @Inject
    public NewsPresenter presenter;

    //ItemsProvider itemProvider;

    public static NewsFragment getInstance(String category) {
        NewsFragment fragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CATEGORY, category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPosition = new ArrayList<>();
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
        DaggerNewsComponent.builder()
                .appComponent(App.get().component())
                .newsModule(new NewsModule(this))
                .build().inject(NewsFragment.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        List<Object> itemList = new ArrayList<>();
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
        recyclerView.smoothScrollToPosition(0);
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
        presenter.onclickNewsItem(news);
    }

    @Override
    public void onVideoClick(News news, int currentVideo) {
        navigateToVideoNewsActivity(news, currentVideo);
    }

    @Override
    public void playVideo(int position, int id) {
        App.get().registerCustomTrackScreen(Constant.Analytics.NEWS + "." + Constant.NewsTags.Video, Integer.toString(id), 1);
        videoPosition.add(position);
    }

    @Override
    public void onCalendarClick(String id) {
        SingleCalendarListFragment singleCalendarListFragment = new SingleCalendarListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idPartido", Integer.valueOf(id));
        bundle.putString("type", Constant.Key.CUP);
        bundle.putString(Constant.Menu.NEWS, Constant.Menu.NEWS);
        singleCalendarListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, singleCalendarListFragment, SingleCalendarListFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onVideoIsDorado() {
        //  showDialogDorado();
    }

    private void refresh() {
        if (getArguments().getString(CATEGORY).equals(NEWS_PROFESSIONAL)) {
            presenter.loadNews(NEWS_PROFESSIONAL);
        } else {
            presenter.loadNews(NewsFragment.NEWS_FOOTBALL_BASE);
        }
    }

    private void refresh(int page) {
        if (getArguments().getString(CATEGORY).equals(NEWS_PROFESSIONAL)) {
            presenter.loadNewsPage(page, NEWS_PROFESSIONAL);
        } else {
            presenter.loadNewsPage(page, NewsFragment.NEWS_FOOTBALL_BASE);
        }
    }

    public void updateNews(List<Object> newsList) {
        if (newsAdapter != null) {
            newsAdapter.updateAll(newsList);
            swipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setNewsProfessional(List<Object> news) {
        updateNews(news);
    }

    @Override
    public void setNewsFootballBase(List<Object> news) {
        updateNews(news);
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

    @Override
    public void onPause() {
        super.onPause();
        newsAdapter.pauseVideo(videoPosition);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
