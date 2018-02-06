package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableChildFragment;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableSimulatorFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table.adapters.TableViewPagerAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/5/17.
 */

public class TableFragment extends Fragment {

    public static final String TAG = TableFragment.class.getSimpleName();

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;

    @Inject
    public NewsPresenter presenter;

    private TableChildFragment tableChildFragment;
    private TableSimulatorFragment tableSimulatorFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initViewPager();
    }


    private void initViewPager() {
        tableChildFragment = new TableChildFragment();
        tableSimulatorFragment = new TableSimulatorFragment();

        pager.setAdapter(new TableViewPagerAdapter(getChildFragmentManager(), getActivity(), tableChildFragment, tableSimulatorFragment));
        tabs.setupWithViewPager(pager);
    }

}
