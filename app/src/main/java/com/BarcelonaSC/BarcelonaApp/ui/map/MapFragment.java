package com.BarcelonaSC.BarcelonaApp.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.shop.VirtualShopFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 15/02/2018.
 */

public class MapFragment extends BaseFragment {

    public static final String TAG = MapFragment.class.getSimpleName();

    @BindView(R.id.webView)
    CustomWebView customWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());

        customWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlAcademia());
    }

}