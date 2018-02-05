package com.millonarios.MillonariosFC.ui.home.menu.youchooce;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.commons.Services.ShareBaseFragment;
import com.millonarios.MillonariosFC.eventbus.ChooseOpenEvent;
import com.millonarios.MillonariosFC.utils.CustomTabLayout;
import com.millonarios.MillonariosFC.utils.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 12/11/2017.
 */

public class YouChooseFragment extends ShareBaseFragment {

    public static final String TAG = YouChooseFragment.class.getSimpleName();

    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    Unbinder unbinder;
    String moveRanking = "0";

    private YouChooseViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_youchooce, container, false);


        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;

    }


    private void initializeViewPager() {

        int Numboftabs = 2;


        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit101()
                , ConfigurationManager.getInstance().getConfiguration().getTit102()};

        viewPagerAdapter = new YouChooseViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

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
    public void onStop() {
        EventBus.getDefault().unregister(getActivity());
        super.onStop();
    }

    @Override
    public void share() {
        ((ShareBaseFragment) viewPagerAdapter.getItem(tabs.getSelectedTabPosition())).share();
    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(ChooseOpenEvent event) {
        Log.i("tag", "--->onMessageEvent update fragment");

        if (event.isOpen()) {
            pager.setPagingEnabled(true);
            tabs.setPagingEnabled(true);
        } else {
            pager.setPagingEnabled(false);
            tabs.setPagingEnabled(false);
        }
        if (event.getMove().equals("2")) {
            moveRanking = "2";
            pager.setCurrentItem(1);
        } else if (event.getMove().equals("1")) {
            moveRanking = "1";
            pager.setCurrentItem(0);
            Log.i("tag", "--->onMessageEvent update fragment2 ");
        }

    }
}
