package com.millonarios.MillonariosFC.commons;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.utils.CustomWebView;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Carlos on 06/12/2017.
 */

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.wv_general)
    CustomWebView wvGeneral;
    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        if (getIntent().getExtras() != null) {
            wvGeneral.loadUrl(ConfigurationManager.getInstance().getConfiguration().getUrlJuramento());
            wvGeneral.loadUrl(getIntent().getExtras().getString(Constant.Key.URL, ""));
            tvSubHeaderTitle.setText((getIntent().getExtras().getString(Constant.Key.TITLE, "")));
        }
        ibReturn.setVisibility(View.VISIBLE);

    }

    @OnClick(R.id.ib_return)
    public void onViewClicked() {
        finish();
    }

}
