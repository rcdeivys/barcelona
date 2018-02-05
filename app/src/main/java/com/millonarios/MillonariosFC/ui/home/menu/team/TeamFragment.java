package com.millonarios.MillonariosFC.ui.home.menu.team;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.commons.BaseFragment;
import com.millonarios.MillonariosFC.utils.CustomTabLayout;
import com.millonarios.MillonariosFC.utils.CustomViewPager;

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

   /*     tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return ContextCompat.getColor(getApplicationContext(), R.color.colorActiveTextTab);
            }
        });*/


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