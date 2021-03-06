package com.BarcelonaSC.BarcelonaApp.ui.home.menu.statistics;

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
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/5/17.
 */

public class StatisticsFragment extends Fragment {

    public static final String TAG = StatisticsFragment.class.getSimpleName();

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
                customWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlEstadisticas());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeContainer.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        customWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlEstadisticas());
    }

    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

}
