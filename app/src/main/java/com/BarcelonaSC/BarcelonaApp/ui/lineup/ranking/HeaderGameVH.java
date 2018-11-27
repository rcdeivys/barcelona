package com.BarcelonaSC.BarcelonaApp.ui.lineup.ranking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderGameVH extends RecyclerView.ViewHolder {

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
    @BindView(R.id.ctv_status)
    FCMillonariosTextView status;
    Context context;

    public static HeaderGameVH getInstance(ViewGroup parent) {
        return new HeaderGameVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_game, parent, false));
    }

    HeaderGameVH(View view) {
        super(view);
        context = view.getContext();
        ButterKnife.bind(this, view);
    }

    public void setData(GameSummonedData gameSummonedData) {

        llGame.setBackgroundColor(Commons.getColor(R.color.transparent));
        teamOneName.setText(gameSummonedData.getEquipo1());
        if (gameSummonedData.getBandera2() != null) {
            Glide.with(context)
                    .load(gameSummonedData.getBandera1())
                    .into(teamOneFlag);

        }
        teamTwoName.setText(gameSummonedData.getEquipo2());

        if (gameSummonedData.getBandera2() != null) {
            Glide.with(context)
                    .load(gameSummonedData.getBandera2())
                    .into(teamTwoFlag);
        }


        String result = gameSummonedData.getGoles1() + "-" + gameSummonedData.getGoles2();
        if (gameSummonedData.getEstado().toUpperCase().equals(Constant.Key.PROGRESS) || gameSummonedData.getEstado().toUpperCase().equals(Constant.Key.FINALIZED))
            clash.setText(result);
        else
            clash.setText("Vs");


        // gameType.setText(arrayString[1]);

        //((VHHeader) holder).llGame.setBackgroundColor(Commons.getColor(R.color.background_yellow_app));


        if (gameSummonedData.getEstadio() != null)
            if (!gameSummonedData.getEstadio().equals(""))
                ctv_stadium.setText(App.get().getString(R.string.stadium, gameSummonedData.getEstadio()));

        ctvGameTime.setText(Commons.getStringDateGame(gameSummonedData.getFecha()));

        if (gameSummonedData.getFechaEtapa() != null) {
            dateFifa.setText(gameSummonedData.getFechaEtapa());
        }


        status.setText(gameSummonedData.getEstadio());


    }
}
