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
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.PlayerData;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerProfileAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = PlayerProfileAdapter.class.getSimpleName();
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
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
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;
            initHeader(vhHeader);
        } else {
            VHItem vhItem = (VHItem) holder;
            initItem(vhItem, position);
        }
    }

    private void initHeader(VHHeader vhHeader) {
        Glide.with(context)
                .load(playerData.getBanner())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhHeader.playerImg);

        if (!type.equals(Constant.Key.GAME_FB)) {
            if (playerData.getGoles1() != null)
                vhHeader.ctvResultGame.setText(playerData.getGoles1() + "-" + playerData.getGoles2());

            vhHeader.ctvWeight.setText(playerData.getnCamiseta());

            vhHeader.ctvPlayerNationality.setText(playerData.getNacionalidad());

            vhHeader.ctvPlayerHeight.setText(playerData.getEstatura());

            vhHeader.ctvPlayerBirthdate.setText(Commons.getStringDate2(playerData.getFechaNacimiento()));

            vhHeader.ctvTeamOneName.setText(playerData.getEquipo1());

            vhHeader.ctvTeamTwoName.setText(playerData.getEquipo2());

            vhHeader.ctvGameData.setText(Commons.getStringDate2(playerData.getFecha()));

            vhHeader.ctvGameFechaFifa.setText(playerData.getFechaEtapa());
            vhHeader.ctvGameLeague.setText("");
            Glide.with(context)
                    .load(playerData.getBandera1())
                    .into(vhHeader.imTeamOneFlag);

            Glide.with(context)
                    .load(playerData.getBandera2())
                    .into(vhHeader.ivTeamTwoFlag);

            if (playerData.getApalusosUltimoPartido() != null)
                vhHeader.tvGameApplause.setText(String.valueOf(playerData.getApalusosUltimoPartido()));

            if (playerData.getAplausosAcumulado() != null)
                vhHeader.tvTotalApplause.setText(String.valueOf(playerData.getAplausosAcumulado()));

            vhHeader.btnApplaused.setOnClickListener(new View.OnClickListener() {
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
            vhHeader.hideElem1.setVisibility(View.GONE);
            vhHeader.hideElem2.setVisibility(View.GONE);
        }

    }

    private void initItem(VHItem vhItem, final int position) {
        News recentItem = getItem(position);
        Glide.with(context)
                .load(recentItem.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(vhItem.ivNews);
    /*    // Set play image
        if (recentItem.getTipo().matches(Constant.NewsType.VIDEO)) {
            vhItem.ivPlay.setImageResource(R.drawable.ic_play_circle_60dp);
        } else {
            vhItem.ivPlay.setImageDrawable(null);
        }
*/

        vhItem.tvData.setText(Commons.getStringDate2(recentItem.getFecha()));
        //     vhItem.tvData.setTypeface(FontsUtil.getOpenSansReularFonts(context));
        vhItem.tvTitle.setText(recentItem.getTitulo());
        //  vhItem.tvTitle.setTypeface(FontsUtil.getHelveticaCondesed2Fonts(context));

        vhItem.llNewsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haveHeader)
                    onItemClickListener.onClickItem(position - 1);
                else
                    onItemClickListener.onClickItem(position);
            }
        });

    }

    private News getItem(int position) {
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

    public interface OnItemClickListener {
        void onClickItem(int position);

        void onClickHeader();

    }

    class VHItem extends RecyclerView.ViewHolder {

        @BindView(R.id.content_news_item)
        LinearLayout llNewsItem;
        @BindView(R.id.iv_news)
        ImageView ivNews;
        /*    @BindView(R.id.iv_play)
            ImageView ivPlay;*/
        @BindView(R.id.tv_date)
        TextView tvData;
        @BindView(R.id.tv_title)
        TextView tvTitle;


        VHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
    }

}