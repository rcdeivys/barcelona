package com.BarcelonaSC.BarcelonaApp.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.response.ConfigurationResponse;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.AuthActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Carlos on 01/11/2017.
 */

public class SplashActivity extends BaseActivity {


    @BindView(R.id.wv_juramento)
    CustomWebView wvJuramento;
    @BindView(R.id.rl_splash)
    RelativeLayout rlSplash;
    @BindView(R.id.rl_juramento)
    RelativeLayout rlJuramento;
    @BindView(R.id.btn_ok)
    Button btnOk;
    private PreferenceManager preferenceManager;
    ConfigurationManager configurationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        preferenceManager = new PreferenceManager(this);
        wvJuramento.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlJuramento());
        App.get().component().configurationApi().getConfiguration().enqueue(new NetworkCallBack<ConfigurationResponse>() {
            @Override
            public void onRequestSuccess(ConfigurationResponse response) {
                wvJuramento.loadUrl(response.getData().getUrlJuramento());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });

    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        preferenceManager.setBoolean(Constant.Key.FIRST_TIME_OATH, false);
        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
        finish();
    }
}
