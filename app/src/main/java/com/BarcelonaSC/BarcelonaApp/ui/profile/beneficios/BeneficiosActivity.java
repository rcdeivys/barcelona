package com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.commons.VideoActivity;
import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.adapter.BeneficiosAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.di.BeneficiosModule;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.di.DaggerBeneficiosComponent;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.mvp.BeneficiosContract;
import com.BarcelonaSC.BarcelonaApp.ui.profile.beneficios.mvp.BeneficiosPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class BeneficiosActivity extends BaseActivity implements BeneficiosContract.View, BeneficiosAdapter.OnItemClickListener {


    @BindView(R.id.btn_top)
    ImageButton btnTop;

    @BindView(R.id.beneficios_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.beneficios_rv)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    @BindView(R.id.ib_return)
    ImageButton ibReturn;

    private LinearLayoutManager mLayoutManager;

    private BeneficiosAdapter beneficiosAdapter;

    @Inject
    public BeneficiosPresenter presenter;

    Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beneficios);
        unbinder = ButterKnife.bind(this);
        tvSubHeaderTitle.setText("BENEFICIOS DORADOS");
        ibReturn.setVisibility(View.VISIBLE);
        initComponent();
        initRecyclerView();
    }

    public void initComponent() {
        DaggerBeneficiosComponent.builder()
                .appComponent(App.get().component())
                .beneficiosModule(new BeneficiosModule(this))
                .build().inject(BeneficiosActivity.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        List<BeneficioData> itemList = new ArrayList<>();
        beneficiosAdapter = new BeneficiosAdapter(itemList, this);
        beneficiosAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(beneficiosAdapter);
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                refresh();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
        presenter.loadBeneficios();
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    //refresh(current_page);
                    //progressBar.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    @Override
    public void updateBeneficios(List<BeneficioData> beneficios) {
        beneficiosAdapter.updateAll(beneficios);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailedUpdate() {
        Toast.makeText(getBaseContext(), "NO SE PUDO CONTACTAR AL SERVIDOR", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refresh() {
        presenter.loadBeneficios();
    }

    @Override
    public void onClickItem(BeneficioData beneficio, int currentVideo) {
        if (beneficio != null) {
            if (beneficio.getTipo().equals("Video")) {
                navigateToVideoBeneficiosActivity(beneficio, currentVideo);
            } else {
                Intent intent = new Intent(getBaseContext(), ShowBeneficioActivity.class);
                intent.putExtra(ShowBeneficioActivity.SET_IMAGE, beneficio.getUrl());
                intent.putExtra(ShowBeneficioActivity.SET_DESCRIPCION, beneficio.getDescripcion());
                intent.putExtra(ShowBeneficioActivity.SET_TIPO, beneficio.getTipo());
                intent.putExtra(ShowBeneficioActivity.SET_LINK, beneficio.getLink());
                intent.putExtra(ShowBeneficioActivity.SET_TITULO, beneficio.getTitulo());
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.ib_return)
    public void onViewClicked() {
        finish();
    }

    public void navigateToVideoBeneficiosActivity(BeneficioData beneficioData, int currentVideo) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, currentVideo);
        intent.putExtra(Constant.Video.PLAY, true);
        intent.putExtra(Constant.Video.URL, beneficioData.getLink());
        intent.putExtra(Constant.Key.ID, "" + beneficioData.getId());
        startActivity(intent);
    }

}