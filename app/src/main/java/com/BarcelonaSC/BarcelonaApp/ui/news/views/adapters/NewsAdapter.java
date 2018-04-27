package com.BarcelonaSC.BarcelonaApp.ui.news.views.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.Match;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.holders.NewsViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter personalizado para el ListView de noticias
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomVideoView.CustomVideoViewOnListener {
    private static final String TAG = NewsAdapter.class.getSimpleName();
    private static final int NEWS_ITEM = 1001;
    private static final int NEWS_HEADER = 1002;

    private Context context;
    private List<Object> newsList;
    private int height;
    private OnItemClickListener onItemClickListener;
    SimpleDateFormat formatOut;
    SimpleDateFormat formatIn;
    NewsViewHolder newsViewHolder;

    public NewsAdapter(Context context, List<Object> newsList) {
        this.context = context;
        this.newsList = newsList;
        formatOut = new SimpleDateFormat("yyyy-MM-dd");
        formatIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case NEWS_ITEM:
                return NewsViewHolder.getInstance(parent);
            case NEWS_HEADER:
                return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false));
            default:
                return NewsViewHolder.getInstance(parent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (newsList.get(position) instanceof Match) {
            return NEWS_HEADER;
        } else {
            return NEWS_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case NEWS_HEADER:
                configurHeadersViewHolder(((VHHeader) holder), position);
                break;
            case NEWS_ITEM:
                configurNewsViewHolder(((NewsViewHolder) holder), position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    private void configurNewsViewHolder(final NewsViewHolder holder, final int position) {
        holder.setNews((News) newsList.get(position));
        if (((News) newsList.get(position)).getTipo().equals(Constant.NewsType.VIDEO)) {
           /* if (((News) newsList.get(position)).isDorado() && !SessionManager.getInstance().getUser().isDorado()) {
                holder.ivShare.setVisibility(View.GONE);
            } else {
                holder.ivShare.setVisibility(View.VISIBLE);
            }*/
            holder.videoView.setCustomVideoViewOnListener(this);
            holder.videoView.setCustomVideoViewPlayListener(new CustomVideoView.CustomVideoViewPlayListener() {
                @Override
                public void play() {
                    onItemClickListener.playVideo(position);
                }
            });
            holder.setNewsVideo(((News) newsList.get(position)), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  /*  if (!SessionManager.getInstance().getUser().isDorado() && ((News) newsList.get(position)).isDorado()) {
                        holder.videoView.customVideoViewOnListener.videoIsDorado();
                    } else {

                    }*/
                    holder.videoView.pause();
                    onItemClickListener.onVideoClick(((News) newsList.get(position)), holder.getVideoCurrentPosition());
                }
            });
        } else {
            holder.onClickContentNewsItem(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickItem(((News) newsList.get(position)));
                }
            });
        }
        holder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual(Constant.Key.SHARE_NEWS, String.valueOf(((News) newsList.get(position)).getId()));
            }
        });
    }

    private void configurHeadersViewHolder(final VHHeader holder, final int position) {
        holder.initDataGame(((Match) newsList.get(position)));
        holder.initHeaderMatch(((Match) newsList.get(position)));
        holder.itemView.setMinimumHeight(200);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onCalendarClick(String.valueOf(((Match) newsList.get(position)).getIdpartido()));
            }
        });
    }

    public void updateAll(List<Object> update) {
        newsList.addAll(newsList.size(), update);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onPrepared() {

    }

    @Override
    public void videoIsDorado() {
        onItemClickListener.onVideoIsDorado();
    }

    class VHHeader extends RecyclerView.ViewHolder {

        //    MATCH
        @BindView(R.id.iv_team_1_flag)
        ImageView ivTeamFlag1;

        @BindView(R.id.iv_team_2_flag)
        ImageView ivTeamFlag2;

        @BindView(R.id.iv_team_1_name)
        TextView tvTeamName1;

        @BindView(R.id.iv_team_2_name)
        TextView tvTeamName2;

        @BindView(R.id.tv_match_date)
        TextView tvMatchDate;

        @BindView(R.id.tv_match_score)
        TextView tvMatchScore;

        @BindView(R.id.tv_sub_title)
        TextView tvSubTitle;

        @BindView(R.id.tv_match_state)
        TextView tvMatchState1;

        @BindView(R.id.tv_match_state_2)
        TextView tvMatchState2;

        @BindView(R.id.tv_info)
        TextView tvInfo;

        @BindView(R.id.v_separador)
        View viewSaparador;

        @BindView(R.id.iv_share)
        ImageView ivShare;

        Context context;

        public VHHeader(View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            ButterKnife.bind(this, itemView);
            ivShare.setVisibility(View.VISIBLE);
            viewSaparador.setVisibility(View.GONE);
        }

        public void initHeaderMatch(Match match) {
            viewSaparador.setVisibility(View.GONE);

            tvMatchDate.setText(match.getFechaEtapa());

            Glide.with(context).load(match.getBandera1())
                    .apply(new RequestOptions().placeholder(R.drawable.logo_bsc).error(R.drawable.logo_bsc))
                    .into(ivTeamFlag1);

            tvTeamName1.setText(match.getEquipo1());

            Glide.with(context).load(match.getBandera2())
                    .apply(new RequestOptions().placeholder(R.drawable.logo_bsc).error(R.drawable.logo_bsc))
                    .into(ivTeamFlag2);

            tvTeamName2.setText(match.getEquipo2());

            if (Constant.Key.PROGRESS.equals(match.getEstado())) {
                tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
            } else if (Constant.Key.FINALIZED.equals(match.getEstado())) {
                tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
                tvMatchState1.setText(match.getEstado().toUpperCase());
                tvMatchState2.setText("");
            } else {
                tvMatchScore.setText(App.getAppContext().getString(R.string.vs));
            }

//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                matchClickListener.onMatchClicked(match.getIdpartido());
//            }
//        });

            if (match.getEstadio() != null && match.getFecha() != null && match.getEstado() != null) {
                initDataGame(match);
            }
        }

        public void initDataGame(Match match) {
            if (match.getEstadio() != null) {
                String state = App.get()
                        .getString(R.string.stadium
                                , match.getEstadio().replace("Estadio:", " "));

                if (state != null)
                    tvMatchState1.setText(state);
            }

            if (match.getFecha() != null) {
                String state2;
                String date = Commons.getStringDate2(match.getFecha()).replace(".", "");
                if (match.getInfo() != null) {
                    state2 = date.toUpperCase()
                            + "  |  " + Commons.getStringHour(match.getFecha())
                            + match.getInfo();
                } else {
                    state2 = date.toUpperCase()
                            + "  |  " + Commons.getStringHour(match.getFecha());
                }

                tvMatchState2.setText(state2);
            }
        }
    }

    public void pauseVideo(int position) {
        notifyItemChanged(position);
    }

    public interface OnItemClickListener {
        void onClickItem(News news);

        void onVideoClick(News news, int currentVideo);

        void playVideo(int position);

        void onCalendarClick(String id);

        void onVideoIsDorado();

    }

}