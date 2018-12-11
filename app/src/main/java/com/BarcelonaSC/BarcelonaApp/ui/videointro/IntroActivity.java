package com.BarcelonaSC.BarcelonaApp.ui.videointro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.response.BannerResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.ConfigurationResponse;
import com.BarcelonaSC.BarcelonaApp.models.response.UserResponse;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.login.AuthActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.news_single.NewsSingleActivity;
import com.BarcelonaSC.BarcelonaApp.ui.splash.SplashActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.SinglePostActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.newrelic.agent.android.NewRelic;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends BaseActivity {

    private String TAG = IntroActivity.class.getSimpleName();

    /*@BindView(R.id.intro_video_view)
    VideoView introVideoView;
    @BindView(R.id.splash_video_view)
    VideoView splash;
    @BindView(R.id.btn_skip)
    Button btnSkip;*/

    private String introUrlPath;
    private String splashUrlPath;
    Handler handler;
    Runnable runnable;
    private PreferenceManager preferenceManager;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_video_intro);
        ButterKnife.bind(this);

        try {
            if (getIntent().hasExtra(Constant.Key.NOTIFICATION)) {
                App.get().registerTrackEvent(
                        Constant.Analytics.NOTIFICATION,
                        Constant.NotificationTags.Clicked,
                        getIntent().getStringExtra(Constant.Key.SECCION),
                        0
                );
            }
        } catch (Exception e){

        }

        preferenceManager = new PreferenceManager(this);
        App.get().component().bannerApi().getBanners().enqueue(new NetworkCallBack<BannerResponse>() {
            @Override
            public void onRequestSuccess(BannerResponse response) {
                SessionManager.getInstance().setBanners(response);
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });
        App.get().component().configurationApi().getConfiguration().enqueue(new NetworkCallBack<ConfigurationResponse>() {
            @Override
            public void onRequestSuccess(ConfigurationResponse response) {
                ConfigurationManager.getInstance().setConfiguration(response.getData());
            }

            @Override
            public void onRequestFail(String errorMessage, int errorCode) {

            }
        });

        NewRelic.withApplicationToken("AAa4eacc3d019a3dddb06ff07587dccb04bf122807").start(this.getApplication());

        if (SessionManager.getInstance().getSession() != null && SessionManager.getInstance().getSession().getToken() != null) {
            App.get().component().profileApi()
                    .get(SessionManager.getInstance().getSession().getToken())
                    .enqueue(new NetworkCallBack<UserResponse>() {
                        @Override
                        public void onRequestSuccess(UserResponse response) {
                            sessionManager.setUser(response.getData());
                            goToThisSection();
                        }

                        @Override
                        public void onRequestFail(String errorMessage, int errorCode) {
                            initSplash(false);
                        }
                    });
        } else {
            initSplash(false);
        }
    }

    public void initIntro() {
        initSplash(false);
    }

    public void initSplash(final boolean notification) {
        // Google analytics intro video screen tag
        App.get().registerTrackScreen(Constant.Analytics.INTRO_VIDEO);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateNext(notification);
            }
        }, 2000);
    }

    protected void goToThisSection(){
        if (getIntent() != null) {
            try {
                String seccion = getIntent().getStringExtra(Constant.Key.SECCION);
                String subseccion = getIntent().getStringExtra(Constant.Key.SUB_SECCION);
                String puntoReferencia = getIntent().getStringExtra(Constant.Key.PUNTO_REFERENCIA);
                String idPost = getIntent().getStringExtra(Constant.Seccion.Id_Post);
                Log.i(TAG, "goToThisSection:  === idPost: "+idPost);
                Log.i(TAG, "goToThisSection:  === seccion: "+seccion);
                Log.i(TAG, "goToThisSection:  === puntoReferencia: "+puntoReferencia);
                switch (seccion) {
                    case Constant.Seccion.MURO:
                        if (getIntent().getStringExtra(Constant.Seccion.Id_Post) != null) {
                            initSplash(true);
                        } else {
                            initSplash(false);
                        }
                        break;
                    case Constant.Seccion.NOTICIAS:
                        try {
                            if (idPost != null && !idPost.equals("noAplica")) {
                                Intent news = new Intent(IntroActivity.this, NewsSingleActivity.class);
                                news.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                news.putExtra(Constant.Seccion.Id_Post, idPost);
                                startActivity(news);
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "goToThisSection:  === Exception!!! ");
                            initSplash(false);
                            e.printStackTrace();
                        }
                        break;
                    case "noticias":
                        try {
                            if (idPost != null && !idPost.equals("noAplica")) {
                                Intent news = new Intent(IntroActivity.this, NewsSingleActivity.class);
                                news.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                news.putExtra(Constant.Seccion.Id_Post, idPost);
                                startActivity(news);
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "goToThisSection:  === Exception!!! ");
                            initSplash(false);
                            e.printStackTrace();
                        }
                        break;
                    case Constant.Seccion.CHAT:
                        initSplash(true);
                        break;
                    default:
                        Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                        home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        home.putExtra(Constant.Key.SECCION, seccion);
                        home.putExtra(Constant.Key.SUB_SECCION, subseccion);
                        home.putExtra(Constant.Key.PUNTO_REFERENCIA, puntoReferencia);
                        startActivity(home);
                        break;
                }
            } catch (Exception e) {
                initSplash(false);
            }
        }
    }

    public void navigateNext(boolean notification) {
        if (preferenceManager.getBoolean(Constant.Key.FIRST_TIME_OATH, true)) {
            Intent intent = new Intent(IntroActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            if (sessionManager.getSession() == null) {
                Intent intent = new Intent(IntroActivity.this, AuthActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                if (notification) {
                    try{
                        String goToThisSection = getIntent().getStringExtra(Constant.Key.SECCION);
                        switch (goToThisSection){
                            case Constant.Seccion.MURO:
                                if (
                                        getIntent().hasExtra(Constant.Seccion.Id_Post) &&
                                                !getIntent().getStringExtra(Constant.Seccion.Id_Post).equals("noAplica")
                                        ) {
                                    Intent intent = new Intent(IntroActivity.this, SinglePostActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(Constant.Seccion.Id_Post, getIntent().getStringExtra(Constant.Seccion.Id_Post));
                                    startActivityForResult(intent, 2000);
                                }
                                break;
                            case Constant.Seccion.CHAT:
                                if (getIntent().getStringExtra(ChatActivity.TAG_PRIVATE) != null) {
                                    Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                                    intent.putExtra(ChatActivity.TAG_PRIVATE, getIntent().getStringExtra(ChatActivity.TAG_PRIVATE));
                                    startActivityForResult(intent, 2000);
                                } else if (getIntent().getStringExtra(ChatActivity.TAG_GROUP) != null) {
                                    Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                                    intent.putExtra(ChatActivity.TAG_GROUP, getIntent().getStringExtra(ChatActivity.TAG_GROUP));
                                    startActivityForResult(intent, 2000);
                                } else {
                                    goDefault();
                                }
                                break;

                            default:
                                goDefault();
                                break;
                        }
                    }catch (Exception e){

                    }
                } else {
                    goDefault();
                }
                finish();
            }
        }
    }

    private void goDefault(){
        if (getIntent().hasExtra(Constant.Key.SECCION)) {
            if(getIntent().hasExtra(Constant.Key.SUB_SECCION)){
                Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                home.putExtra(Constant.Key.SUB_SECCION, getIntent().getStringExtra(Constant.Key.SUB_SECCION));
                startActivity(home);
            }else{
                Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                home.putExtra(Constant.Key.PUNTO_REFERENCIA, getIntent().getStringExtra(Constant.Key.PUNTO_REFERENCIA));
                startActivity(home);
            }
        } else {
            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
