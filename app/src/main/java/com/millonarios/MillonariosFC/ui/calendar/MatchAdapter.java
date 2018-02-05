package com.millonarios.MillonariosFC.ui.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.Match;
import com.millonarios.MillonariosFC.ui.calendar.viewholder.MatchChildViewHolder;
import com.millonarios.MillonariosFC.ui.calendar.viewholder.MatchParentViewHolder;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
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


    public MatchAdapter(Context context, List<? extends ExpandableGroup> groups) {
        super(groups);
        mContext = context;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public MatchParentViewHolder onCreateGroupViewHolder(ViewGroup viewGroup, int i) {
        View view = inflate.inflate(R.layout.list_item_match_parent, viewGroup, false);
        return new MatchParentViewHolder(view);
    }

    @Override
    public MatchChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup, int i) {
        View view = inflate.inflate(R.layout.item_match, viewGroup, false);
        return new MatchChildViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(MatchChildViewHolder matchChildViewHolder, int i, ExpandableGroup expandableGroup, int i1) {
        final Match match = (Match) expandableGroup.getItems().get(i1);

       /* if (match.isNewDate()) {
            matchChildViewHolder.llheader.setVisibility(View.VISIBLE);
        } else {
            matchChildViewHolder.llheader.setVisibility(View.GONE);
        }

        if (match.isNewDateHeader()) {
            matchChildViewHolder.llSubHeader.setVisibility(View.VISIBLE);
            matchChildViewHolder.tvSubTitle.setText(match.getNewDateHeaderTitle());
        } else {
            matchChildViewHolder.llSubHeader.setVisibility(View.GONE);
            matchChildViewHolder.tvSubTitle.setText("");
        } */

        matchChildViewHolder.tvMatchDate.setText(Commons.getStringDate(match.getFechaEtapa()));

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

        /*if (match.getInfo() != null) {
            matchChildViewHolder.tvInfo.setText(match.getInfo());
        } else {
            matchChildViewHolder.tvInfo.setVisibility(View.GONE);
        }*/

    }

    public void initDataGame(MatchChildViewHolder matchChildViewHolder, Match match) {

        String state = App.get()
                .getString(R.string.stadium
                        , match.getEstadio().replace("Estadio:", " "));
        String state2;

        matchChildViewHolder.tvMatchState
                .setText(state);

        if (match.getInfo() != null) {
            state2 = Commons.getStringDate2(match.getFecha())
                    + "  |  " + Commons.getStringHour(match.getFecha())
                    + match.getInfo();
        } else {
            state2 = Commons.getStringDate2(match.getFecha())
                    + "  |  " + Commons.getStringHour(match.getFecha());
        }

        matchChildViewHolder.tvMatchState2
                .setText(state2);

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