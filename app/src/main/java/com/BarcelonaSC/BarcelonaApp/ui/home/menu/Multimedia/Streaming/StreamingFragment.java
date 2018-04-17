package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaStreamingData;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.di.DaggerStreamingComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.di.StreamingModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.mvp.StreamingContract;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Streaming.mvp.StreamingPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class StreamingFragment extends ShareBaseFragment implements StreamingContract.View {

    public static final String TAG = StreamingFragment.class.getSimpleName();
    Unbinder unbinder;
    private String type;

    @Inject
    StreamingPresenter presenter;

    @BindView(R.id.wv_streaming)
    WebView webView;

    public StreamingFragment() {

    }

//    public static StreamingFragment newInstance(String type) {
//        Bundle args = new Bundle();
//        args.putString(Constant.Key.TYPE, type);
//        StreamingFragment fragment = new StreamingFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multimedia_streaming, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.loadStreaming();
        
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        return view;
    }

    public void initComponent() {
        DaggerStreamingComponent.builder()
                .appComponent(App.get().component())
                .streamingModule(new StreamingModule(this, type))
                .build().inject(StreamingFragment.this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToastError(String error) {

    }

    @Override
    public void setVideos(List<MultimediaDataResponse> listVideos) {

    }

    @Override
    public void setStreaming(MultimediaStreamingData streamingData) {
        Log.e("DEIVYS: " , streamingData.getUrlEnvivo());
        webView.loadUrl(streamingData.getUrlEnvivo());
    }

    @Override
    public void share() {
        new ShareSection(App.getAppContext()).share(App.getAppContext(), ConfigurationManager.getInstance().getConfiguration().getTit42());
    }
}
