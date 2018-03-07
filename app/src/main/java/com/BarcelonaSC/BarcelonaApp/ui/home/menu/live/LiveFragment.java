package com.BarcelonaSC.BarcelonaApp.ui.home.menu.live;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/13/17.
 */

public class LiveFragment extends Fragment {

    public static String TAG = LiveFragment.class.getSimpleName();

    @BindView(R.id.webView)
    CustomWebView customWebView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                customWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlLivestream());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        customWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlLivestream());
    }

    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

}
