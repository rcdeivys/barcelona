package com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.eventbus.ChooseOpenEvent;
import com.BarcelonaSC.BarcelonaApp.models.EncuestaData;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded.ApplaudedFragment;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.ChooseProfiledetails.ChooseProfileActivity;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.di.DaggerVoteComponent;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.di.VoteModule;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.mvp.VoteContract;
import com.BarcelonaSC.BarcelonaApp.ui.youchooce.vote.mvp.VotePresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ProgressClock;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 09/01/2018.
 */

public class VoteFragment extends BaseFragment implements VoteContract.View, VoteAdapter.OnItemClickListener, ProgressClock.ProgressClockListener {

    private static final String TAG = ApplaudedFragment.class.getSimpleName();
    @BindView(R.id.rvChoose)
    RecyclerView rvChoose;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    Unbinder unbinder;
    @Inject
    VotePresenter presenter;
    @BindView(R.id.progress_clock)
    ProgressClock progressClock;
    @BindView(R.id.ll_vote_container)
    LinearLayout llVoteContainer;
    private VoteAdapter mVoteAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean finisher = false;
    private AlertDialog alertDialog;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (finisher && isVisibleToUser) {
            presenter.getChoose();
        }

    }

    public static VoteFragment newInstance() {
        Bundle args = new Bundle();
        VoteFragment fragment = new VoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initComponent() {
        DaggerVoteComponent.builder()
                .appComponent(App.get().component())
                .voteModule(new VoteModule(this))
                .build().inject(VoteFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.getChoose();
            }
        });

        if (mVoteAdapter == null) {
            showProgress();
            presenter.getChoose();
        } else {
            rvChoose.setAdapter(mVoteAdapter);
            rvChoose.setLayoutManager(mLayoutManager);
        }

        return view;
    }

    @Override
    public void onClickItem(int id) {
        presenter.onclickItem(id);
    }

    @Override
    public void onClickPlayerVote(int posicion, int msj) {
        Log.i(TAG, "/--->onClickPlayerVote");
        presenter.onClickPlayerVote(posicion, msj);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToastError(String error) {
        setRefreshing(false);
        hideProgress();
        if (error.toLowerCase().equals("no puedes votar en esta encuesta")) {
            showToast("Ya se registró tu participación en la votación", Toast.LENGTH_LONG);
        } else if (error.toLowerCase().equals("Votos agregados con exito")) {
            showToast("¡Voto registrado con éxito!", Toast.LENGTH_LONG);
        } else {
            showToast(error, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void setChooseData(EncuestaData mEncuestaData) {
        llVoteContainer.setVisibility(View.VISIBLE);
        finisher = true;
        initRvAndAdapter();
        tvTitle.setText(mEncuestaData.getTitulo());
        mVoteAdapter.setData(mEncuestaData.getRespuestas());
        notifyDataSetChanged();
        progressClock.initclock(mEncuestaData.getFechaInicio(),
                mEncuestaData.getFechaFin(), this);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void showNoEncuentas() {
        llVoteContainer.setVisibility(View.INVISIBLE);
        showToast("En estos momentos no hay votación activa", Toast.LENGTH_SHORT);
        setRefreshing(false);
        hideProgress();
    }

    private void initRvAndAdapter() {
        if (mVoteAdapter == null) {
            mVoteAdapter = new VoteAdapter(this);
            rvChoose.setAdapter(mVoteAdapter);
            rvChoose.setLayoutManager(mLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        setRefreshing(false);
        hideProgress();
        mVoteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void navigateToChooseProfileActivity(int idRespuesta, boolean showVotes) {
        Intent intent = new Intent(getActivity(), ChooseProfileActivity.class);
        intent.putExtra(Constant.Key.ID_RESPUESTA, idRespuesta);
        intent.putExtra(Constant.Key.SHOW_VOTES, showVotes);
        navigator.navigateToActivity(intent);
    }

    @Override
    public void onFinish() {
        showDialog("Votación finalizada");
        setRefreshing(false);
        hideProgress();
        finisher = true;
    }


    public void showDialog(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ok, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText(message);
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_ok);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onFinishEncuesta();
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void showShareVote(final int id) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_share_vote, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        alertDialog = builder.show();

        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_return);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                EventBus.getDefault().post(new ChooseOpenEvent("2", true));
            }
        });

        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_submit);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareSection.shareIndividual("tueliges", String.valueOf(id));
                alertDialog.dismiss();
                EventBus.getDefault().post(new ChooseOpenEvent("2", true));
            }
        });
    }

}
