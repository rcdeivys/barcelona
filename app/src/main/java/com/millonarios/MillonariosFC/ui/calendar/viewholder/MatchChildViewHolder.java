package com.millonarios.MillonariosFC.ui.calendar.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;
//import com.BarcelonaSC.BarcelonaApp.utils.SCBarcelonaTextView;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

/**
 * Created by Erick on 01/11/2017.
 */

public class MatchChildViewHolder extends ChildViewHolder {

    public ImageView ivTeamFlag1;
    public ImageView ivTeamFlag2;
    public TextView tvTeamName1;
    public TextView tvTeamName2;
    public TextView tvMatchScore;
    public TextView tvSubTitle;
    public TextView tvMatchState;
    public TextView tvMatchState2;
    public TextView tvMatchDate;
    public TextView tvInfo;
    public LinearLayout llheader;
    public LinearLayout llSubHeader;
    public View view;

    public MatchChildViewHolder(View itemView) {
        super(itemView);

        view = itemView.findViewById(R.id.v_separador);
        llheader = itemView.findViewById(R.id.ll_header);
        llSubHeader = itemView.findViewById(R.id.ll_sub_header);
        tvSubTitle = itemView.findViewById(R.id.tv_sub_title);
        tvMatchState = itemView.findViewById(R.id.tv_match_state);
        tvMatchState2 = itemView.findViewById(R.id.tv_match_state_2);
        ivTeamFlag1 = itemView.findViewById(R.id.iv_team_1_flag);
        ivTeamFlag2 = itemView.findViewById(R.id.iv_team_2_flag);
        tvTeamName1 = itemView.findViewById(R.id.iv_team_1_name);
        tvTeamName2 = itemView.findViewById(R.id.iv_team_2_name);
        tvMatchDate = itemView.findViewById(R.id.tv_match_date);
        tvInfo = itemView.findViewById(R.id.tv_info);

        tvMatchScore = itemView.findViewById(R.id.tv_match_score);
    }

}