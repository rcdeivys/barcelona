package com.BarcelonaSC.BarcelonaApp.ui.home.menu.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/13/17.
 */

public class LiveActivity extends BaseActivity {

    private String TAG = NewsInfografyActivity.class.getSimpleName();

    @BindView(R.id.infografy_webview)
    WebView webView;
    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;
    @BindView(R.id.btn_share)
    ImageButton btnShare;
    @BindView(R.id.text_header)
    FCMillonariosTextView textHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infografy);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlLivestream());

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textHeader.setText(ConfigurationManager.getInstance().getConfiguration().getTit9());

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        super.initBanner(BannerView.Seccion.LIVE);
    }

    @Override
    public void onBackPressed() {
        finish();
        webView.loadUrl("about:blank");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void share() {
        new ShareSection(App.getAppContext()).share(App.getAppContext(), "envivo");
    }

}
