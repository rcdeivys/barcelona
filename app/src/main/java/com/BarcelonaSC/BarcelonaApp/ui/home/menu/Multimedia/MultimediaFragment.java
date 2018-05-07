package com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.Services.ShareBaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MultimediaFragment extends ShareBaseFragment {

    public static final String TAG = MultimediaFragment.class.getSimpleName();
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    Unbinder unbinder;
    int tabCount;

    String selected;

    private MultimediaViewPagerAdapter viewPagerAdapter;

    public MultimediaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multimedia, container, false);
        unbinder = ButterKnife.bind(this, view);
        initializeViewPager();
        return view;
    }

    private void initializeViewPager() {

        int Numboftabs = 2;

        /** CAMBIAR POR EL CODIGO DEL TITULO**/
        String[] titles = {ConfigurationManager.getInstance().getConfiguration().getTit_17_1()
                , ConfigurationManager.getInstance().getConfiguration().getTit_17_2()};

        viewPagerAdapter = new MultimediaViewPagerAdapter(getChildFragmentManager(), titles, Numboftabs);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setupWithViewPager(pager);

        tabCount = tabs.getTabCount();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    View view = getActivity().getCurrentFocus();
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    ((HomeActivity) getActivity()).shareSection();
                } else {
                    ((HomeActivity) getActivity()).notShareSection();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        for (int i = 0; i < tabCount; i++) {
            TabLayout.Tab tab = tabs.getTabAt(i);

            View tabView = ((ViewGroup) tabs.getChildAt(0)).getChildAt(i);
            tabView.requestLayout();

            View view = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
            TextView tabTitle = view.findViewById(R.id.ct_title);
            tabTitle.setText(titles[i]);

            tab.setCustomView(view);
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

    public void setSelection(String selected) {
        this.selected = selected;
        setCurrentPage();
    }

    public void setCurrentPage() {
        if (pager != null)
            if (selected.equals(Constant.Menu.IN_LIVE)) {
                pager.setCurrentItem(1);
            } else {
                pager.setCurrentItem(0);
            }
    }

    @Override
    public void share() {
        ((ShareBaseFragment) viewPagerAdapter.getItem(tabs.getSelectedTabPosition())).share();
    }
}
