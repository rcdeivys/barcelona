package com.BarcelonaSC.BarcelonaApp.ui.futbolbase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Amplex on 13/11/2017.
 */

public class FutbolBaseFragment extends BaseFragment {

    public static final String TAG = FutbolBaseFragment.class.getSimpleName();
    @BindView(R.id.tabs)
    CustomTabLayout tabs;

    @BindView(R.id.pager)
    CustomViewPager pager;
    Unbinder unbinder;

    private FutbolBasePagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_futbol_base, container, false);
        unbinder = ButterKnife.bind(this, view);

        initializeViewPager();

        return view;
    }

    private void initializeViewPager() {
        //int numbOfTabs = 3;
        int numbOfTabs = 2;

//        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit2()
//                , ConfigurationManager.getInstance().getConfiguration().getTit6()
//                , ConfigurationManager.getInstance().getConfiguration().getTit3()};
        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit2()
                , ConfigurationManager.getInstance().getConfiguration().getTit6()};

        viewPagerAdapter = new FutbolBasePagerAdapter(getChildFragmentManager(), titles, numbOfTabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        //pager.setOffscreenPageLimit(3);
        pager.setOffscreenPageLimit(2);
        tabs.setupWithViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}