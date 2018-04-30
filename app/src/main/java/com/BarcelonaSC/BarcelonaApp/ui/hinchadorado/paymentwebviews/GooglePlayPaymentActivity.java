package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.paymentwebviews;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GooglePlayPaymentActivity extends BaseActivity {

    @BindView(R.id.wv_general)
    CustomWebView customWebView;
    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_play_payment);

        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            customWebView.setUrl("la Dirección url del servicio de pago de google play");
            tvSubHeaderTitle.setText((getIntent().getExtras().getString(Constant.Key.TITLE, "")));
        }
        ibReturn.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.ib_return)
    public void onViewClicked() {
        finish();
    }

}

