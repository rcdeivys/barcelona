package com.BarcelonaSC.BarcelonaApp.ui.chat;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Carlos on 22/01/2018.
 */

public class ChatFragment extends BaseFragment {

    public static final String TAG = ChatFragment.class.getSimpleName();
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    Unbinder unbinder;


    private ChatViewPagerAdapter viewPagerAdapter;

    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        int state ;
        if (isVisibleToUser) {
            state = 1;
        } else
            state = 0;
        FirebaseManager.getInstance().changeUserState(state, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);


        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;

    }

    private void initializeViewPager() {

        int Numboftabs = 4;

        String[] titles = {""};

        viewPagerAdapter = new ChatViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);

        for (int i = 0; i < tabs.getTabCount(); i++) {
            switch (i) {
                case 0:
                    tabs.getTabAt(i).setIcon(R.drawable.tab_icon_messages);
                    break;
                case 1:
                    tabs.getTabAt(i).setIcon(R.drawable.tab_icon_group);
                    break;
                case 2:
                    tabs.getTabAt(i).setIcon(R.drawable.tab_icon_friend);
                    break;
                case 3:
                    tabs.getTabAt(i).setIcon(R.drawable.tab_icon_request);
                    break;
                default:
                    tabs.getTabAt(i).setIcon(R.drawable.tab_icon_messages);
                    break;
            }
            View root = tabs.getChildAt(0);
            if (root instanceof LinearLayout) {
                ((LinearLayout) root).setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setColor(getResources().getColor(R.color.gray_transparent));
                drawable.setSize(2, 1);
                ((LinearLayout) root).setDividerDrawable(drawable);
            }

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

