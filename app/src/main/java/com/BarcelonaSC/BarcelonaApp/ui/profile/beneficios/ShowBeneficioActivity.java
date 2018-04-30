package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ShowBeneficioActivity extends BaseActivity {

    public static String SET_IMAGE = "image";
    public static String SET_DESCRIPCION = "descripcion";
    public static String SET_TIPO = "tipo";
    public static String SET_LINK = "link";
    public static String SET_TITULO = "titulo";

    @BindView(R.id.scroll_view)
    ScrollView sv;

    @BindView(R.id.iv_beneficio)
    ImageView imgBeneficio;

    @BindView(R.id.tv_title_beneficio)
    TextView textTitle;

    @BindView(R.id.ib_return)
    ImageButton ibReturn;

    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    @BindView(R.id.wv_beneficios)
    WebView webView;

    @BindView(R.id.content_wv_beneficios)
    RelativeLayout contentWebView;

    @BindView(R.id.content_iv_beneficios)
    RelativeLayout contentImageView;

    Unbinder unbinder;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_beneficio);
        unbinder = ButterKnife.bind(this);

        if (getIntent().getStringExtra(SET_TIPO).equals("Normal")) {
            try {
                Glide.with(getBaseContext())
                        .load(getIntent().getStringExtra(SET_IMAGE))
                        .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                        .into(imgBeneficio);
                String mensaje = getIntent().getStringExtra(SET_DESCRIPCION);
                textTitle.setText(mensaje);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvSubHeaderTitle.setText("BENEFICIOS DORADOS");
            ibReturn.setVisibility(View.VISIBLE);
            contentImageView.setVisibility(View.VISIBLE);
            textTitle.setVisibility(View.VISIBLE);
        } else if (getIntent().getStringExtra(SET_TIPO).equals("Infografia")) {
            sv.setVisibility(View.GONE);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            String link = getIntent().getStringExtra(SET_LINK);
            webView.loadUrl(link);
            String mensaje = getIntent().getStringExtra(SET_DESCRIPCION);
            textTitle.setText(mensaje);
            tvSubHeaderTitle.setText("BENEFICIOS DORADOS");
            ibReturn.setVisibility(View.VISIBLE);
            contentWebView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.ib_return)
    public void onViewClicked() {
        finish();
    }
}