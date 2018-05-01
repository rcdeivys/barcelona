package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video;

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
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaStreamingData;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.di.DaggerVideoComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.di.VideoModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp.VideoContract;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video.mvp.VideoPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class VideoFragment extends BaseFragment implements VideoContract.View, VideoAdapter.OnItemClickListener {

    public static final String TAG = VideoFragment.class.getSimpleName();
    private String type;

    @BindView(R.id.rv_multimedia)
    RecyclerView rvMultimedia;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    Unbinder unbinder;

    List<Integer> videoPosition;

    private LinearLayoutManager linearLayoutManager;
    private VideoAdapter videoAdapter;

    @Inject
    VideoPresenter presenter;

    public VideoFragment() {

    }

    public static VideoFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        VideoFragment fragment = new VideoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(Constant.Key.TYPE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        videoPosition = new ArrayList<>();
        initComponent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multimedia_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });

        if (videoAdapter == null) {
            showProgress();
            getData();
        } else {
            rvMultimedia.setAdapter(videoAdapter);
            rvMultimedia.setLayoutManager(linearLayoutManager);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();
    }

    public void initComponent() {
        DaggerVideoComponent.builder()
                .appComponent(App.get().component())
                .videoModule(new VideoModule(this, type))
                .build().inject(VideoFragment.this);
    }

    private void initRvAndAdapter() {
        if (videoAdapter == null) {
            videoAdapter = new VideoAdapter(this);
            rvMultimedia.setAdapter(videoAdapter);
            rvMultimedia.setLayoutManager(linearLayoutManager);
        }
    }

    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    private void notifyDataSetChanged() {
        videoAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }

    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToastError(String error) {
        showToast(error, Toast.LENGTH_SHORT);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setVideos(List<MultimediaDataResponse> listVideos) {
        initRvAndAdapter();
        videoAdapter.setData(listVideos);
        notifyDataSetChanged();
    }

    @Override
    public void setStreaming(MultimediaStreamingData streamingData) {
    }

    public void getData() {
        if (type.equals(Constant.Key.MULTIMEDIA_VIDEO)) {
            presenter.loadVideos();
        } else if (type.equals(Constant.Key.MULTIMEDIA_ONLINE)) {
            presenter.loadStreaming();
        }
    }

    @Override
    public void onClickItem(int position) {

    }

    @Override
    public void onVideoClick(MultimediaDataResponse multimediaDataResponse, int currentVideo) {
        navigateToVideoActivity(multimediaDataResponse, currentVideo);
    }

    public void navigateToVideoActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition) {
        navigator.navigateToVideoMultimediaActivity(multimediaDataResponse, currentPosition);
    }

    @Override
    public void onVideoShare(String id) {
        ShareSection.shareIndividual("video", id);
    }


    @Override
    public void playVideo(int position) {
        if (!videoPosition.contains(position))
            videoPosition.add(position);
    }

    @Override
    public void onPause() {
        super.onPause();
        videoAdapter.pauseVideo(videoPosition);
        videoPosition.clear();
    }
}
