package com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.GameSummonedData;
import com.BarcelonaSC.BarcelonaApp.models.IdealElevenData;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.UtilDragAnDrop;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.dragAndDropPlayer.DragDropPlayerDialogFragment;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.di.DaggerIdealElevenComponent;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.di.IdealElevenModule;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.mvp.IdealElevenContract;
import com.BarcelonaSC.BarcelonaApp.ui.lineup.idealeleven.mvp.IdealElevenPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.BarcelonaSC.BarcelonaApp.utils.SoccesFieldView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 12/11/2017.
 */

public class IdealElevenFragment extends ShareBaseFragment implements IdealElevenContract.View, SoccesFieldView.PlayerClickListener, DragDropPlayerDialogFragment.PlayerDropListener {

    private static final String TAG = IdealElevenFragment.class.getSimpleName();
    @Inject
    IdealElevenPresenter presenter;

    Unbinder unbinder;

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

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressbar)
    ProgressBar progressbar;
    @BindView(R.id.socces_field_view)
    FrameLayout soccesFieldView;
    @BindView(R.id.btn_ok)
    Button btnOk;

    List<CircleImageView> circleImageViews = new ArrayList<>();
    List<IdealElevenData> idealElevenData = new ArrayList<>();

    private String messageToast = "";
    private boolean visible = false;


    public static IdealElevenFragment newInstance() {

        Bundle args = new Bundle();

        IdealElevenFragment fragment = new IdealElevenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initComponent() {
        DaggerIdealElevenComponent.builder()
                .appComponent(App.get().component())
                .idealElevenModule(new IdealElevenModule(this))
                .build().inject(IdealElevenFragment.this);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
        if (!messageToast.equals("")) {
            showToast(messageToast);
            messageToast = "";
        }
    }

    @Override
    public void showShareDialog(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ideal_eleven_share, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = (FCMillonariosTextView) dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText(message);
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
                alertDialog.dismiss();
            }
        });

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
        if (!visible) {
            messageToast = message;
        } else {
            showToast(message, Toast.LENGTH_LONG);
        }
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setIdealEleven(List<IdealElevenData> idealElevenDatas) {
        this.idealElevenData.clear();
        this.idealElevenData = idealElevenDatas;
        UtilDragAnDrop.paintPlayerDrop(circleImageViews, soccesFieldView, idealElevenData, getContext());
        hideProgress();
    }


    @Override
    public void setPlayByPlayData(GameSummonedData playByPlayData) {

        presenter.getIdealEleven();

        setGameData(playByPlayData);
        setRefreshing(false);
        hideProgress();

    }

    @Override
    public void navigateToProfilePlayerActivity(String playerId) {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ideal_evelen, container, false);


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
        presenter.getPlayByPlay();
        //       myimage4.setOnTouchListener(new MyTouchListener());

       /* llPrueba.setOnDragListener(new MyDragListener());*/

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    public void setGameData(GameSummonedData playByPlay) {
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

        String result = playByPlay.getGoles1() + "-" + playByPlay.getGoles2();
        if (playByPlay.getEstado().toUpperCase().equals(Constant.Key.FINALIZED) || playByPlay.getEstado().toUpperCase().equals(Constant.Key.PROGRESS))
            clash.setText(result);
        else {
            clash.setText("VS");
        }

        String[] arrayString = playByPlay.getFechaEtapa().split(",");
        dateFifa.setText(playByPlay.getFechaEtapa());

        ctv_stadium.setText(App.get().getString(R.string.stadium, playByPlay.getEstadio()));

        String gameTime;
        if (playByPlay.getInfo() != null) {
            gameTime = App.get().getString(R.string.game_date_time
                    , Commons.getStringDate2(playByPlay.getFecha())
                    , Commons.getStringHour(playByPlay.getFecha()))
                    + playByPlay.getInfo();
        } else {
            gameTime = App.get().getString(R.string.game_date_time
                    , Commons.getStringDate2(playByPlay.getFecha())
                    , Commons.getStringHour(playByPlay.getFecha()));
        }

        ctvGameTime.setText(gameTime);

    }

    @Override
    public void onPlayerClickListener(String idPlayer, String namePlayer) {

    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        FragmentTransaction ft = (getActivity()).getSupportFragmentManager().beginTransaction();
        DragDropPlayerDialogFragment dialog = DragDropPlayerDialogFragment.newInstance();
        if (idealElevenData.size() == 0)
            dialog.setPlayerList(presenter.getPlayerSummonedData(), this);
        else {
            dialog.setPlayerList(presenter.getPlayerSummonedData(), idealElevenData, this);
        }
        dialog.show(ft, DragDropPlayerDialogFragment.TAG);

    }


    @Override
    public void onPlayerDropSave(List<IdealElevenData> idealElevenData, String imageEncode, Boolean saveForLate) {
        this.idealElevenData.clear();
        this.idealElevenData = idealElevenData;
        if (!saveForLate) {
            presenter.setIdealEleven(idealElevenData, imageEncode);
            showProgress();
        }
    }


    @Override
    public void share() {
        Log.i(TAG, "---> ideal eleven");
        if (!new SessionManager(App.getAppContext()).getUrlLineUpShare().equals("")) {
            new ShareSection(App.getAppContext()).shareIdealEleven(new SessionManager(App.getAppContext()).getUrlLineUpShare());
        } else {
            showToast(App.getAppContext().getString(R.string.ideal_eleven), Toast.LENGTH_LONG);
        }
    }

}
