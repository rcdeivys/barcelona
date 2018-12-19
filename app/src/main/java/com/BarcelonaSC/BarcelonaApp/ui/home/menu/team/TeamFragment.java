package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 11/10/2017.
 */

public class TeamFragment extends BaseFragment {


    public static final String TAG = TeamFragment.class.getSimpleName();

    @BindView(R.id.pager)
    CustomViewPager pager;

    @BindView(R.id.tabs)
    CustomTabLayout tabs;

    Unbinder unbinder;

    int tabCount;

    private TeamViewPagerAdapter viewPagerAdapter;

    protected String subseccion;

    public static TeamFragment newInstance(String subseccion){
        TeamFragment fragment = new TeamFragment();
        Log.i(TAG, "newInstance: subseccion: ---> "+subseccion);
        try{
            fragment.subseccion = subseccion.toLowerCase();
        }catch (Exception e){
            fragment.subseccion = null;
        }
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_team, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;

    }


    private void initializeViewPager() {
        int Numboftabs = 3;

        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit61()
                , ConfigurationManager.getInstance().getConfiguration().getTit62()
                , ConfigurationManager.getInstance().getConfiguration().getTit63()};

        viewPagerAdapter = new TeamViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);

        tabCount = tabs.getTabCount();

        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);

            View tabView = ((ViewGroup) tabs.getChildAt(0)).getChildAt(i);
            tabView.requestLayout();

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView tabTitle = view.findViewById(R.id.ct_title);
            tabTitle.setText(titles[i]);

            tab.setCustomView(view);
        }

        /*tabs.setDistributeEvenly(true);

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.colorActiveTextTab);
            }
        });*/

        try {
            switch(subseccion){
                case Constant.SubSeccion.NOMINA:
                    pager.setCurrentItem(0);
                    break;
                case Constant.SubSeccion.CONVOCADOS:
                    pager.setCurrentItem(1);
                    break;
                case Constant.SubSeccion.JUGADOR:
                    pager.setCurrentItem(2);
                    break;
            }
        }catch (Exception e){
            Log.i(TAG, "initializeViewPager: ---> error: "+e.getLocalizedMessage()+" msg: "+e.getMessage());
        }

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