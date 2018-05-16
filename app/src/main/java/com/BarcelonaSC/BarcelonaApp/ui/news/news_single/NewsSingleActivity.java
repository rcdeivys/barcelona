package com.BarcelonaSC.BarcelonaApp.ui.news.news_single;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.NewsSingleData;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.di.DaggerNewsSingleComponent;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.di.NewsSingleModule;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp.NewsSingleContract;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.mvp.NewsSinglePresenter;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by RYA-Laptop on 22/03/2018.
 */

public class NewsSingleActivity extends BaseActivity implements NewsSingleContract.View {

    public static String TAG = NewsSingleActivity.class.getSimpleName();

    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;
    @BindView(R.id.text_header)
    FCMillonariosTextView textHeader;
    @BindView(R.id.normal_new)
    LinearLayout normalLin;
    @BindView(R.id.img_news)
    ImageView imgNew;
    @BindView(R.id.text_title)
    FCMillonariosTextView txtTitle;
    @BindView(R.id.text_description)
    FCMillonariosTextView txtDescrip;
    @BindView(R.id.video_new)
    LinearLayout videoLin;
    @BindView(R.id.video_news)
    CustomVideoView videoNew;
    @BindView(R.id.infografy_new)
    LinearLayout infografyLin;
    @BindView(R.id.infografy_webview)
    WebView infografyNew;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Inject
    NewsSinglePresenter presenter;

    String id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_single);
        ButterKnife.bind(this);

        if (getIntent() != null && getIntent().hasExtra(Constant.Seccion.Id_Post)) {
            id = getIntent().getStringExtra(Constant.Seccion.Id_Post);
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewsSingleActivity.this, HomeActivity.class));
            }
        });

        super.initBanner(BannerView.Seccion.NEWS);
        initComponent();

        if (id != null) {
            showProgress();
            presenter.getNewsSingle(id);
        }
    }

    public void initComponent() {
        DaggerNewsSingleComponent.builder()
                .appComponent(App.get().component())
                .newsSingleModule(new NewsSingleModule(this))
                .build().inject(NewsSingleActivity.this);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        hideProgress();
    }

    @Override
    public void setNewsSingle(List<NewsSingleData> noticia) {
        if (noticia.get(0).getTipo().matches(Constant.NewsType.VIDEO)) {
            setVideoNew(noticia.get(0));
        } else if (noticia.get(0).getTipo().matches(Constant.NewsType.INFOGRAFY) || noticia.get(0).getTipo().matches(Constant.NewsType.STAT)) {
            setInfografyNew(noticia.get(0));
        } else {
            setNormalNew(noticia.get(0));
        }
        hideProgress();
    }

    private void setNormalNew(NewsSingleData data) {
        normalLin.setVisibility(View.VISIBLE);
        if (data.getFoto() != null) {
            Glide.with(this)
                    .load(data.getFoto())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bsc_news_wm)
                            .error(R.drawable.bsc_news_wm))
                    .into(imgNew);
        }
        txtTitle.setText(data.getTitulo());
        txtDescrip.setText(data.getDescripcion());
    }

    private void setVideoNew(NewsSingleData data) {
        videoLin.setVisibility(View.VISIBLE);

        if (data.getLink() != null) {
            videoNew.setVideoUrl(data.getLink(), getIntent().getIntExtra(Constant.Video.CURRENT_POSITION, 0), false);
            videoNew.start();
            videoNew.setImage(getBaseContext(), data.getFoto());
            videoNew.setButtonFullScreen(R.drawable.ic_media_fullscreen_exit, Commons.getColor(R.color.white), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "No se puede reproducir el vídeo", Toast.LENGTH_SHORT).show();
        }
    }

    private void setInfografyNew(NewsSingleData data) {
        infografyLin.setVisibility(View.VISIBLE);

        WebSettings settings = infografyNew.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        infografyNew.setWebViewClient(new WebViewClient());
        if (data.getLink() != null) {
            infografyNew.loadUrl(data.getLink());
        } else {
            Toast.makeText(this, "No se puede mostrar la información", Toast.LENGTH_SHORT).show();
        }
    }

}