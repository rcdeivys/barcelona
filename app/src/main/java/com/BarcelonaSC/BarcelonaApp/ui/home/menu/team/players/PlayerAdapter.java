package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = PlayerAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private Context context;
    private List<NominaItem> data;
    private GameSummonedData gameSummonedData;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;

    public PlayerAdapter(PlayerOffSummonedFragment playerOffSummonedFragment) {
        this.context = playerOffSummonedFragment.getContext();
        gameSummonedData = new GameSummonedData();
        data = new ArrayList<>();

        onItemClickListener = playerOffSummonedFragment;
    }

    public void setData(List<NominaItem> data) {
        haveHeader = false;
        this.data = data;
    }

    public void setData(GameSummonedData gameSummonedData) {
        haveHeader = true;
        this.gameSummonedData = gameSummonedData;
        this.data = gameSummonedData.getJugadores();
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
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_team_game_header, parent, false));
        } else {
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_players, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;
            vhHeader.teamOneName.setText(gameSummonedData.getEquipo1());
            if (gameSummonedData.getBandera2() != null) {
                Glide.with(context)
                        .load(gameSummonedData.getBandera1())
                        .into(vhHeader.teamOneFlag);

            }
            vhHeader.teamTwoName.setText(gameSummonedData.getEquipo2());

            if (gameSummonedData.getBandera2() != null) {
                Glide.with(context)
                        .load(gameSummonedData.getBandera2())
                        .into(vhHeader.teamTwoFlag);
            }


            String result = gameSummonedData.getGoles1() + "-" + gameSummonedData.getGoles2();
            if (gameSummonedData.getEstado().toUpperCase().equals(Constant.Key.PROGRESS) || gameSummonedData.getEstado().toUpperCase().equals(Constant.Key.FINALIZED))
                vhHeader.clash.setText(result);
            else
                vhHeader.clash.setText("VS");


            String[] arrayString = gameSummonedData.getFechaEtapa().split(",");
            // vhHeader.gameType.setText(arrayString[1]);

            vhHeader.ctv_stadium.setText(App.get().getString(R.string.stadium, gameSummonedData.getEstadio()));

            vhHeader.ctvGameTime.setText(
                    App.get().getString(R.string.game_date_time
                            , Commons.getStringDate2(gameSummonedData.getFecha())
                            , Commons.getStringHour(gameSummonedData.getFecha()))
                            + " (" + gameSummonedData.getEstado() + ")");

            vhHeader.dateFifa.setText(arrayString[0]);

            vhHeader.llGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClickHeader();
                }
            });

        } else {
            VHItem vhItem = (VHItem) holder;

            NominaItem playoffData = getItem(position);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            vhItem.playerImg.setLayoutParams(params);
            Glide.with(context)
                    .load(playoffData.getBanner())
                    .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                    .into(vhItem.playerImg);
            vhItem.playerImg.setAlpha((float) 1.0);

            vhItem.playerImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (haveHeader)
                        onItemClickListener.onClickItem(position - 1);
                    else
                        onItemClickListener.onClickItem(position);
                }
            });
        }
    }

    private NominaItem getItem(int position) {
        if (haveHeader)
            return data.get(position - 1);
        else
            return data.get(position);
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return data.size() + 1;
        else
            return data.size();
    }

    public interface OnItemClickListener {
        void onClickItem(int position);

        void onClickHeader();

    }

    class VHItem extends RecyclerView.ViewHolder {
        @BindView(R.id.player_img)
        ImageView playerImg;

        VHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.team_one_name)
        FCMillonariosTextView teamOneName;
        @BindView(R.id.clash)
        FCMillonariosTextView clash;
        @BindView(R.id.team_two_name)
        FCMillonariosTextView teamTwoName;
        @BindView(R.id.ctv_stadium)
        FCMillonariosTextView ctv_stadium;
        @BindView(R.id.ctv_game_league)
        FCMillonariosTextView ctvGameTime;
        @BindView(R.id.date_fifa)
        FCMillonariosTextView dateFifa;
        @BindView(R.id.team_one_flag)
        ImageView teamOneFlag;
        @BindView(R.id.team_two_flag)
        ImageView teamTwoFlag;
        @BindView(R.id.LL_Game)
        LinearLayout llGame;


        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}