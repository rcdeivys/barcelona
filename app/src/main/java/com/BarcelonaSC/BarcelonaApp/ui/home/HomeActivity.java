package com.BarcelonaSC.BarcelonaApp.ui.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.permissions.MillosMultiplePermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.academy.AcademyFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.CalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.futbolbase.FutbolBaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.MapActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.di.DaggerHomeComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.di.HomeModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table.TableFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.WallAndChat.WallAndChatFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.configuration.NotificationFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.lineup.LineUpFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.MainNewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.profile.ProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.shop.VirtualShopFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.statistics.StatisticsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.TeamFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.youchooce.YouChooseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.mvp.HomeContract;
import com.BarcelonaSC.BarcelonaApp.ui.home.mvp.HomePresenter;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.MonumentalMainFragment;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VRFragment;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by root on 10/31/17.
 */

public class HomeActivity extends BaseSideMenuActivity implements HomeContract.View {

    public static final String TAG = HomeActivity.class.getSimpleName();
    private String mActiveFragmentTag;

    @Inject
    public HomePresenter presenter;
    @BindView(android.R.id.content)
    ViewGroup rootView;
    @BindView(R.id.content_powered)
    LinearLayout contentPowered;
    @BindView(R.id.iv_powered)
    ImageView imgPowered;
    @BindView(R.id.tb_sub_menu)
    RelativeLayout subMenu;
    @BindView(R.id.ib_sub_header_share)
    ImageButton share;

    public String nameSection;

    ShareSection section;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        App.get().component().fireBaseManager();
        setPermissions();
        if (ConfigurationManager.getInstance().getConfiguration().getPatrocinante() != null)
            if (!ConfigurationManager.getInstance().getConfiguration().getPatrocinante().equals("")) {
                contentPowered.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(ConfigurationManager.getInstance().getConfiguration().getPatrocinante())
                        .into(imgPowered);

            }
        super.initMenu();
        section = new ShareSection(getBaseContext());
        initComponent();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActiveFragmentTag.equals(TableFragment.TAG)) {
                    ((TableFragment) getFragmentByTag(TableFragment.TAG)).share();
                } else if (mActiveFragmentTag.equals(LineUpFragment.TAG)) {
                    ((LineUpFragment) getFragmentByTag(LineUpFragment.TAG)).share();
                } else {
                    section.share(getBaseContext(), nameSection);
                }
            }
        });
        if (getIntent().getExtras() != null) {
            presenter.setFragmentFromSeccion(getIntent().getExtras().getString(Constant.Key.SECCION, ""));
        }

        presenter.getActivity(HomeActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        super.setSubTitle(nameSection);

    }

    public void hideSubMenu() {
        subMenu.setVisibility(View.GONE);
    }

    public void showSubMenu() {
        subMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void addFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.top_in, R.anim.top_out)
                .replace(R.id.fl_container, fragment, tag)
                .commit();
    }

    public void initComponent() {
        DaggerHomeComponent.builder()
                .appComponent(App.get().component())
                .homeModule(new HomeModule(this))
                .build().inject(HomeActivity.this);
    }

    @Override
    public void showFragment(final Fragment fragment, final String tag) {
        Log.d("TAG", "Mensaje : " + tag);
        if (getActivity() != null) {
            if (mActiveFragmentTag != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(getSupportFragmentManager().findFragmentByTag(mActiveFragmentTag))
                        .commit();
            }
            mActiveFragmentTag = tag;
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.top_in, R.anim.top_out)
                    .show(fragment)
                    .commit();
            getSeccion(tag);

        }
    }

    public void shareSection() {
        share.setVisibility(View.VISIBLE);
    }

    public void notShareSection() {
        share.setVisibility(View.GONE);
    }

    public void getSeccion(String tag) {
        if (tag.equals(ProfileFragment.TAG)) {
            initBanner(BannerView.Seccion.PROFILE);
        } else if (tag.equals(NotificationFragment.TAG)) {
            initBanner(BannerView.Seccion.SETINGS);
        } else if (tag.equals(CalendarFragment.TAG)) {
            initBanner(BannerView.Seccion.CALENDAR);
        } else if (tag.equals(NewsFragment.TAG + Constant.Menu.NEWS)) {
            initBanner(BannerView.Seccion.NEWS);
        } else if (tag.equals(FutbolBaseFragment.TAG /*+ Constant.Menu.FOOTBALL_BASE*/)) {
            initBanner(BannerView.Seccion.FOOTBALL_BASE);
        } else if (tag.equals(AcademyFragment.TAG)) {
            initBanner(BannerView.Seccion.ACADEMY);
        } else if (tag.equals(LineUpFragment.TAG)) {
            initBanner(BannerView.Seccion.LINE_UP);
        } else if (tag.equals(WallAndChatFragment.TAG)) {
            initBanner(BannerView.Seccion.WALL_AND_CHAT);
        } else if (tag.equals(VRFragment.TAG)) {
            initBanner(BannerView.Seccion.VIRTUAL_REALITY);
        } else if (tag.equals(VirtualShopFragment.TAG)) {
            initBanner(BannerView.Seccion.STORE);
        } else if (tag.equals(TableFragment.TAG)) {
            initBanner(BannerView.Seccion.TABLE);
        } else if (tag.equals(TeamFragment.TAG)) {
            initBanner(BannerView.Seccion.TEAM);
        } else if (tag.equals(StatisticsFragment.TAG)) {
            initBanner(BannerView.Seccion.STATISTICS);
        } else if (tag.equals(YouChooseFragment.TAG)) {
            initBanner(BannerView.Seccion.YOU_CHOOSE);
        } else if (tag.equals(MonumentalMainFragment.TAG)) {
            initBanner(BannerView.Seccion.MONUMENTAL);
        } else if (tag.equals(MapActivity.TAG)) {
            initBanner(BannerView.Seccion.MAP);
        } else {
            initBanner(BannerView.Seccion.SETINGS);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            presenter.setFragmentFromSeccion(intent.getExtras().getString(Constant.Key.SECCION, ""));
        }
        Log.i(TAG, "--->onNewIntent");
    }

    @Override
    public void setTitle(String title) {
        super.setSubTitle(title);
        if (ConfigurationManager.getInstance().getConfiguration().getTit4().equals(title) ||
                ConfigurationManager.getInstance().getConfiguration().getTit5().equals(title) ||
                ConfigurationManager.getInstance().getConfiguration().getTit7().equals(title) ||
                ConfigurationManager.getInstance().getConfiguration().getTit12().equals(title)) {
            share.setVisibility(View.VISIBLE);
        } else {
            share.setVisibility(View.GONE);
        }
        nameSection = title;
        if (subMenu.getVisibility() == View.GONE)
            showSubMenu();
    }

    @Override
    public void trackFragment(String fragment) {
        registerTrackScreen(fragment);
    }

    @Override
    public void onClickMenuItem(String fragment) {
        dl_menu.closeDrawers();
        presenter.onItemMenuSelected(fragment);
    }

    @Override
    public Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public void onBackPressed() {
        if (!dl_menu.isDrawerOpen(Gravity.START)) {
            if (mActiveFragmentTag.equals(MainNewsFragment.TAG + Constant.Menu.NEWS)) {
                super.onBackPressed();
            } else {
                presenter.newsProfessional();
            }
        } else {
            dl_menu.closeDrawers();
        }
//        if (mActiveFragmentTag.equals(NewsFragment.TAG + Constant.Menu.NEWS)) {
//            super.onBackPressed();
//        } else {
//            presenter.newsProfessional();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MapActivity.MAP_REQUEST_CODE) {
            if ((resultCode == RESULT_OK)) {
                presenter.onItemMenuSelected(data.getExtras().getString(MapActivity.SECCION_SELECTED));
            }
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new MillosMultiplePermissionListener(this);
        MultiplePermissionsListener allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, "Permisos denegados")
                                .build());
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(allPermissionsListener).check();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        token.continuePermissionRequest();
    }

    public void showPermissionGranted(String permission) {

    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    @Override
    protected void onDestroy() {
        FirebaseManager.getInstance().changeUserState(-1, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError() {

            }
        });
        super.onDestroy();

    }
}