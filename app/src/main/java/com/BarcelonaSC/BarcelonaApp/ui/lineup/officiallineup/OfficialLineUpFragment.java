package com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.PlayByPlay;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.di.DaggerOLineUpComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.di.OLineUpModule;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.mvp.OLineUpContract;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.officiallineup.mvp.OLineUpPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.BarcelonaSC.BarcelonaApp.utils.SoccesFieldView;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 12/11/2017.
 */

public class OfficialLineUpFragment extends ShareBaseFragment implements OLineUpContract.View, OfficialLineUpAdapter.OnItemClickListener, SoccesFieldView.PlayerClickListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {

    private static final String TAG = OfficialLineUpFragment.class.getSimpleName();
    @Inject
    OLineUpPresenter presenter;
    @BindView(R.id.rv_players)
    RecyclerView rvPlayers;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    Unbinder unbinder;
    @BindView(R.id.btn_top)
    ImageButton btnTop;
    @BindView(R.id.nsv_lineup)
    NestedScrollView nsvLineup;
    @BindView(R.id.vv_game_lineup)
    VideoView vvGameLineup;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    //game layout
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
    @BindView(R.id.socces_field_view)
    SoccesFieldView soccesFieldView;
    @BindView(R.id.img_play)
    ImageView imgPlay;
    @BindView(R.id.rl_video_container)
    RelativeLayout rlVideoContainer;

    private OfficialLineUpAdapter officialLineUpAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static OfficialLineUpFragment newInstance() {
        Bundle args = new Bundle();

        OfficialLineUpFragment fragment = new OfficialLineUpFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initComponent() {
        DaggerOLineUpComponent.builder()
                .appComponent(App.get().component())
                .oLineUpModule(new OLineUpModule(this))
                .build().inject(OfficialLineUpFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_official_lineup, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.onAttach(this);

        showProgress();
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.getPlayByPlay();
            }
        });

        if (officialLineUpAdapter == null) {
            presenter.getPlayByPlay();
        } else {
            rvPlayers.setAdapter(officialLineUpAdapter);
            rvPlayers.setLayoutManager(linearLayoutManager);
            hideProgress();
        }

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nsvLineup.scrollTo(0, 0);
            }
        });
        rlVideoContainer.setVisibility(View.GONE);
        vvGameLineup.setOnCompletionListener(this);
        vvGameLineup.setOnPreparedListener(this);
        vvGameLineup.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                imgPlay.setVisibility(View.GONE);
                hideProgress();
                //isPaused = true;
                Toast.makeText(getActivity(), "No se pudo reproducir el video", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        imgPlay.setVisibility(View.GONE);

        return view;
    }

    private void initRvAndAdapter() {
        if (officialLineUpAdapter == null) {
            officialLineUpAdapter = new OfficialLineUpAdapter(this);
            rvPlayers.setAdapter(officialLineUpAdapter);
            linearLayoutManager.setAutoMeasureEnabled(true);
            rvPlayers.setLayoutManager(linearLayoutManager);
            rvPlayers.setNestedScrollingEnabled(false);
        }
    }

    private void notifyDataSetChanged() {
        officialLineUpAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setPlayByPlayData(PlayByPlay playByPlayData) {
        Log.i(TAG, "--->" + playByPlayData.toString());
        setGameData(playByPlayData);
        soccesFieldView.initSoccesField(playByPlayData, OfficialLineUpFragment.this);
        initRvAndAdapter();
        officialLineUpAdapter.setData(playByPlayData);
        notifyDataSetChanged();
    }

    @Override
    public void navigateToProfilePlayerActivity(String playerId) {
        Log.i(TAG, "--->Player id" + playerId);
        navigator.navigateToPlayerActivity(Integer.valueOf(playerId), "");
    }

    @Override
    public void setVideo(String url, String info) {
        if (!url.equals("")) {
            vvGameLineup.stopPlayback();
            vvGameLineup.setVideoURI(Uri.parse(url));
            rlVideoContainer.setVisibility(View.VISIBLE);
        } else {
            rlVideoContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDetach();
        unbinder.unbind();

    }

    @Override
    public void onClickItem(int position, boolean isTitular) {
        presenter.onClickItem(position, isTitular);
    }

    @Override
    public void onClickHeader() {

    }

    public void setGameData(PlayByPlay playByPlay) {
        teamOneName.setText(playByPlay.getEquipo1());
        if (playByPlay.getBandera2() != null) {
            Glide.with(this)
                    .load(playByPlay.getBandera1())
                    .into(teamOneFlag);

        }
        teamTwoName.setText(playByPlay.getEquipo2());

        if (playByPlay.getBandera2() != null) {
            Glide.with(this)
                    .load(playByPlay.getBandera2())
                    .into(teamTwoFlag);
        }
        if (!playByPlay.getEstado().equals("Pendiente")) {
            String result = playByPlay.getGoles1() + "-" + playByPlay.getGoles2();
            clash.setText(result);
        } else {
            clash.setText(R.string.vs);
        }

        String[] arrayString = playByPlay.getFechaEtapa().split(",");
        dateFifa.setText(playByPlay.getFechaEtapa());

        ctv_stadium.setText("Estadio : " + playByPlay.getEstadio());

        String gameTime;
        if (playByPlay.getInfo() != null) {
            gameTime = App.get().getString(R.string.game_date_time
                    , Commons.getStringDate2(playByPlay.getFecha()).replace(".", "")
                    , Commons.getStringHour(playByPlay.getFecha()))
                    + playByPlay.getInfo();
        } else {
            gameTime = App.get().getString(R.string.game_date_time
                    , Commons.getStringDate2(playByPlay.getFecha()).replace(".", "")
                    , Commons.getStringHour(playByPlay.getFecha()));
        }

        ctvGameTime.setText(gameTime.toUpperCase());/*+ " (" + playByPlay.getEstado().toUpperCase() + ")"*/

    }

    @Override
    public void onPlayerClickListener(String idPlayer, String namePlayer) {
        navigateToProfilePlayerActivity(idPlayer);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.setOnSeekCompleteListener(this);
        imgPlay.setVisibility(View.VISIBLE);
        mp.seekTo(1);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnSeekCompleteListener(this);
        imgPlay.setVisibility(View.VISIBLE);
        rlVideoContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imgPlay.getVisibility() == View.VISIBLE) {
                    imgPlay.setVisibility(View.GONE);
                    vvGameLineup.start();
                } else {
                    imgPlay.setVisibility(View.VISIBLE);
                    vvGameLineup.pause();
                }
            }
        });
        mp.seekTo(vvGameLineup.getCurrentPosition() + 1);
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    @Override
    public void share() {
        new ShareSection(App.getAppContext()).share(App.getAppContext(), ConfigurationManager.getInstance().getConfiguration().getTit7());
        Log.i(TAG, "--->OFICIAL LINE UP");
    }

    @Override
    public void onPause() {
        super.onPause();
        vvGameLineup.canPause();
        imgPlay.setVisibility(View.VISIBLE);
    }
}