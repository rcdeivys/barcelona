package com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
//import com.BarcelonaSC.BarcelonaApp.utils.SCBarcelonaTextView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Erick on 01/11/2017.
 */

public class MatchChildViewHolder extends ChildViewHolder {

    @BindView(R.id.content_game_calendar)
    public LinearLayout contentGameCalendar;

    @BindView(R.id.iv_team_1_flag)
    public ImageView ivTeamFlag1;

    @BindView(R.id.iv_team_2_flag)
    public ImageView ivTeamFlag2;

    @BindView(R.id.iv_team_1_name)
    public TextView tvTeamName1;

    @BindView(R.id.iv_team_2_name)
    public TextView tvTeamName2;

    @BindView(R.id.tv_match_score)
    public TextView tvMatchScore;

    @BindView(R.id.tv_sub_title)
    public TextView tvSubTitle;

    @BindView(R.id.tv_match_state)
    public TextView tvMatchState;

    @BindView(R.id.tv_match_state_2)
    public TextView tvMatchState2;

    @BindView(R.id.tv_match_date)
    public TextView tvMatchDate;

    @BindView(R.id.tv_info)
    public TextView tvInfo;

    @BindView(R.id.ll_header)
    public LinearLayout llheader;

    @BindView(R.id.ll_sub_header)
    public LinearLayout llSubHeader;

    @BindView(R.id.v_separador)
    public View view;

    @BindView(R.id.iv_share)
    public ImageView ivShare;

    public MatchChildViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }


}