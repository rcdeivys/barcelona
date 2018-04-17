package com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerProfile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.holders.NewsViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements CustomVideoView.CustomVideoViewOnListener {


    private static final String TAG = PlayerProfileAdapter.class.getSimpleName();
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int APLAUDIO = 1;
    private static final int NO_APLAUDIO = 0;
    @BindView(R.id.player_img)
    ImageView playerImg;
    private Context context;
    private PlayerData playerData;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;
    String type;

    public PlayerProfileAdapter(PlayerProfileFragment playerOffSummonedFragment) {
        this.context = playerOffSummonedFragment.getContext();
        playerData = new PlayerData();
        //  playerData.setNoticias(new ArrayList<News>());
        onItemClickListener = playerOffSummonedFragment;
    }

    public PlayerProfileAdapter(PlayerProfileFragment playerOffSummonedFragment, String type) {
        this.context = playerOffSummonedFragment.getContext();
        this.type = type;
        playerData = new PlayerData();
        onItemClickListener = playerOffSummonedFragment;
    }

    public void setData(PlayerData playerData) {
        haveHeader = true;
        this.playerData = playerData;
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
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player_header, parent, false));
        } else {
            return NewsViewHolder.getInstance(parent);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHHeader) {
            ((VHHeader) holder).initHeader(playerData);
        } else {
            configurNewsViewHolder((NewsViewHolder) holder, position);
        }
    }

    private void configurNewsViewHolder(final NewsViewHolder holder, final int position) {
        holder.setNews(getNewsList(position));
        holder.ivShare.setVisibility(View.GONE);
        if (getNewsList(position).getTipo().equals(Constant.NewsType.VIDEO)) {
           /* if (getNewsList(position).isDorado() && !SessionManager.getInstance().getUser().isDorado()) {
                holder.ivShare.setVisibility(View.GONE);
            } else {
                holder.ivShare.setVisibility(View.VISIBLE);
            }*/
            holder.videoView.setCustomVideoViewOnListener(this);
            holder.setNewsVideo(getNewsList(position), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                 /*   if (!SessionManager.getInstance().getUser().isDorado() && getNewsList(position).isDorado()) {
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
            return playerData.getNewsList().get(position - 1);
        else
            return playerData.getNewsList().get(position);
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return playerData.getNewsList().size() + 1;
        else
            return playerData.getNewsList().size();
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

        @BindView(R.id.lin_1)
        LinearLayout hideElem1;
        @BindView(R.id.lin_2)
        LinearLayout hideElem2;
        @BindView(R.id.player_img)
        ImageView playerImg;
        @BindView(R.id.TV_GameApplause)
        TextView tvGameApplause;
        @BindView(R.id.TV_TotalApplause)
        TextView tvTotalApplause;
        @BindView(R.id.btn_applaused)
        ImageView btnApplaused;
        @BindView(R.id.team_one_name)
        FCMillonariosTextView ctvTeamOneName;
        @BindView(R.id.result)
        FCMillonariosTextView ctvResultGame;
        @BindView(R.id.team_two_name)
        FCMillonariosTextView ctvTeamTwoName;
        @BindView(R.id.game_date)
        FCMillonariosTextView ctvGameData;
        @BindView(R.id.game_fecha_fifa)
        FCMillonariosTextView ctvGameFechaFifa;
        @BindView(R.id.team_one_flag)
        ImageView imTeamOneFlag;
        @BindView(R.id.team_two_flag)
        ImageView ivTeamTwoFlag;

        @BindView(R.id.ctv_weight)
        FCMillonariosTextView ctvWeight;
        @BindView(R.id.ctv_player_nationality)
        FCMillonariosTextView ctvPlayerNationality;
        @BindView(R.id.ctv_player_height)
        FCMillonariosTextView ctvPlayerHeight;
        @BindView(R.id.ctv_player_birthdate)
        FCMillonariosTextView ctvPlayerBirthdate;
        @BindView(R.id.ctv_game_league)
        FCMillonariosTextView ctvGameLeague;

        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void initHeader(final PlayerData playerData) {
            Glide.with(context)
                    .load(playerData.getBanner())
                    .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                    .into(playerImg);

            if (!type.equals(Constant.Key.GAME_FB)) {
                if (!"Pendiente".equals(playerData.getEstado()))
                    ctvResultGame.setText(playerData.getGoles1() + "-" + playerData.getGoles2());
                else
                    ctvResultGame.setText("VS");

                ctvWeight.setText(playerData.getPeso());
                ctvWeight.setText(playerData.getPeso());
                ctvPlayerNationality.setText(playerData.getNacionalidad());

                ctvPlayerHeight.setText(playerData.getEstatura());

                ctvPlayerBirthdate.setText(Commons.getStringDate(playerData.getFechaNacimiento()));

                ctvTeamOneName.setText(playerData.getEquipo1());

                ctvTeamTwoName.setText(playerData.getEquipo2());

                if (playerData.getFecha() != null) {
                    String d = Commons.getStringDate2(playerData.getFecha()).replace(".", "");
                    ctvGameData.setText(d.toUpperCase());
                }

                ctvGameFechaFifa.setText(playerData.getFechaEtapa());
                ctvGameLeague.setText("");
                Glide.with(context)
                        .load(playerData.getBandera1())
                        .into(imTeamOneFlag);

                Glide.with(context)
                        .load(playerData.getBandera2())
                        .into(ivTeamTwoFlag);

                if (playerData.getApalusosUltimoPartido() != null)
                    tvGameApplause.setText(String.valueOf(playerData.getApalusosUltimoPartido()));

                if (playerData.getAplausosAcumulado() != null)
                    tvTotalApplause.setText(String.valueOf(playerData.getAplausosAcumulado()));

                setImgAplauso(playerData.getUltimoAplauso());

                btnApplaused.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (playerData.getSepuedeaplaudir() == 1) {
                            onItemClickListener.onClickHeader();
                        } else {
                            Toast.makeText(context, context.getString(R.string.clap_later), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                hideElem1.setVisibility(View.GONE);
                hideElem2.setVisibility(View.GONE);
            }
        }

        private void setImgAplauso(int band) {
            if (band == APLAUDIO) {
             //   btnApplaused.setImageResource(R.drawable.applause_on);
            } else {
             //   btnApplaused.setImageResource(R.drawable.applause_off);
            }
        }
    }
}
