package com.BarcelonaSC.BarcelonaApp.ui.register.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/20/17.
 */

public class TermsAndConditionsDialog extends DialogFragment {

    @BindView(R.id.accept)
    Button btnBack;
    @BindView(R.id.webView)
    WebView myWebView;


    public static TermsAndConditionsDialog newInstance() {
        TermsAndConditionsDialog f = new TermsAndConditionsDialog();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_terms_conditions);
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
        myWebView.loadUrl(Commons.getString(R.string.url_api).replace("api/", "") + "webviews/tyc/index.php");

        return dialogo;
    }
}
