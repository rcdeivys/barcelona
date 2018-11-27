package com.BarcelonaSC.BarcelonaApp.ui.home.menu.lineup;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.response.IdealElevenRankingEnableResponse;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Carlos on 12/11/2017.
 */

public class LineUpFragment extends ShareBaseFragment {

    public static final String TAG = LineUpFragment.class.getSimpleName();

    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    Unbinder unbinder;

    private LineupViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lineup, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;
    }

    private void initializeViewPager() {
        int Numboftabs = 3;

        String[] titles = {
                ConfigurationManager.getInstance().getConfiguration().getTit71(),
                ConfigurationManager.getInstance().getConfiguration().getTit72(),
                "Ranking"
        };

        viewPagerAdapter = new LineupViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);
        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);
        eternity();
    }

    public void eternity() {
        final boolean isNull = getActivity() != null ? false : true;
        if (!isNull) {
            App.get().component().idealElevenRankingEnableApi().getEnable().enqueue(new Callback<IdealElevenRankingEnableResponse>() {
                @Override
                public void onResponse(Call<IdealElevenRankingEnableResponse> call, Response<IdealElevenRankingEnableResponse> response) {
                    try {
                        if (response.body().status.equals("exito")) {
                            if (!isNull) {
                                if (!response.body().idealElevenRankingEnableData.ranking_activo) {
                                    tabs.hideTab(2);
                                    pager.setCurrentItem(1);
                                } else
                                    tabs.showTab(2);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
                @Override
                public void onFailure(Call<IdealElevenRankingEnableResponse> call, Throwable t) {

                }
            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    eternity();
                }
            }, 30000);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void share() {
        ((ShareBaseFragment) viewPagerAdapter.getItem(tabs.getSelectedTabPosition())).share();
    }
}