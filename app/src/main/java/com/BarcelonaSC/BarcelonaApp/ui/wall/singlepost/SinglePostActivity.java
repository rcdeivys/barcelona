package com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.WallCommentFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.di.DaggerSinglePostComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.di.SinglePostModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp.SinglePostContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.singlepost.mvp.SinglePostPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import javax.inject.Inject;

/**
 * Created by Leonardojpr on 3/22/18.
 */

public class SinglePostActivity extends BaseActivity implements SinglePostContract.View {

    @Inject
    SinglePostPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar_main);
        initComponent();
        if (getIntent().getStringExtra(Constant.Seccion.Id_Post) != null) {
            presenter.load(getIntent().getStringExtra(Constant.Seccion.Id_Post));
        } else {
            finish();
        }
    }

    public void initComponent() {
        DaggerSinglePostComponent.builder()
                .appComponent(App.get().component())
                .singlePostModule(new SinglePostModule(this))
                .build().inject(SinglePostActivity.this);
    }

    @Override
    public void setLoad(WallItem wallItem) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.cal_container, WallCommentFragment.newInstance(wallItem, true), WallCommentFragment.TAG)
                .commitAllowingStateLoss();
    }
}
