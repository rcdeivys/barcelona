package com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.fragments.monumental.profile.adapters.MonumentalProfilePagerAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by RYA-Laptop on 19/02/2018.
 */

public class MonumentalProfileActivity extends BaseActivity {

    @BindView(R.id.tabs)
    CustomTabLayout tabs;
    @BindView(R.id.pager)
    CustomViewPager pager;
    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.ib_sub_header_share)
    ImageButton ibShare;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    private MonumentalProfilePagerAdapter viewPagerAdapter;
    Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_monumental);
        unbinder = ButterKnife.bind(this);

        tvSubHeaderTitle.setText("Detalle Monumental");
        ibReturn.setVisibility(View.VISIBLE);
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        initializeViewPager();
    }

    public void initSubToolBar(String name) {
        tvSubHeaderTitle.setText(name);
        ibShare.setVisibility(View.INVISIBLE);
    }

    private void initializeViewPager() {
        int Numboftabs = 2;
        String monumental = getIntent().getExtras().getString(Constant.Key.MONUMETAL_ID);
        String survey = getIntent().getExtras().getString(Constant.Key.SURVEY_ID);
        String[] titles = {"Perfil Monumental", "Redes Sociales"};

        viewPagerAdapter = new MonumentalProfilePagerAdapter(getSupportFragmentManager(), titles, Numboftabs, monumental, survey);
        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);
        tabs.setVisibility(View.VISIBLE);
        tabs.setupWithViewPager(pager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}