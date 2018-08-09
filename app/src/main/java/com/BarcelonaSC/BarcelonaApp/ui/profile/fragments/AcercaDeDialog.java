package com.BarcelonaSC.BarcelonaApp.ui.profile.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/20/17.
 */

public class AcercaDeDialog extends DialogFragment {

    private String TAG = AcercaDeDialog.class.getSimpleName();

    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;
    @BindView(R.id.webView)
    WebView myWebView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_acerca_de);
        ButterKnife.bind(this, dialogo);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogo.dismiss();
            }
        });

        WebSettings settings = myWebView.getSettings();
        myWebView.getSettings().setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);

        myWebView.setWebViewClient(new WebViewClient());
        myWebView.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrl_acercade());

        return dialogo;
    }
}
