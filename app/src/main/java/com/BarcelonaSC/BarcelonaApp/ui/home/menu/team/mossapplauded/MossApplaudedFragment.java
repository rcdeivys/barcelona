package com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.mossapplauded;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.eventbus.PlayerEvent;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 11/10/2017.
 */

public class MossApplaudedFragment extends BaseFragment {

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.img_banner)
    ImageView imgBanner;
    @BindView(R.id.Banner)
    RelativeLayout Banner;
    Unbinder unbinder;

    public ApplaudedViewPagerAdapter viewPagerAdapter;

    public static MossApplaudedFragment newInstance() {

        Bundle args = new Bundle();

        MossApplaudedFragment fragment = new MossApplaudedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_most_applauded, container, false);

        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;
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


    private void initializeViewPager() {

        int Numboftabs = 2;

        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit631()
                , ConfigurationManager.getInstance().getConfiguration().getTit632()};

        viewPagerAdapter = new ApplaudedViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);

        tabs.setupWithViewPager(pager);


    }

    // This method will be called when a MessageEvent is posted (in the UI thread for Toast)
    @Subscribe
    public void onMessageEvent(PlayerEvent event) {
        if (event.isUpdate()) {
            viewPagerAdapter.notifyDataSetChanged();
        }
    }
}
