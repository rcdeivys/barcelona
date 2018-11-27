package com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.commons.VideoActivity;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.SCalendarData;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.CalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.di.DaggerSCalendarComponent;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.di.SCalendarModule;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp.SCalendarContract;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.singlecalendar.mvp.SCalendarPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.news.views.adapters.NewsAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Erick on 2/11/2017.
 */

public class SingleCalendarListFragment extends BaseFragment implements SCalendarContract.View, NewsAdapter.OnItemClickListener {

    public static final String TAG = SingleCalendarListFragment.class.getSimpleName();
    NewsAdapter newsAdapter;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;

    @BindView(R.id.rv_noticias)
    RecyclerView rvNoticias;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.btn_top)
    ImageButton btnTop;

    @BindView(R.id.iv_team_1_flag)
    ImageView flagOne;

    @BindView(R.id.iv_team_1_name)
    TextView nameOne;

    @BindView(R.id.iv_team_2_flag)
    ImageView flagTwo;

    @BindView(R.id.iv_team_2_name)
    TextView nameTwo;

    @BindView(R.id.tv_match_score)
    TextView matchScore;

    @BindView(R.id.tv_match_date)
    TextView matchDate;

    @BindView(R.id.tv_match_state)
    TextView matchState;

    @BindView(R.id.tv_match_state_2)
    TextView matchState2;

    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;

    @BindView(R.id.ib_share)
    ImageButton ivShare;

    int id_partido;
    String type;
    String news;

    List<Integer> videoPosition;

    @Inject
    SCalendarPresenter presenter;

    Unbinder unbinder;

    public static SingleCalendarListFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        SingleCalendarListFragment singleCalendarListFragment = new SingleCalendarListFragment();
        singleCalendarListFragment.setArguments(args);

        return singleCalendarListFragment;
    }

    public void initComponent() {
        DaggerSCalendarComponent.builder()
                .appComponent(App.get().component())
                .sCalendarModule(new SCalendarModule(SingleCalendarListFragment.this))
                .build().inject(SingleCalendarListFragment.this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoPosition = new ArrayList<>();
        initComponent();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_single_calendar, container, false);
        unbinder = ButterKnife.bind(this, view);

        ((HomeActivity) getActivity()).hideSubMenu();

        presenter.onAttach(this);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
               initRecyclerView();
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("idPartido") && bundle.containsKey("type")) {
            id_partido = bundle.getInt("idPartido");
            type = bundle.getString("type");
            news = bundle.getString(Constant.Menu.NEWS);
            initRecyclerView();
            showProgress();

        }

        btnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rvNoticias.smoothScrollToPosition(0);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (news != null) {
                    onBackFragment(NewsFragment.TAG);
                } else {
                    onBackFragment(CalendarFragment.TAG);
                }


            }
        });

        return view;
    }

    public void onBackFragment(String tag) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.top_in, R.anim.top_out)
                .remove(getFragmentManager().findFragmentByTag(SingleCalendarListFragment.TAG))
                .show(getFragmentManager().findFragmentByTag(tag))
                .commitAllowingStateLoss();
    }

    @OnClick(R.id.ib_share)
    void shareThisGame() {
        ShareSection.shareIndividual(Constant.Key.SHARE_GAME, String.valueOf(id_partido));
    }

    public void initRecyclerView() {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        rvNoticias.setLayoutManager(mLayoutManager);
        List<Object> itemList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), itemList);
        newsAdapter.setOnItemClickListener(this);
        rvNoticias.setAdapter(newsAdapter);
        getData();
    }

    public void getData() {
        if (type.equals(Constant.Key.CUP)) {
            presenter.getNoticias(id_partido);
        } else {
            presenter.getNoticiasFb(id_partido);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((HomeActivity) getActivity()).showSubMenu();
        unbinder.unbind();
        presenter.onDetach();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setRefreshing(boolean state) {
        swipeContainer.setRefreshing(state);
    }

    @Override
    public void showToast(String message) {
        showToast(message, Toast.LENGTH_SHORT);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void setNoticias(SCalendarData data) {
        setHeaderData(data);
        List<Object> objects = new ArrayList<>();
        objects.addAll(data.getNoticias());
        newsAdapter.updateAll(objects);
        setRefreshing(false);
        hideProgress();
    }

    @Override
    public void navigateToInfografiaActivity(News news) {
        navigator.navigateToInfografiaActivity(news);
    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {
        navigator.navigateToNewsDetailsActivity(news);
    }

    @Override
    public void navigateToGalleryActivity(News news) {

    }

    private void setHeaderData(SCalendarData data) {
        Glide.with(getActivity()).load(data.getBandera1())
                .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                .into(flagOne);
        nameOne.setText(data.getEquipo1());

        Glide.with(getActivity()).load(data.getBandera2())
                .apply(new RequestOptions().placeholder(R.drawable.logo_millos).error(R.drawable.logo_millos))
                .into(flagTwo);
        nameTwo.setText(data.getEquipo2());

        matchDate.setText(Commons.getStringDate(data.getFechaEtapa()));
        String score = data.getGoles1() + "-" + data.getGoles2();
        if (Constant.Key.PROGRESS.equals(data.getEstado())) {
            matchScore.setText(score);
        } else if ("Finalizado".equals(data.getEstado())) {
            matchScore.setText(score);
            matchState.setText(data.getEstado().toUpperCase());
            matchState2.setText("");
        } else {
            matchScore.setText(App.getAppContext().getString(R.string.vs));
        }
        if (data.getEstadio() != null) {
            String state = App.get()
                    .getString(R.string.stadium
                            , data.getEstadio().replace("Estadio:", " "));
            matchState.setText(state);
        }
        String date = Commons.getStringDate2(data.getFecha()).replace(".", "");
        String state2 = date.toUpperCase()
                + "  |  " + Commons.getStringHour(data.getFecha());
        /*+ " (" + data.getEstado().toUpperCase() + ")"*/
        matchState2.setText(state2);
    }

    @Override
    public void onClickItem(News news) {
        presenter.onclickNewsItem(news);
    }

    @Override
    public void onVideoClick(News news, int currentVideo) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, currentVideo);
        intent.putExtra(Constant.Video.URL, news.getLink());
        startActivity(intent);
    }

    @Override
    public void playVideo(int position, int id) {
        videoPosition.add(position);
    }

    @Override
    public void onCalendarClick(String id) {

    }

    @Override
    public void onVideoIsDorado() {
//        showDialogDorado();
    }

    @Override
    public void onPause() {
        super.onPause();
        newsAdapter.pauseVideo(videoPosition);
    }
}