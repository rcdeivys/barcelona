package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.players;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder.MatchChildViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 11/10/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private static final String TAG = PlayerAdapter.class.getSimpleName();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final String IN_PROGRESS = "En curso";
    private static final String FINALIZED = "Finalizado";
    private Context context;
    private List<NominaItem> data;
    private List<NominaItem> dataFiltered;
    private List<NominaItem> oldData;
    private GameSummonedData gameSummonedData;
    private OnItemClickListener onItemClickListener;
    private boolean haveHeader = false;

    public PlayerAdapter(PlayerOffSummonedFragment playerOffSummonedFragment) {
        this.context = playerOffSummonedFragment.getContext();
        gameSummonedData = new GameSummonedData();
        data = new ArrayList<>();
        oldData = new ArrayList<>();
        onItemClickListener = playerOffSummonedFragment;
    }

    public void setData(List<NominaItem> data) {
        haveHeader = false;
        this.data = data;
        this.oldData.clear();
        this.oldData.clear();
        this.oldData.addAll(data);
        this.dataFiltered = data;
    }

    public void setData(GameSummonedData gameSummonedData) {
        haveHeader = true;
        this.gameSummonedData = gameSummonedData;
        this.data = gameSummonedData.getJugadores();
        this.oldData.addAll(gameSummonedData.getJugadores());
        this.dataFiltered = data;
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
            return new MatchChildViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_match, parent, false));
        } else {
            return new VHItem(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_players, parent, false));
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MatchChildViewHolder) {
            MatchChildViewHolder matchChildViewHolder = (MatchChildViewHolder) holder;
            matchChildViewHolder.tvMatchDate.setText(Commons.getStringDate(gameSummonedData.getFechaEtapa()));


            Glide.with(context).load(gameSummonedData.getBandera1())
                    .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                    .into(matchChildViewHolder.ivTeamFlag1);

            matchChildViewHolder.tvTeamName1.setText(gameSummonedData.getEquipo1());

            Glide.with(context).load(gameSummonedData.getBandera2())
                    .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                    .into(matchChildViewHolder.ivTeamFlag2);

            matchChildViewHolder.tvTeamName2.setText(gameSummonedData.getEquipo2());
            if (Constant.Key.PROGRESS.equals(gameSummonedData.getEstado())) {
                matchChildViewHolder.tvMatchScore.setText(gameSummonedData.getGoles1() + "-" + gameSummonedData.getGoles2());
            } else if (Constant.Key.FINALIZED.equals(gameSummonedData.getEstado())) {
                matchChildViewHolder.tvMatchScore.setText(gameSummonedData.getGoles1() + "-" + gameSummonedData.getGoles2());
                matchChildViewHolder.tvMatchState.setText(gameSummonedData.getEstado().toUpperCase());
                matchChildViewHolder.tvMatchState2.setText("");
            } else {
                matchChildViewHolder.tvMatchScore.setText(App.getAppContext().getString(R.string.vs));
            }

            if (gameSummonedData.getEstadio() != null && gameSummonedData.getFecha() != null && gameSummonedData.getEstado() != null) {
                initDataGame(matchChildViewHolder, gameSummonedData);
            }

        } else {
            VHItem vhItem = (VHItem) holder;

            final NominaItem playoffData = getItem(position);

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

            vhItem.ivShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareSection.shareIndividual(Constant.Key.SHARE_PLAYER, String.valueOf(playoffData.getIdJugador()));
                }
            });
        }
    }

    public void initDataGame(MatchChildViewHolder matchChildViewHolder, GameSummonedData match) {

        if (match.getEstadio() != null) {
            String state = App.get()
                    .getString(R.string.stadium
                            , match.getEstadio().replace("Estadio:", " "));

            matchChildViewHolder.tvMatchState

                    .setText(state);
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

            matchChildViewHolder.tvMatchState2
                    .setText(state2);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                Log.d(TAG, "charSequence " + charSequence);
                if (charString.isEmpty()) {
                    dataFiltered.clear();
                    dataFiltered = oldData;
                    Log.d(TAG, "filters oldData size " + oldData.size());
                } else {
                    List<NominaItem> filteredList = new ArrayList<>();
                    for (NominaItem row : oldData) {

                        if (row.getNombre().toUpperCase().contains(charString.toUpperCase())) {
                            filteredList.add(row);
                        }
                    }

                    dataFiltered = filteredList;
                }
                Log.d(TAG, "filters size " + dataFiltered.size());
                FilterResults filterResults = new FilterResults();
                filterResults.values = dataFiltered;
                filterResults.count = dataFiltered.size();
                Log.d(TAG, "filters size " + filterResults.count);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                Log.d(TAG, "filters count " + filterResults.count);
                data.clear();
                data.addAll((ArrayList<NominaItem>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }

    public void closeSearch() {
        data.clear();
        data.addAll(oldData);
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onClickItem(int position);

        void onClickHeader();

    }

    class VHItem extends RecyclerView.ViewHolder {

        @BindView(R.id.player_img)
        ImageView playerImg;

        @BindView(R.id.iv_share)
        ImageView ivShare;

        VHItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public void removeAll() {
        data.clear();
        dataFiltered.clear();
        oldData.clear();
        notifyDataSetChanged();
    }

}