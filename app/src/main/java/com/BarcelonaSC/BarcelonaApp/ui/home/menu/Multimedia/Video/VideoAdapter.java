package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.Video;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Deivys
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomVideoView.CustomVideoViewOnListener {

    private static final String TAG = VideoAdapter.class.getSimpleName();
    private OnItemClickListener onItemClickListener;

    private static final int TYPE_VIDEO = 0;
    private List<MultimediaDataResponse> listVideos;
    private Context context;

    public VideoAdapter(VideoFragment videoFragment) {
        this.context = videoFragment.getContext();
        listVideos = new ArrayList<>();
        onItemClickListener = videoFragment;
    }

    public void setData(List<MultimediaDataResponse> listVideos) {
        this.listVideos = listVideos;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_VIDEO;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mm_videos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final VideoHolder videoHolder = (VideoHolder) holder;
        final MultimediaDataResponse item = listVideos.get(position);

        videoHolder.videoView.setCustomVideoViewPlayListener(new CustomVideoView.CustomVideoViewPlayListener() {
            @Override
            public void play() {
                onItemClickListener.playVideo(position);
            }
        });
        videoHolder.setNewsVideo(item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoHolder.videoView.pause();
                onItemClickListener.onVideoClick(item, videoHolder.getVideoCurrentPosition());
            }
        });

        videoHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onVideoShare(String.valueOf(item.getId()));
                videoHolder.videoView.pause();
            }
        });

    }

    public void pauseVideo(List<Integer> position) {
        for (Integer positions : position) {
            notifyItemChanged(positions);
        }

    }


    @Override
    public int getItemCount() {
        return listVideos.size();
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void videoIsDorado() {

    }

    public interface OnItemClickListener {

        void onClickItem(int position);

        void onVideoClick(MultimediaDataResponse multimediaDataResponse, int currentVideo);

        void onVideoShare(String id);

        void playVideo(int position);

    }

    public class VideoHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.texture_video_view)
        public CustomVideoView videoView;

        @BindView(R.id.img_header)
        ImageView imgHeader;

        @BindView(R.id.tv_title)
        FCMillonariosTextView tvTitle;

        @BindView(R.id.tv_tag)
        FCMillonariosTextView tvTag;

        @BindView(R.id.iv_share)
        public ImageView ivShare;

        private Context context;

        public VideoHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            ButterKnife.bind(this, itemView);
        }

        public void setNewsVideo(MultimediaDataResponse item, View.OnClickListener onClickListener) {
            imgHeader.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            scaleVideo(Commons.dpToPx(250));
            videoView.setVideoUrl(item.getLink(), 0, false);
            videoView.setImage(context, item.getFoto());
//            videoView.setImageNews(context, item.getFoto());
            //ivShare.setVisibility(View.VISIBLE);
            videoView.setButtonFullScreen(R.drawable.ic_media_fullscreen, Commons.getColor(R.color.white), onClickListener);
            tvTitle.setText(item.getTitulo());
            tvTag.setText("#YoSoyAzul");

        }

        private void scaleVideo(int h) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, h);
            videoView.setLayoutParams(layoutParams);
        }

        public int getVideoCurrentPosition() {
            return videoView.getCurrentPosition();
        }
    }
}
