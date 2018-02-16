package com.BarcelonaSC.BarcelonaApp.ui.news;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Actividad correspondiente al detalle de las noticias
 */
public class NewsDetailsActivity extends BaseActivity {

    private String TAG = NewsDetailsActivity.class.getSimpleName();

    @BindView(R.id.img_news)
    ImageView imgNews;
    @BindView(R.id.text_title)
    FCMillonariosTextView textTitle;
    @BindView(R.id.text_description)
    FCMillonariosTextView textDescription;
    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;
    @BindView(R.id.text_header)
    FCMillonariosTextView textHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        Glide.with(this)
                .load(extras.getString(Constant.Key.IMG))
                .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                .into(imgNews);

        imgNews.setAlpha((float) 1.0);

        textTitle.setText(extras.getString(Constant.Key.TITLE));
        textDescription.setText(extras.getString(Constant.Key.DESC_NEW));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textHeader.setText(ConfigurationManager.getInstance().getConfiguration().getTit2());

        super.initBanner(BannerView.Seccion.NEWS);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
