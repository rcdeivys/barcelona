package com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.PlayerGameActivity;
import com.BarcelonaSC.BarcelonaApp.models.PlayerPlayByPlay;
import com.BarcelonaSC.BarcelonaApp.utils.CustomPlayerActivity;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.models.PlayByPlay;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 11/10/2017.
 */

public class OfficialLineUpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = OfficialLineUpAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private Context context;
    private PlayByPlay data;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;

    public OfficialLineUpAdapter(OfficialLineUpFragment officialLineUpFragment) {
        this.context = officialLineUpFragment.getContext();
        data = new PlayByPlay();

        onItemClickListener = officialLineUpFragment;
    }


    public void setData(PlayByPlay data) {
        haveHeader = true;
        this.data = data;
    }

    /*public void setData(List<String> gameSummonedData) {
        haveHeader = true;
        this.gameSummonedData = gameSummonedData;
        this.data = gameSummonedData.getJugadores();
    }*/

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
            return new VHHeader(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_o_lineup_header, parent, false));
        } else {
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_o_lineup_players, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VHHeader) {
            VHHeader vhHeader = (VHHeader) holder;

            vhHeader.fctvNameDirector.setText(data.getNombreDt());
            Glide.with(context)
                    .load(data.getFotoDt())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(vhHeader.civDirectorPhoto);
        } else {
            VHItem vhItem = (VHItem) holder;
            position = position - 1;
            if (position == 0) {
                vhItem.fctvTitulares.setVisibility(View.VISIBLE);
                vhItem.fctvTitulares.setText("Titulares");
            } else if (position == data.getTitulares().size()) {
                vhItem.fctvTitulares.setVisibility(View.VISIBLE);
                vhItem.fctvTitulares.setText("Suplentes");
            } else {
                vhItem.fctvTitulares.setVisibility(View.GONE);
            }
            Log.i("TAG", "titulares tamaño:" + data.getTitulares().size() + " suplentes tamaño " + data.getSuplentes().size() + "  posicion:" + position);

            PlayerPlayByPlay playerPlayByPlay;
            if (position < data.getTitulares().size()) {
                playerPlayByPlay = data.getTitulares().get(position);
                final int pos = position;
                vhItem.llLineUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClickItem(pos, true);
                    }
                });
            } else {
                playerPlayByPlay = data.getSuplentes().get(position - data.getTitulares().size());
                final int pos = position - data.getTitulares().size();
                vhItem.llLineUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClickItem(pos, false);
                    }
                });
            }

            vhItem.tvName.setText(splitName(playerPlayByPlay.getNombre()));
            Glide.with(context)
                    .load(playerPlayByPlay.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(vhItem.civPlayerPhoto);


            if (playerPlayByPlay.getActividades() == null || playerPlayByPlay.getActividades().size() == 0) {
                vhItem.llActivityPlayer.setVisibility(View.INVISIBLE);
            } else {
                vhItem.llActivityPlayer.setVisibility(View.VISIBLE);
            }
            vhItem.llActivityPlayer.removeAllViews();
            for (PlayerGameActivity playerGameActivity : playerPlayByPlay.getActividades()) {
                CustomPlayerActivity customPlayerActivity = new CustomPlayerActivity(context);
                customPlayerActivity.setDataActivity(playerGameActivity);
                vhItem.llActivityPlayer.addView(customPlayerActivity);

            }


        }
    }

    private String splitName(String name) {

        String[] splitName = name.split(" ");

        String firstName;
        String lastName;

        firstName = splitName[0].charAt(0) + ". ";
        lastName = splitName[splitName.length - 1];
        return firstName + lastName;


    }

    private PlayerPlayByPlay getItem(int position) {
        if (haveHeader)
            return data.getTitulares().get(position - 1);
        else
            return data.getTitulares().get(position);
    }

    @Override
    public int getItemCount() {
        if (haveHeader)
            return data.getTitulares().size() + data.getSuplentes().size() + 1;
        else
            return data.getTitulares().size() + data.getSuplentes().size();
    }

    public interface OnItemClickListener {
        void onClickItem(int position, boolean isTitular);

        void onClickHeader();

    }

    class VHItem extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_lineup)
        LinearLayout llLineUp;
        @BindView(R.id.fctv_titulares)
        FCMillonariosTextView fctvTitulares;
        @BindView(R.id.civ_player_photo)
        CircleImageView civPlayerPhoto;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.ll_activity_player)
        LinearLayout llActivityPlayer;


        VHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        @BindView(R.id.civ_director_photo)
        CircleImageView civDirectorPhoto;
        @BindView(R.id.fctv_name_director)
        TextView fctvNameDirector;

        VHHeader(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}