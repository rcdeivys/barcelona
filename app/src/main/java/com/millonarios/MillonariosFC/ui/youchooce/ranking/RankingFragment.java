package com.millonarios.MillonariosFC.ui.youchooce.ranking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.eventbus.ChooseOpenEvent;
import com.millonarios.MillonariosFC.eventbus.ChooseUpdateRanEvent;
import com.millonarios.MillonariosFC.models.RespuestaData;
import com.millonarios.MillonariosFC.ui.home.menu.team.mossapplauded.ApplaudedFragment;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfileActivity;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.di.DaggerRankingComponent;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.di.RankingModule;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.mvp.RankingContract;
import com.millonarios.MillonariosFC.ui.youchooce.ranking.mvp.RankingPresenter;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 09/01/2018.
 */

public class RankingFragment extends BaseFragment implements RankingContract.View, RankingAdapter.OnItemClickListener {

    private static final String TAG = ApplaudedFragment.class.getSimpleName();
    @BindView(R.id.rvRanking)
    RecyclerView rvRanking;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    Unbinder unbinder;
    @Inject
    RankingPresenter presenter;
    private RankingAdapter mRankingAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private String messageToast = "";
    private boolean visible = false;
    private boolean show = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        visible = isVisibleToUser;
        if (!messageToast.equals("")) {
            showDialog(messageToast);
            messageToast = "";
        } else {
            if (swipeContainer != null) {
                presenter.getRanking(show);
            }
        }
    }


    public static RankingFragment newInstance() {

        Bundle args = new Bundle();

        RankingFragment fragment = new RankingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void initComponent() {
        DaggerRankingComponent.builder()
                .appComponent(App.get().component())
                .rankingModule(new RankingModule(this))
                .build().inject(RankingFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        EventBus.getDefault().register(this);
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ranking, container, false);
        unbinder = ButterKnife.bind(this, view);
        unbinder = ButterKnife.bind(this, view);


        presenter.onAttach(this);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                presenter.getRanking(show);
            }
        });

        if (mRankingAdapter == null) {
            showProgress();
            presenter.getRanking(show);
        } else {
            rvRanking.setAdapter(mRankingAdapter);
            rvRanking.setLayoutManager(mLinearLayoutManager);
        }

        return view;
    }

    @Override
    public void onClickItem(int id) {
        navigateToChooseProfileActivity(id);
    }

    public void navigateToChooseProfileActivity(int idRespuesta) {
        Intent intent = new Intent(getActivity(), ChooseProfileActivity.class);
        intent.putExtra(Constant.Key.ID_RESPUESTA, idRespuesta);
        intent.putExtra(Constant.Key.SHOW_VOTES, true);
        navigator.navigateToActivity(intent);
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
    public void noShowVotes() {
        if (!visible) {
            messageToast = App.getAppContext().getString(R.string.ranking_no_show);
        } else {
            showDialog(App.getAppContext().getString(R.string.ranking_no_show));
        }

        setRefreshing(false);
        hideProgress();
    }


    public void showDialog(String message) {

        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ok, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        alertDialog.setCancelable(false);
        FCMillonariosTextView fcMillonariosTextView = (FCMillonariosTextView) dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText(message);
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_ok);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChooseOpenEvent("1"));
                alertDialog.dismiss();

            }
        });


    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToastError(String error) {
        setRefreshing(false);
        hideProgress();
        showToast(error, Toast.LENGTH_LONG);
    }

    @Override
    public void setVotesData(List<RespuestaData> mPlayersVotes) {
        initRvAndAdapter();
        mRankingAdapter.setData(mPlayersVotes);
        notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(getActivity());
        super.onStop();
    }

    private void initRvAndAdapter() {
        if (mRankingAdapter == null) {
            mRankingAdapter = new RankingAdapter(this);
            rvRanking.setAdapter(mRankingAdapter);
            rvRanking.setLayoutManager(mLinearLayoutManager);
        }

    }

    private void notifyDataSetChanged() {
        setRefreshing(false);
        hideProgress();
        mRankingAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(ChooseUpdateRanEvent event) {
        Log.i("tag", "--->onMessageEvent update ranking");
        if (event.isUpdateRanking()) {
            Log.i("tag", "--->onMessageEvent update fragment 2");
            show = true;
            presenter.getRanking(true);
        }
    }


}
