package com.BarcelonaSC.BarcelonaApp.ui.news;

import android.os.Bundle;
import android.support.v7.widget.AppCompatImageButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.ib_sub_header_share)
    ImageButton ibShare;

    private String url = "";

    private String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();

        Glide.with(this)
                .load(extras.getString(Constant.Key.IMG))
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(imgNews);

        imgNews.setAlpha((float) 1.0);

        try{
            id = extras.getString(Constant.Key.ID);
            url = extras.getString(Constant.Key.URL);
            Log.i("ERROR_URL"," ---> URL is not null: "+url);
        }catch (Exception e){
            Log.i("ERROR_URL"," ---> URL is null "+e.getMessage());
        }

        ibShare.setVisibility(View.VISIBLE);

        textTitle.setText(extras.getString(Constant.Key.TITLE));
        textDescription.setText(extras.getString(Constant.Key.DESC_NEW));

        textHeader.setText(ConfigurationManager.getInstance().getConfiguration().getTit2());

        super.initBanner(BannerView.Seccion.NEWS);
    }

    @OnClick(R.id.ib_sub_header_share)
    void onShareNews(){
        ShareSection.shareIndividual(Constant.Key.SHARE_NEWS, id);
    }

    @OnClick(R.id.btn_back)
    void onFinishActivity(){
        finish();
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
