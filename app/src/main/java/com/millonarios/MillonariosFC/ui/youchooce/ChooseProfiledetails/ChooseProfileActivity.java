package com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.ConfigurationManager;
import com.millonarios.MillonariosFC.commons.BaseActivity;
import com.millonarios.MillonariosFC.ui.youchooce.ChooseProfiledetails.ChooseProfile.ChooseProfileFragment;
import com.millonarios.MillonariosFC.utils.BannerView;
import com.millonarios.MillonariosFC.utils.Constants.Constant;
import com.millonarios.MillonariosFC.utils.CustomViewPager;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Carlos on 13/10/2017.
 */

public class ChooseProfileActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.ib_sub_header_share)
    ImageButton ibShare;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_profile);
        ButterKnife.bind(this);

        tvSubHeaderTitle.setText(ConfigurationManager.getInstance().getConfiguration().getTit10());
        ibReturn.setVisibility(View.VISIBLE);
        ibReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ChooseProfileFragment chooseProfileFragment = ChooseProfileFragment.newInstance(getIntent().getExtras().getInt(Constant.Key.ID_RESPUESTA)
                , getIntent().getExtras().getBoolean(Constant.Key.SHOW_VOTES));
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, chooseProfileFragment, ChooseProfileFragment.TAG).commit();

        super.initBanner(BannerView.Seccion.YOU_CHOOSE);
    }


    public void initSubToolBar(String name) {
        tvSubHeaderTitle.setText(name);
        ibShare.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}