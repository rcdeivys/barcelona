package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;

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

    public void setUrl(String url){
        if(url!=null){
            //Log.i("URLSETTED","---> "+url+" ultimo: "+url.substring(url.length()-1,url.length()));
            if(!url.substring(url.length()-1,url.length()).equals("/")){
                this.loadUrl(url + "/?idusuario=" +SessionManager.getInstance().getSession().getIdUser());
            }else{
                this.loadUrl(url + "?idusuario=" + SessionManager.getInstance().getSession().getIdUser());
            }
            //Log.i("URLSETTED","---> "+this.getUrl());
        }
    }
}
