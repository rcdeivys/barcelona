package com.BarcelonaSC.BarcelonaApp.ui.virtualreality;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import com.google.vr.sdk.widgets.video.VrVideoView;

import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 07-08-2017.
 */


public class VirtualActivity extends BaseActivity {

    private static final String STATE_IS_PAUSED = "isPaused";
    private static final String STATE_VIDEO_DURATION = "videoDuration";
    private static final String STATE_PROGRESS_TIME = "progressTime";

    private VrVideoView videoWidgetView;

    private SeekBar seekBar;
    private TextView statusText;

    private boolean isPaused = false;

    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.ib_sub_header_share)
    ImageButton ibShare;
    @BindView(R.id.content_footer)
    RelativeLayout contentFooter;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView subHeaderTitle;

    private VideoReality virtualRealityItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_virtual);
        ButterKnife.bind(this);

        initSubToolBar(ConfigurationManager.getInstance().getConfiguration().getTit8());
        ibReturn.setVisibility(View.VISIBLE);
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        if (getIntent().getSerializableExtra(Constant.Key.VIRTUAL_REALITY_SECTION) != null) {
            virtualRealityItem = (VideoReality) getIntent().getSerializableExtra(Constant.Key.VIRTUAL_REALITY_SECTION);


        }


        Uri uri = Uri.parse((getIntent().getStringExtra("url")));
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        statusText = (TextView) findViewById(R.id.status_text);
        videoWidgetView = (VrVideoView) findViewById(R.id.video_view);
        Typeface helvetica_condensed = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Helvetica-Condensed/Helvetica-Condensed-Bold_0.otf");

        TextView text = (TextView) findViewById(R.id.texto);
        text.setTypeface(helvetica_condensed);


        VrVideoView.Options options = new VrVideoView.Options();
        options.inputFormat = VrVideoView.Options.TYPE_MONO;
        //options.inputType = VrVideoView.Options.TYPE_STEREO_OVER_UNDER;
        try {
            videoWidgetView.loadVideo(uri, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (savedInstanceState != null) {
            long progressTime = savedInstanceState.getLong(STATE_PROGRESS_TIME);
            videoWidgetView.seekTo(progressTime);
            seekBar.setMax((int) savedInstanceState.getLong(STATE_VIDEO_DURATION));
            seekBar.setProgress((int) progressTime);

            isPaused = savedInstanceState.getBoolean(STATE_IS_PAUSED);
            if (isPaused) {
                videoWidgetView.pauseVideo();
            }
        } else {
            seekBar.setEnabled(false);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                // if the user changed the position, seek to the new position.
                if (fromUser) {
                    videoWidgetView.seekTo(progress);
                    updateStatusText();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // ignore for now.
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // ignore for now.
            }
        });

        // initialize the video listener
        videoWidgetView.setEventListener(new VrVideoEventListener() {

            @Override
            public void onLoadSuccess() {
                // Log.i(TAG, "Successfully loaded video " + videoWidgetView.getDuration());
                seekBar.setMax((int) videoWidgetView.getDuration());
                seekBar.setEnabled(true);
                updateStatusText();
            }


            @Override
            public void onLoadError(String errorMessage) {
                Toast.makeText(
                        getApplicationContext(), "Tu dispositivo no es compatible para reproducir este vídeo", Toast.LENGTH_LONG)
                        .show();
                // Log.e(TAG, "Error loading video: " + errorMessage);
            }

            @Override
            public void onClick() {
                if (isPaused) {
                    videoWidgetView.playVideo();
                } else {
                    videoWidgetView.pauseVideo();
                }

                isPaused = !isPaused;
                updateStatusText();
            }


            @Override
            public void onNewFrame() {
                updateStatusText();
                seekBar.setProgress((int) videoWidgetView.getCurrentPosition());
            }


            @Override
            public void onCompletion() {
                videoWidgetView.seekTo(0);
            }

        });
        super.initBanner(BannerView.Seccion.VIRTUAL_REALITY);
    }

    public void initSubToolBar(String name) {
        subHeaderTitle.setText(name);
        ibShare.setVisibility(View.INVISIBLE);
    }


    private void updateStatusText() {
        String status = (isPaused ? "Paused: " : "Playing: ") +
                String.format(Locale.getDefault(), "%.2f", videoWidgetView.getCurrentPosition() / 1000f) +
                " / " +
                videoWidgetView.getDuration() / 1000f +
                " seconds.";
        statusText.setText(status);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong(STATE_PROGRESS_TIME, videoWidgetView.getCurrentPosition());
        savedInstanceState.putLong(STATE_VIDEO_DURATION, videoWidgetView.getDuration());
        savedInstanceState.putBoolean(STATE_IS_PAUSED, isPaused);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Prevent the view from rendering continuously when in the background.
        videoWidgetView.pauseRendering();
        // If the video was playing when onPause() is called, the default behavior will be to pause
        // the video and keep it paused when onResume() is called.
        isPaused = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Resume the 3D rendering.
        videoWidgetView.resumeRendering();
        // Update the text to account for the paused video in onPause().
        updateStatusText();
    }

    @Override
    public void onDestroy() {
        // Destroy the widget and free memory.
        videoWidgetView.shutdown();
        super.onDestroy();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contentFooter.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            videoWidgetView.setLayoutParams(layoutParams);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            contentFooter.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Commons.dpToPx(250));
            videoWidgetView.setLayoutParams(layoutParams);

        }
    }

}