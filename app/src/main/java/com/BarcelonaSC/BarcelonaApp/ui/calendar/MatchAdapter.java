package com.BarcelonaSC.BarcelonaApp.ui.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.Match;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder.MatchChildViewHolder;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder.MatchParentViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Erick on 01/11/2017.
 */

public class MatchAdapter extends ExpandableRecyclerViewAdapter<MatchParentViewHolder, MatchChildViewHolder> {

    LayoutInflater inflate;
    private Context mContext;
    private MatchClickListener matchClickListener;
    private String subseccion;

    public MatchAdapter(Context context, List<? extends ExpandableGroup> groups, String subseccion) {
        super(groups);
        mContext = context;
        inflate = LayoutInflater.from(context);
        this.subseccion = subseccion;
    }

    @Override
    public MatchParentViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int i) {
        View view = inflate.inflate(R.layout.list_item_match_parent, viewGroup, false);
        return new MatchParentViewHolder(view, subseccion);
    }

    @Override
    public MatchChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        View view = inflate.inflate(R.layout.item_match, viewGroup, false);
        return new MatchChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(MatchChildViewHolder matchChildViewHolder, int i, ExpandableGroup expandableGroup, int i1) {
//        final Match match = (Match) expandableGroup.getItems().get(i1);
//
//        if (match.getDestacado() == 0)
//            matchChildViewHolder.contentGameCalendar.setBackgroundColor(Commons.getColor(R.color.colorHeader));
//        else
//            matchChildViewHolder.contentGameCalendar.setBackgroundColor(Commons.getColor(R.color.colorAccent));
//
//        matchChildViewHolder.tvMatchDate.setText(Commons.getStringDate(match.getFechaEtapa()));
//
//        if (i1 == 0) {
//            matchChildViewHolder.llheader.setVisibility(View.GONE);
//        }
//
//        if (i1 == expandableGroup.getItems().size() - 1)
//            matchChildViewHolder.view.setVisibility(View.GONE);
//        else
//            matchChildViewHolder.view.setVisibility(View.VISIBLE);
//
//        Glide.with(mContext).load(match.getBandera1())
//                .apply(new RequestOptions().placeholder(R.drawable.logo_bsc).error(R.drawable.logo_bsc))
//                .into(matchChildViewHolder.ivTeamFlag1);
//
//        matchChildViewHolder.tvTeamName1.setText(match.getEquipo1());
//
//        Glide.with(mContext).load(match.getBandera2())
//                .apply(new RequestOptions().placeholder(R.drawable.logo_bsc).error(R.drawable.logo_bsc))
//                .into(matchChildViewHolder.ivTeamFlag2);
//
//        matchChildViewHolder.tvTeamName2.setText(match.getEquipo2());
//        if (Constant.Key.PROGRESS.equals(match.getEstado())) {
//            matchChildViewHolder.tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
//        } else if (Constant.Key.FINALIZED.equals(match.getEstado())) {
//            matchChildViewHolder.tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
//            matchChildViewHolder.tvMatchState.setText(match.getEstado().toUpperCase());
//            matchChildViewHolder.tvMatchState2.setText("");
//        } else {
//            matchChildViewHolder.tvMatchScore.setText(App.getAppContext().getString(R.string.vs));
//        }
//
//        matchChildViewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ShareSection.shareIndividual(Constant.Key.SHARE_GAME, String.valueOf(match.getIdpartido()));
//            }
//        });
//
//        matchChildViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                matchClickListener.onMatchClicked(match.getIdpartido());
//            }
//        });
//
//        if (match.getEstadio() != null && match.getFecha() != null && match.getEstado() != null) {
//            initDataGame(matchChildViewHolder, match);
//        }
//
//        /*if (match.getInfo() != null) {
//            matchChildViewHolder.tvInfo.setText(match.getInfo());
//        } else {
//            matchChildViewHolder.tvInfo.setVisibility(View.GONE);
//        }*/

        final Match match = (Match) expandableGroup.getItems().get(i1);
        if (match.getDestacado() == 0) {
            matchChildViewHolder.contentGameCalendar.setBackgroundColor(Commons.getColor(R.color.colorPrimaryDark));
            matchChildViewHolder.tvInfo.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvTeamName1.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvTeamName2.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvMatchDate.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvMatchState.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvMatchScore.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvMatchState2.setTextColor(mContext.getResources().getColor(R.color.white));
            matchChildViewHolder.tvSubTitle.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            matchChildViewHolder.contentGameCalendar.setBackgroundColor(Commons.getColor(R.color.side_menu_text_active));
            matchChildViewHolder.tvInfo.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvTeamName1.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvTeamName2.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvMatchDate.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvMatchState.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvMatchScore.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvMatchState2.setTextColor(mContext.getResources().getColor(R.color.black));
            matchChildViewHolder.tvSubTitle.setTextColor(mContext.getResources().getColor(R.color.black));
        }

        if (match.getFechaEtapa() != null) {
            matchChildViewHolder.tvMatchDate.setText(match.getFechaEtapa());
        }
        if (i1 == 0) {
            matchChildViewHolder.llheader.setVisibility(View.GONE);
        }

        if (i1 == expandableGroup.getItems().size() - 1)
            matchChildViewHolder.view.setVisibility(View.GONE);
        else
            matchChildViewHolder.view.setVisibility(View.VISIBLE);

        Glide.with(mContext).load(match.getBandera1())
                .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                .into(matchChildViewHolder.ivTeamFlag1);

        matchChildViewHolder.tvTeamName1.setText(match.getEquipo1());

        Glide.with(mContext).load(match.getBandera2())
                .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                .into(matchChildViewHolder.ivTeamFlag2);

        matchChildViewHolder.tvTeamName2.setText(match.getEquipo2());
        if (Constant.Key.PROGRESS.equals(match.getEstado())) {
            matchChildViewHolder.tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
        } else if (Constant.Key.FINALIZED.equals(match.getEstado())) {
            matchChildViewHolder.tvMatchScore.setText(match.getGoles1() + "-" + match.getGoles2());
            matchChildViewHolder.tvMatchState.setText(match.getEstado().toUpperCase());
            matchChildViewHolder.tvMatchState2.setText("");
        } else {
            matchChildViewHolder.tvMatchScore.setText(App.getAppContext().getString(R.string.vs));
        }

        matchChildViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchClickListener.onMatchClicked(match.getIdpartido());
            }
        });

        if (match.getEstadio() != null && match.getFecha() != null && match.getEstado() != null) {
            initDataGame(matchChildViewHolder, match);
        }

        matchChildViewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual(Constant.Key.SHARE_GAME, String.valueOf(match.getIdpartido()));
            }
        });

    }

    public void initDataGame(MatchChildViewHolder matchChildViewHolder, Match match) {
//        String state = App.get()
//                .getString(R.string.stadium
//                        , match.getEstadio().replace("Estadio:", " "));
//        String state2;
//        matchChildViewHolder.tvMatchState
//                .setText(state);
//        if (match.getInfo() != null) {
//            state2 = Commons.getStringDate2(match.getFecha()).replace(".", "")
//                    + "  |  " + Commons.getStringHour(match.getFecha()).replace(".", "")
//                    + match.getInfo();
//        } else {
//            state2 = Commons.getStringDate2(match.getFecha()).replace(".", "")
//                    + "  |  " + Commons.getStringHour(match.getFecha()).replace(".", "");
//        }
//        matchChildViewHolder.tvMatchState2
//                .setText(state2.toUpperCase());

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
        } else {
            matchChildViewHolder.tvMatchState2
                    .setText("Por definir");
        }
    }

    @Override
    public void onBindGroupViewHolder(MatchParentViewHolder matchParentViewHolder, int i, ExpandableGroup expandableGroup) {
        matchParentViewHolder.setTvTittle(expandableGroup);
    }

    public void setMatchClickListener(MatchClickListener matchClickListener) {
        this.matchClickListener = matchClickListener;
    }

    public interface MatchClickListener {

        void onMatchClicked(int idPartido);

        void onDestacado(int position);
    }

}