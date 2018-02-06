package com.BarcelonaSC.BarcelonaApp.ui.news.views.holders;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.VideoControllerView;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 10/31/17.
 */

public class NewsVideoViewHolder extends RecyclerView.ViewHolder implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {

    @BindView(R.id.img_header)
    public ImageView imgHeader;
    @BindView(R.id.img_play)
    public ImageView imgPlay;
    @BindView(R.id.text_date)
    FCMillonariosTextView textDate;
    @BindView(R.id.text_title)
    FCMillonariosTextView textTitle;
    @BindView(R.id.videoSurface)
    public SurfaceView videoSurface;
    @BindView(R.id.content_view)
    RelativeLayout contentView;
    @BindView(R.id.videoSurfaceContainer)
    FrameLayout frameLayout;

    private Context context;

    MediaPlayer player;
    VideoControllerView controller;

    public static NewsVideoViewHolder getInstance(ViewGroup parent) {
        return new NewsVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_video, parent, false));
    }

    public NewsVideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(News item) {
        if (item.getFoto() != null) {
            Glide.with(context)
                    .load(item.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.millos_news_wm).error(R.drawable.millos_news_wm))
                    .into(imgHeader);
        }


        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);

        player = new MediaPlayer();
        controller = new VideoControllerView(context);

        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource("http://www.youtubemaza.com/file/download/44689");
            player.setOnPreparedListener(this);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        MediaController mc = new MediaController(context);
//        mc.setAnchorView(videoView);
//        videoView.setMediaController(mc);
//        //videoView.setVideoPath(Uri.parse("http://www.html5videoplayer.net/videos/toystory.mp4"));
//        videoView.setVideoPath("http://www.youtubemaza.com/file/download/44689");
//
//        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            public void onPrepared(MediaPlayer mp) {
//
//            }
//        });
//
//
//        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//
//            public boolean onError(MediaPlayer mp, int what, int extra) {
//                Log.d("Dbg", "OnErrorListener: onError: " + what + ", " + extra);
//                return false;
//            }
//
//        });
//
//        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//               finishVideo();
//            }
//        });

        if (item.getFecha() != null)
            textDate.setText(Commons.getStringDate2(item.getFecha()));

        if (item.getTitulo() != null)
            textTitle.setText(item.getTitulo());
    }

    public void initVideo() {
//        videoView.setVisibility(View.VISIBLE);
//        videoView.start();
//        imgPlay.setVisibility(View.GONE);
//        imgHeader.setVisibility(View.GONE);
    }

    public void finishVideo() {
//        videoView.setVisibility(View.GONE);
//        imgPlay.setVisibility(View.VISIBLE);
//        imgHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();
        return false;
    }

    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        player.setDisplay(holder);
        player.prepareAsync();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    // End SurfaceHolder.Callback

    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        controller.setMediaPlayer(this);
        controller.setAnchorView(frameLayout);
        frameLayout.setVisibility(View.VISIBLE);
        imgHeader.setVisibility(View.GONE);
        player.start();
    }
    // End MediaPlayer.OnPreparedListener

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public void pause() {
        player.pause();
    }

    @Override
    public void seekTo(int i) {
        player.seekTo(i);
    }

    @Override
    public void start() {
        player.start();
    }

    @Override
    public boolean isFullScreen() {
        return false;
    }

    @Override
    public void toggleFullScreen() {

    }
    // End VideoMediaController.MediaPlayerControl

}
