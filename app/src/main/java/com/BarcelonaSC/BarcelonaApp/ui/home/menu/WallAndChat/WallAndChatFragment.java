package com.BarcelonaSC.BarcelonaApp.ui.home.menu.WallAndChat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallAndChatFragment extends Fragment {

    public static final String TAG = WallAndChatFragment.class.getSimpleName();

    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    Unbinder unbinder;

    private WallAndChatViewPagerAdapter viewPagerAdapter;

    public static WallAndChatFragment newInstance() {

        Bundle args = new Bundle();

        WallAndChatFragment fragment = new WallAndChatFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall_and_chat, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;
    }


    private void initializeViewPager() {

        viewPagerAdapter = new WallAndChatViewPagerAdapter(getFragmentManager());

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
