package com.BarcelonaSC.BarcelonaApp.ui.home.menu.lineup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        int Numboftabs = 2;

        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit71()
                , ConfigurationManager.getInstance().getConfiguration().getTit72()};

        viewPagerAdapter = new LineupViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);
        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);
   /*     tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.colorActiveTextTab);
            }
        });*/
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