package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.PlayByPlay;
import com.BarcelonaSC.BarcelonaApp.models.PlayerPlayByPlay;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 12/11/2017.
 */

public class SoccesFieldView extends RelativeLayout {


    @BindView(R.id.player1)
    CircleImageView player1;
    @BindView(R.id.player2)
    CircleImageView player2;
    @BindView(R.id.player3)
    CircleImageView player3;
    @BindView(R.id.player4)
    CircleImageView player4;
    @BindView(R.id.player5)
    CircleImageView player5;
    @BindView(R.id.player6)
    CircleImageView player6;
    @BindView(R.id.player7)
    CircleImageView player7;
    @BindView(R.id.player8)
    CircleImageView player8;
    @BindView(R.id.player9)
    CircleImageView player9;
    @BindView(R.id.player10)
    CircleImageView player10;
    @BindView(R.id.player11)
    CircleImageView player11;

    private List<CircleImageView> players;
    private PlayByPlay playByPlay;
    private PlayerClickListener playerClickListener;

    public SoccesFieldView(Context context) {
        super(context);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_2, null);
        addView(root);
    }

    public SoccesFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_2, null);
        addView(root);
    }

    public SoccesFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_2, null);
        addView(root);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SoccesFieldView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        View root = null;
        root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_2, null);
        addView(root);
    }


    public void initSoccesField(PlayByPlay playByPlay, PlayerClickListener playerClickListener) {
        this.playerClickListener = playerClickListener;
        View root = null;
        this.playByPlay = playByPlay;
        this.removeAllViews();
        // TODO: 15/11/2017 Revisar esto
        // if (playByPlay.getFormacion().equals("Casa")) {
        if (playByPlay.getFormacion().equals("4-4-2"))
            root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_2, null);
        else if (playByPlay.getFormacion().equals("4-3-3"))
            root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_3_3, null);
        else if (playByPlay.getFormacion().equals("4-4-1-1"))
            root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_4_1_1, null);
        else if (playByPlay.getFormacion().equals("4-5-1"))
            root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_4_5_1, null);
      /*  } else {
            if (playByPlay.getFormacion().equals("4-4-2"))
                root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_visit_4_4_2, null);
            else if (playByPlay.getFormacion().equals("4-3-3"))
                root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_visit_4_3_3, null);
            else if (playByPlay.getFormacion().equals("4-4-1-1"))
                root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_visit_4_4_1_1, null);
            else if (playByPlay.getFormacion().equals("4-5-1"))
                root = LayoutInflater.from(getContext()).inflate(R.layout.view_formation_visit_4_5_1, null);
        } */


        addView(root);
        ButterKnife.bind(this, root);
        listViewItem();
        formation(playByPlay.getTitulares(), playByPlay.getSuplentes());
    }

    public void listViewItem() {
        players = new ArrayList<>();

        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
        players.add(player5);
        players.add(player6);
        players.add(player7);
        players.add(player8);
        players.add(player9);
        players.add(player10);
        players.add(player11);
    }

    private void formation(List<PlayerPlayByPlay> titularList, List<PlayerPlayByPlay> suplentesList) {


        for (final PlayerPlayByPlay titular : titularList) {
            for (int i = 1; i <= 11; i++) {
                if (titular.getPosicion() == i) {
                    players.get(i - 1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playerClickListener.onPlayerClickListener(String.valueOf(titular.getIdjugador()), titular.getNombre());
                        }
                    });
                    setImagePlayer(players.get(i - 1), titular.getFoto());
                }
            }
        }
       /* for (final PlayerPlayByPlay suplentes : suplentesList) {
            for (int i = 1; i <= 11; i++) {
                if (Integer.parseInt(suplentes.getPosicionCampo()) == 1) {
                    players.getPlayByPlay(i - 1).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            playerClickListener.onPlayerClickListener(suplentes.getIdjugador(), suplentes.getNombre());
                        }
                    });
                    setImagePlayer(players.getPlayByPlay(i - 1), suplentes.getFoto());
                }
            }
        }*/

    }


    private void setImagePlayer(CircleImageView circleImageView, String url) {

        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(circleImageView);

    }

    public interface PlayerClickListener {

        void onPlayerClickListener(String idPlayer, String namePlayer);
    }

    public void rotateLayout() {
        for (CircleImageView player : players) {
            player.setRotation(90);
        }

    }

}
