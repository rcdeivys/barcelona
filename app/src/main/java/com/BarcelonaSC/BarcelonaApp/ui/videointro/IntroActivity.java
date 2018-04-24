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

        //introUrlPath = "android.resource://" + getPackageName() + "/" + R.raw.video_intro;
        //splashUrlPath = "android.resource://" + getPackageName() + "/" + R.raw.splash_2;

        /*btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introVideoView.stopPlayback();
                navigateNext();
            }
        });*/

        if (SessionManager.getInstance().getSession() != null && SessionManager.getInstance().getSession().getToken() != null) {
            App.get().component().profileApi()
                    .get(SessionManager.getInstance().getSession().getToken())
                    .enqueue(new NetworkCallBack<UserResponse>() {
                        @Override
                        public void onRequestSuccess(UserResponse response) {
                            sessionManager.setUser(response.getData());
                            if (getIntent() != null) {
                                if (getIntent().hasExtra(Constant.Key.SECCION) && getIntent().hasExtra(Constant.Seccion.Id_Post)) {
                                    Log.d(TAG + "erick: ", getIntent().getStringExtra(Constant.Key.SECCION));
                                    if (getIntent().hasExtra(Constant.Key.SECCION)
                                            && getIntent().getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.MURO)) {
                                        if (getIntent().getStringExtra(Constant.Seccion.Id_Post) != null) {
                                            initSplash(true);
                                        } else {
                                            initSplash(false);
                                        }
                                    } else if (getIntent().hasExtra(Constant.Key.SECCION)
                                            && getIntent().getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.NOTICIAS)
                                            && getIntent().getStringExtra(Constant.Seccion.Id_Post) != null
                                            && !getIntent().getStringExtra(Constant.Seccion.Id_Post).equals("noAplica")) {
                                        Intent news = new Intent(IntroActivity.this, NewsSingleActivity.class);
                                        news.putExtra(Constant.Seccion.Id_Post, getIntent().getStringExtra(Constant.Seccion.Id_Post));
                                        startActivity(news);
                                    } else {
                                        initSplash(false);
                                    }
                                } else if (getIntent().hasExtra(Constant.Key.SECCION)) {
                                    if (getIntent().getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.CHAT)) {
                                        initSplash(true);

                                    } else {
                                        initSplash(false);
                                    }
                                } else {
                                    initSplash(false);
                                }
                            } else {
                                Log.d(TAG + "erick: ", getIntent().getStringExtra(Constant.Key.SECCION));
                                if (getIntent().hasExtra(Constant.Key.SECCION)) {

                                    Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                                    home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                                    startActivity(home);
                                }
                            }
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
        /*btnSkip.setVisibility(View.GONE);
        introVideoView.setVisibility(View.GONE);
        splash.setVideoURI(Uri.parse(splashUrlPath));
        splash.start();

        splash.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                navigateNext();
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                handler.removeCallbacks(runnable);
                if (getActivity() == null)
                    return;
                splash.stopPlayback();
                navigateNext();

            }

        };
        handler = new Handler();
        handler.postDelayed(runnable, 2000);*/

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateNext(notification);
            }
        }, 3000);

    }

    public void navigateNext(boolean notification) {
        Log.i(TAG, "PRUEBAAA FUNCIONA: 1");
        if (preferenceManager.getBoolean(Constant.Key.FIRST_TIME_OATH, true)) {
            startActivity(new Intent(IntroActivity.this, SplashActivity.class));
            finish();
        } else {
            if (sessionManager.getSession() == null) {
                startActivity(new Intent(IntroActivity.this, AuthActivity.class));
                finish();
            } else {
                Log.i(TAG, "PRUEBAAA FUNCIONA: 2");
                if (notification) {
                    Log.i(TAG, "PRUEBAAA FUNCIONA: 3");
                    if (getIntent().getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.MURO)) {
                        Log.i(TAG, "PRUEBAAA FUNCIONA: 4");
                        Intent intent = new Intent(IntroActivity.this, SinglePostActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra(Constant.Seccion.Id_Post, getIntent().getStringExtra(Constant.Seccion.Id_Post));
                        startActivityForResult(intent, 2000);
                    } else if (getIntent().getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.CHAT)) {
                        Log.i(TAG, "PRUEBAAA FUNCIONA: 5");
                        if (getIntent().getStringExtra(ChatActivity.TAG_PRIVATE) != null) {
                            Log.i(TAG, "PRUEBAAA FUNCIONA: 6");
                            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                            intent.putExtra(ChatActivity.TAG_PRIVATE, getIntent().getStringExtra(ChatActivity.TAG_PRIVATE));
                            startActivityForResult(intent, 2000);
                        } else if (getIntent().getStringExtra(ChatActivity.TAG_GROUP) != null) {
                            Log.i(TAG, "PRUEBAAA FUNCIONA: 7");
                            Intent intent = new Intent(IntroActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                            intent.putExtra(ChatActivity.TAG_GROUP, getIntent().getStringExtra(ChatActivity.TAG_GROUP));
                            startActivityForResult(intent, 2000);
                        } else {
                            Log.i(TAG, "PRUEBAAA FUNCIONA: 8");
                            if (getIntent().hasExtra(Constant.Key.SECCION)) {
                                Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                                home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                                startActivity(home);
                            } else {
                                startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                            }
                        }
                    } else {
                        if (getIntent().hasExtra(Constant.Key.SECCION)) {
                            Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                            home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                            startActivity(home);
                        } else {
                            startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                        }
                    }
                } else {
                    if (getIntent().hasExtra(Constant.Key.SECCION)) {
                        Intent home = new Intent(IntroActivity.this, HomeActivity.class);
                        home.putExtra(Constant.Key.SECCION, getIntent().getStringExtra(Constant.Key.SECCION));
                        startActivity(home);
                    } else {
                        startActivity(new Intent(IntroActivity.this, HomeActivity.class));
                    }
                }
                finish();
            }
        }
    }

    /*public void initVideoIntro() {
        btnSkip.setVisibility(View.VISIBLE);
        introVideoView.setVideoURI(Uri.parse(introUrlPath));
        introVideoView.start();

        introVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                initSplash();
            }
        });
    }*/

    @Override
    protected void onResume() {
        super.onResume();

    }

}