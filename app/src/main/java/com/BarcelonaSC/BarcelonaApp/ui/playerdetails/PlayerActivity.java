package com.BarcelonaSC.BarcelonaApp.ui.playerdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTabLayout;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Carlos on 13/10/2017.
 */

public class PlayerActivity extends BaseActivity {

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

    private PlayerPagerAdapter viewPagerAdapter;

    private int playerId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        ButterKnife.bind(this);
        tvSubHeaderTitle.setText("");
        initializeViewPager();
        ibReturn.setVisibility(View.VISIBLE);
        super.initBanner(BannerView.Seccion.TEAM);
    }

    @OnClick(R.id.ib_sub_header_share)
    void sharePlayerProfile() {
        if (getIntent().getExtras().getString(Constant.Key.TYPE, "").equals(Constant.Key.GAME_FB)) {
            ShareSection.share(getActivity(), "futbolbase");
        } else {
            ShareSection.shareIndividual(Constant.Key.SHARE_PLAYER, String.valueOf(playerId));
        }
    }

    @OnClick(R.id.ib_return)
    void returnPreviousActivity() {
        onBackPressed();
    }

    public void initSubToolBar(String name) {
        tvSubHeaderTitle.setText(name);
        ibShare.setVisibility(View.VISIBLE);
    }

    private void initializeViewPager() {

        int Numboftabs = 2;

        playerId = getIntent().getExtras().getInt(Constant.Key.PLAYER_ID, 0);
        String type = getIntent().getExtras().getString(Constant.Key.TYPE, "");
        String[] titles = new String[2];

        if (!type.equals(Constant.Key.GAME_FB)) {
            titles[0] = ConfigurationManager.getInstance().getConfiguration().getTit611();
            titles[1] = ConfigurationManager.getInstance().getConfiguration().getTit612();
        } else {
            try {
                titles[0] = ConfigurationManager.getInstance().getConfiguration().getTit1421();
                titles[1] = ConfigurationManager.getInstance().getConfiguration().getTit1422();
            }catch (Exception e) {
                titles[0] = "";
                titles[1] = "";
            }
        }


        viewPagerAdapter = new PlayerPagerAdapter(getSupportFragmentManager(), titles, Numboftabs, playerId, type);

        pager.setPagingEnabled(true);
        pager.setAdapter(viewPagerAdapter);

        tabs.setVisibility(View.VISIBLE);
        tabs.setupWithViewPager(pager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}