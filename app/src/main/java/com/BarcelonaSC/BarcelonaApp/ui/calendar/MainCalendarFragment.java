package com.BarcelonaSC.BarcelonaApp.ui.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Amplex on 14/11/2017.
 */

public class MainCalendarFragment extends BaseFragment {

    private String type;
    Unbinder unbinder;

    protected String subseccion;

    public static MainCalendarFragment newInstance(String type,String subseccion) {
        Bundle args = new Bundle();
        args.putString(Constant.Key.TYPE, type);
        MainCalendarFragment mainCalendarFragment = new MainCalendarFragment();
        mainCalendarFragment.setArguments(args);
        try{
            mainCalendarFragment.subseccion = subseccion.toLowerCase();
        }catch (Exception e){
            mainCalendarFragment.subseccion = null;
        }
        return mainCalendarFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        type = getArguments().getString(Constant.Key.TYPE);
        CalendarFragment calendarFragment = CalendarFragment.newInstance(type,subseccion);
        getFragmentManager().beginTransaction()
                .replace(R.id.cal_container, calendarFragment)
                .commitAllowingStateLoss();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}