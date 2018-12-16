package com.BarcelonaSC.BarcelonaApp.utils;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.BannerData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Carlos on 06/12/2017.
 */

public class BannerView extends AppCompatImageView {

    List<BannerData> bannersData;

    public BannerView(Context context) {
        super(context);
        Glide.with(App.getAppContext()).load("").apply(new RequestOptions().placeholder(R.drawable.footer).error(R.drawable.footer).centerCrop()).into(this);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Glide.with(App.getAppContext()).load("").apply(new RequestOptions().placeholder(R.drawable.footer).error(R.drawable.footer).centerCrop()).into(this);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Glide.with(App.getAppContext()).load("").apply(new RequestOptions().placeholder(R.drawable.footer).error(R.drawable.footer).centerCrop()).into(this);
    }


    public void putBannerData(Seccion seccion, final BannerListener bannerListener) {
        if (bannersData == null || bannersData.size() == 0) {
            bannersData = SessionManager.getInstance().getBanners();
        }

        for (final BannerData bannerData : bannersData) {
            if (bannerData.getSeccion().equals(seccion.getValue())) {
                Log.e("DEIVYS : " , bannerData.toString());
                this.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bannerData.getSeccionDestino() != null) {
                            bannerListener.onClickBannerSeccionListener(bannerData.getSeccionDestino(), bannerData.getPartido(), bannerData.getSeccion(), bannerData.getTitulo());
                        } else {
                            switch (bannerData.getTarget()) {
                                case "Externo":
                                    bannerListener.onClickBannerExternoListener(bannerData.getUrl(), bannerData.getSeccion(), bannerData.getTitulo());
                                    break;
                                case "Interno":
                                    bannerListener.onClickBannerInternoListener(bannerData.getUrl(), bannerData.getSeccion(), bannerData.getTitulo());
                                    break;
                                case "Seccion":
                                    if(bannerData.getType().equals("Partido")) {
                                        bannerListener.onClickBannerSeccionListener("calendar", bannerData.getPartido(), bannerData.getSeccion(), bannerData.getTitulo());
                                    }else{
                                        bannerListener.onClickBannerSeccionListener(bannerData.getSeccionDestino(), bannerData.getPartido(), bannerData.getSeccion(), bannerData.getTitulo());
                                    }                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                });
                Glide.with(App.getAppContext()).load(bannerData.getFoto()).apply(new RequestOptions().placeholder(R.drawable.footer).error(R.drawable.footer).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).into(this);
                break;
            }
        }

    }

    public interface BannerListener {
        void onClickBannerInternoListener(String url, String section, String title);

        void onClickBannerExternoListener(String url, String section, String title);

        void onClickBannerSeccionListener(String seccionDestino, String bannerData, String section, String title);
    }

    public enum Seccion {
        TOP("top"), BOTTOM("bottom"), SETINGS("setting"), PROFILE("profile"),
        NEWS("news"), CALENDAR("calendar"),
        TABLE("table"), STATISTICS("statistics"),
        TEAM("team"), LINE_UP("line_up"), VIRTUAL_REALITY("virtual_reality"),
        FOOTBALL_BASE("football_base"), STORE("store"), ACADEMY("academy"),
        LIVE("live"), WALL_AND_CHAT("wall_and_chat"), WALL("muro"), CHAT("chat"), GAMES("games"), YOU_CHOOSE("you_choose"),
        MONUMENTAL("monumental"), MAP("geolocalizacion"),MULTIMEDIA("multimedia"),GLORIAS("glory_bsc");

        private final String id;

        Seccion(String id) {
            this.id = id;
        }

        public String getValue() {
            return id;
        }
    }

}