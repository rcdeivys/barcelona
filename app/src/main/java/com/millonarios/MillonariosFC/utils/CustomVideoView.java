package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.VideoView;

import com.millonarios.MillonariosFC.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 1/15/18.
 */

public class CustomVideoView extends RelativeLayout implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, SeekBar.OnSeekBarChangeListener {

    private static final String STATE_IS_PAUSED = "isPaused";
    private static final String STATE_VIDEO_DURATION = "videoDuration";
    private static final String STATE_PROGRESS_TIME = "progressTime";

    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.fullscreen)
    AppCompatImageView btnFullScreen;
    @BindView(R.id.content_video)
    RelativeLayout contentVideo;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.play)
    ImageView play;
    @BindView(R.id.time)
    FCMillonariosTextView textTime;

    private boolean isFullScreen = false;

    private boolean isPaused = false;

    public CustomVideoView(Context context) {
        super(context);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        View root = LayoutInflater.from(getContext()).inflate(R.layout.custom_video_view, null);
        addView(root);

        ButterKnife.bind(this, root);

        videoView.setOnCompletionListener(this);
        videoView.setOnPreparedListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        progressBar.setVisibility(View.VISIBLE);

        videoView.stopPlayback();

    }



    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        progressBar.setVisibility(View.GONE);
        contentVideo.setVisibility(View.VISIBLE);

        contentVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused) {
                    videoView.start();
                    videoView.setVisibility(View.VISIBLE);
                    play.setVisibility(View.GONE);
                    isPaused = false;
                } else {
                    play.setVisibility(View.VISIBLE);
                    videoView.pause();
                    isPaused = true;
                }
            }
        });

        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.postDelayed(onEverySecond, 1000);
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (b) {
            videoView.seekTo(i);
            seekBar.setProgress(0);
            videoView.seekTo(0);
            time(0);
            videoView.pause();
            isPaused = true;
            play.setVisibility(View.VISIBLE);
            time(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    public void setVideoUrl(String url) {
        videoView.setVideoURI(Uri.parse(url));
    }

    private void time(int time) {
        textTime.setText(String.format("%d : %d", TimeUnit.MILLISECONDS.toMinutes((long) time), TimeUnit.MILLISECONDS.toSeconds((long) time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) time))));

    }

    private Runnable onEverySecond = new Runnable() {

        @Override
        public void run() {

            if (seekBar != null) {
                seekBar.setProgress(videoView.getCurrentPosition());
                time(videoView.getCurrentPosition());
            }

            if (videoView.isPlaying()) {
                seekBar.postDelayed(onEverySecond, 1000);
            }

        }
    };

    public void setFullScreen(OnClickListener onClickListener) {
        btnFullScreen.setOnClickListener(onClickListener);
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }
}
