package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableChildFragment;
import com.BarcelonaSC.BarcelonaApp.ui.table.fragments.TableSimulatorFragment;

/**
 * Created by Leonardojpr on 11/5/17.
 */

public class TableViewPagerAdapter extends FragmentStatePagerAdapter {

    private final int tabsNumber = 2;
    private Context context;

    TableChildFragment tableChildFragment;
    TableSimulatorFragment simulatorFragment;

    public TableViewPagerAdapter(FragmentManager fm, Context context, TableChildFragment tableChildFragment, TableSimulatorFragment simulatorFragment) {
        super(fm);
        this.context = context.getApplicationContext();
        this.tableChildFragment = tableChildFragment;
        this.simulatorFragment = simulatorFragment;
    }

    @Override
    public Fragment getItem(int position) {


        switch (position) {
            case 0:
                return tableChildFragment;
            case 1:
                return simulatorFragment;
            default:
                return tableChildFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return ConfigurationManager.getInstance().getConfiguration().getTit41();
            case 1:
                return ConfigurationManager.getInstance().getConfiguration().getTit42();
            default:
                return "Table";
        }

    }

    @Override
    public int getCount() {
        return tabsNumber;
    }
}
