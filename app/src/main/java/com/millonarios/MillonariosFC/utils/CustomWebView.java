package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Carlos on 01/11/2017.
 */

public class CustomWebView extends WebView {

    public CustomWebView(Context context) {
        super(context);
        initWebView();
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebView();
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebView();
    }

    private void initWebView() {
        this.setWebViewClient(new WebViewClient());
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setDomStorageEnabled(true);
    }
}
