package com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.ChooseProfileData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.holders.NewsViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomVideoView.CustomVideoViewOnListener {


    private static final String TAG = ChooseProfileAdapter.class.getSimpleName();
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    @BindView(R.id.player_img)
    ImageView playerImg;
    private Context context;
    private ChooseProfileData playerData;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;
    private boolean showVotes = false;
    String type;

    public ChooseProfileAdapter(ChooseProfileFragment playerOffSummonedFragment) {
        this.context = playerOffSummonedFragment.getContext();
        playerData = new ChooseProfileData();
        //  playerData.setNoticias(new ArrayList<News>());
        onItemClickListener = playerOffSummonedFragment;
    }

    public ChooseProfileAdapter(ChooseProfileFragment playerOffSummonedFragment, String type) {
        this.context = playerOffSummonedFragment.getContext();
        this.type = type;
        playerData = new ChooseProfileData();
        onItemClickListener = playerOffSummonedFragment;
    }

    public void setData(ChooseProfileData playerData, boolean showVotes) {
        haveHeader = true;
        this.playerData = playerData;
        this.showVotes = showVotes;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position) && haveHeader) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_profile_header, parent, false));
        } else {
            return NewsViewHolder.getInstance(parent);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHHeader) {
            initHeader((VHHeader) holder);
        } else {
            configurNewsViewHolder((NewsViewHolder) holder, position);
        }
    }

    private void initHeader(VHHeader vhHeader) {
        Glide.with(context)
                .load(playerData.getBanner())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhHeader.playerImg);

        vhHeader.tvVotes.setText(String.valueOf(playerData.getVotos()));

        if (showVotes) {
            vhHeader.llVotes.setVisibility(View.VISIBLE);
        } else {
            vhHeader.llVotes.setVisibility(View.INVISIBLE);
        }
    }

    private void configurNewsViewHolder(final NewsViewHolder holder, final int position) {
        holder.setNews(getNewsList(position));
        holder.ivShare.setVisibility(View.GONE);
        if (getNewsList(position).getTipo().equals(Constant.NewsType.VIDEO)) {
        /*    if (getNewsList(position).isDorado() && !SessionManager.getInstance().getUser().isDorado()) {
                holder.ivShare.setVisibility(View.GONE);
            } else {
                holder.ivShare.setVisibility(View.VISIBLE);
            }*/
            holder.videoView.setCustomVideoViewOnListener(this);
            holder.setNewsVideo(getNewsList(position), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  if (!SessionManager.getInstance().getUser().isDorado() && getNewsList(position).isDorado()) {
                        holder.videoView.customVideoViewOnListener.videoIsDorado();
                    } else {
                        holder.videoView.pause();
                        onItemClickListener.onClickVideoItem(getNewsList(position), holder.getVideoCurrentPosition());
                    }*/
                }
            });
        } else {
            holder.onClickContentNewsItem(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickItem(getNewsList(position));
                }
            });
        }
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual(Constant.Key.SHARE_NEWS, String.valueOf(getNewsList(position).getId()));
            }
        });
    }

    private News getNewsList(int position) {
        if (haveHeader)
            return playerData.getNoticias().get(position - 1);
        else
            return playerData.getNoticias().get(position);
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return playerData.getNoticias().size() + 1;
        else
            return playerData.getNoticias().size();
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void videoIsDorado() {
        onItemClickListener.onVideoIsDorado();
    }

    public interface OnItemClickListener {
        void onClickItem(News news);

        void onClickHeader();

        void onClickVideoItem(News news, int currentPosition);

        void onVideoIsDorado();

    }

    class VHHeader extends RecyclerView.ViewHolder {

        @BindView(R.id.player_img)
        ImageView playerImg;
        @BindView(R.id.ll_votes)
        LinearLayout llVotes;
        @BindView(R.id.tv_votes)
        TextView tvVotes;


        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}