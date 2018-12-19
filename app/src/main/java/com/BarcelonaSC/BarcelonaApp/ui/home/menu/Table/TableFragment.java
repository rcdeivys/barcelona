package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.news.mvp.NewsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableChildFragment;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableSimulatorFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table.adapters.TableViewPagerAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Leonardojpr on 11/5/17.
 */

public class TableFragment extends ShareBaseFragment {

    public static final String TAG = TableFragment.class.getSimpleName();

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;

    @Inject
    public NewsPresenter presenter;

    private TableChildFragment tableChildFragment;
    private TableSimulatorFragment tableSimulatorFragment;
    private TableViewPagerAdapter tableViewPagerAdapter;

    protected String subseccion;

    public static TableFragment newInstance(String subseccion){
        TableFragment fragment = new TableFragment();
        Log.i(TAG, "newInstance: subseccion: ---> "+subseccion);
        try{
            fragment.subseccion = subseccion.toLowerCase();
        }catch (Exception e){
            fragment.subseccion = null;
        }
        return fragment;
    }

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

        tableViewPagerAdapter = new TableViewPagerAdapter(getChildFragmentManager(), getActivity(), tableChildFragment, tableSimulatorFragment);

        pager.setAdapter(tableViewPagerAdapter);
        tabs.setupWithViewPager(pager);

        try {
            switch(subseccion){
                case Constant.SubSeccion.TABLE:
                    pager.setCurrentItem(0);
                    break;
                case Constant.SubSeccion.SIMULATOR:
                    pager.setCurrentItem(1);
                    break;
            }
        }catch (Exception e){
            Log.i(TAG, "initializeViewPager: ---> error: "+e.getLocalizedMessage()+" msg: "+e.getMessage());
        }

    }

    @Override
    public void share() {
        ((ShareBaseFragment) tableViewPagerAdapter.getItem(tabs.getSelectedTabPosition())).share();
    }

}