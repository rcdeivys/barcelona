package com.BarcelonaSC.BarcelonaApp.ui.calendar;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.models.Tournament;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.di.CalendarModule;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.di.DaggerCalendarComponent;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp.CalendarContract;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.mvp.CalendarPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.SingleCalendarListFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.viewholder.LeagueGroup;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Erick on 01/11/2017.
 */

public class CalendarFragment extends BaseFragment implements CalendarContract.View, MatchAdapter.MatchClickListener {

    public static final String TAG = CalendarFragment.class.getSimpleName();

    @BindView(R.id.rv_matches)
    RecyclerView rvCalendar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.btn_top)
    ImageButton btnTop;

    private String type;
    private MatchAdapter matchAdapter;
    private LinearLayoutManager linearLayoutManager;
    Unbinder unbinder;

    @Inject
    CalendarPresenter presenter;

    public static CalendarFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        CalendarFragment calendarFragment = new CalendarFragment();
        calendarFragment.setArguments(args);

        return calendarFragment;
    }

    public void initComponent() {
        DaggerCalendarComponent.builder()
                .appComponent(App.get().component())
                .calendarModule(new CalendarModule(this))
                .build().inject(CalendarFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getString(Constant.Key.TYPE);
        linearLayoutManager = new LinearLayoutManager(getContext());
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);

        presenter.onAttach(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                getData();
            }
        });

        if (matchAdapter == null) {
            showProgress();
            getData();
        } else {
            rvCalendar.setAdapter(matchAdapter);
            rvCalendar.setLayoutManager(linearLayoutManager);
        }

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvCalendar.smoothScrollToPosition(0);
            }
        });

        return view;
    }

    public void getData() {
        if (type.equals(Constant.Key.CUP)) {
            presenter.getCup();
        } else if (type.equals(Constant.Key.CUP_FB)) {
            presenter.getCupFb();
        } else {
            presenter.getMatch();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDetach();
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
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_LONG);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setCup(List<Tournament> tournaments) {
        matchAdapter = new MatchAdapter(getContext(), generateLeagueGroup(tournaments));
        matchAdapter.setMatchClickListener(this);
//        if (matchAdapter.getGroups() != null && matchAdapter.getGroups().size() > 0)
//            matchAdapter.toggleGroup(0);
        rvCalendar.setAdapter(matchAdapter);
        rvCalendar.setLayoutManager(linearLayoutManager);
        notifyDataSetChanged();
        if (getActivity() != null)
            if (((HomeActivity) getActivity()).idPartido != 0) {
                onMatchClicked(((HomeActivity) getActivity()).idPartido);
                ((HomeActivity) getActivity()).idPartido = 0;
            }
    }

    private List<LeagueGroup> generateLeagueGroup(List<Tournament> tournaments) {
        List<LeagueGroup> genres = new ArrayList<>();
        for (Tournament tournament : tournaments) {
            genres.add(new LeagueGroup(tournament.getCupName(), tournament.getMatches()));
        }
        return genres;
    }

    private void notifyDataSetChanged() {
        matchAdapter.notifyDataSetChanged();
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void onMatchClicked(int idPartido) {
        App.get().registerCustomTrackScreen(Constant.Analytics.GAME, Integer.toString(idPartido), 3);
        SingleCalendarListFragment singleCalendarListFragment = new SingleCalendarListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idPartido", idPartido);
        bundle.putString("type", type);
        singleCalendarListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, singleCalendarListFragment, SingleCalendarListFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onDestacado(final int position) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rvCalendar.smoothScrollToPosition(position);
            }
        }, 5000);
    }
}