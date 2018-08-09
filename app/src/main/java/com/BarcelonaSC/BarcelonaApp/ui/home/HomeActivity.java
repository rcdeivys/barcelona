package com.BarcelonaSC.BarcelonaApp.ui.home;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.BuildConfig;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.network.NetworkCallBack;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.models.response.ConfigurationResponse;
import com.BarcelonaSC.BarcelonaApp.permissions.MillosMultiplePermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.academy.AcademyFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.CalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.futbolbase.FutbolBaseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.MapActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.di.DaggerHomeComponent;
import com.BarcelonaSC.BarcelonaApp.ui.home.di.HomeModule;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Multimedia.MultimediaFragment;
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
import com.BarcelonaSC.BarcelonaApp.ui.home.updatepopup.UpdateDialog;
import com.BarcelonaSC.BarcelonaApp.ui.home.updatepopup.WebUpdateActivity;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VRFragment;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.ShareSection;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
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

public class HomeActivity extends BaseSideMenuActivity implements HomeContract.View, UpdateDialog.OnItemClickListener {

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

    public int idPartido = 0;

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

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        try {
            App.get().component().configurationApi().getConfiguration().enqueue(new NetworkCallBack<ConfigurationResponse>() {
                @Override
                public void onRequestSuccess(ConfigurationResponse response) {
                    Log.i("CONFIGURATION", " ---> DATA : " + response.getData().toStringDIalogo());
                    String versionName = BuildConfig.VERSION_NAME;
                    Log.i("CONFIGURATION", " ---> DATA versionName: " + versionName);
                    if (response.getData().getShowPopup()!=null && response.getData().getShowPopup().equals("1")) {

                        //if (true) {
                        UpdateDialog updateDialog = new UpdateDialog();
                        updateDialog.setCancelable(false);
                        String type = response.getData().getTypePopup();
                        switch (type) {
                            case "update":
                                //verificar version de app
                                if (!response.getData().getVerApp().equals(versionName)) {
                                    updateDialog.setParams(
                                            response.getData().getBannerPopup(),
                                            response.getData().getLinkPopup(),
                                            response.getData().getDestinoPopup(),
                                            "update",
                                            response.getData().isBoton1Activo(),
                                            response.getData().getTextoBoton1(),
                                            HomeActivity.this);
                                    showDialogFragment(updateDialog);
                                }
                                break;
                            case "informativo":
                                updateDialog.setParams(
                                        response.getData().getBannerPopup(),
                                        response.getData().getLinkPopup(),
                                        response.getData().getDestinoPopup(),
                                        "informativo",
                                        response.getData().isBoton1Activo(),
                                        response.getData().getTextoBoton1(),
                                        HomeActivity.this);
                                showDialogFragment(updateDialog);
                                break;
                            default:
                                updateDialog.setParams(
                                        response.getData().getBannerPopup(),
                                        response.getData().getLinkPopup(),
                                        response.getData().getDestinoPopup(),
                                        response.getData().getTargetPopup(),
                                        response.getData().isBoton1Activo(),
                                        response.getData().getTextoBoton1(),
                                        HomeActivity.this);
                                showDialogFragment(updateDialog);
                                break;
                        }

                    }

                }

                @Override
                public void onRequestFail(String errorMessage, int errorCode) {

                }
            });
        } catch (Exception e) {
            Log.i("CONFIGURATION", " ---> Hubo un error : " + e.getMessage());
        }

        if (FirebaseInstanceId.getInstance().getToken() != null)
            presenter.sentFirebaseInstanceIdTokenToServer(FirebaseInstanceId.getInstance().getToken());

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(ChatActivity.TAG_GROUP) != null) {
                getActivity().startActivity(ChatActivity.intent(getIntent().getStringExtra(ChatActivity.TAG_GROUP), this));
            } else if (getIntent().getStringExtra(ChatActivity.TAG_PRIVATE) != null) {
                getActivity().startActivity(ChatActivity.intent(Long.parseLong(getIntent().getStringExtra(ChatActivity.TAG_PRIVATE)), this));
            } else {
                if (getIntent().getStringExtra(Constant.Key.SECCION) != null) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.setFragmentFromSeccion(getIntent().getStringExtra(Constant.Key.SECCION));
                        }
                    }, 500);
                }
            }
        }

        presenter.getActivity(HomeActivity.this);

        if ((getIntent().getStringExtra(Constant.Key.SECCION_PARTIDO) != null)) {
            idPartido = Integer.parseInt(getIntent().getStringExtra(Constant.Key.SECCION_PARTIDO));
        }
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
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
            getSeccion(tag, fragment);

        }
    }

    public void shareSection() {
        share.setVisibility(View.VISIBLE);
    }

    public void notShareSection() {
        share.setVisibility(View.GONE);
    }

    public void getSeccion(String tag, Fragment fragment) {
        if (tag.equals(ProfileFragment.TAG)) {
            trackFragment(Constant.Analytics.PROFILE);
            initBanner(BannerView.Seccion.PROFILE);
        } else if (tag.equals(NotificationFragment.TAG)) {
            trackFragment(Constant.Analytics.NOTIFICATION);
            initBanner(BannerView.Seccion.SETINGS);
        } else if (tag.equals(CalendarFragment.TAG)) {
            trackFragment(Constant.Analytics.CALENDAR);
            initBanner(BannerView.Seccion.CALENDAR);
        } else if (tag.equals(MainNewsFragment.TAG + Constant.Menu.NEWS)) {
            trackFragment(Constant.Analytics.NEWS);
            initBanner(BannerView.Seccion.NEWS);
        } else if (tag.equals(FutbolBaseFragment.TAG + Constant.Menu.FOOTBALL_BASE)) {
            initBanner(BannerView.Seccion.FOOTBALL_BASE);
        } else if (tag.equals(AcademyFragment.TAG)) {
            initBanner(BannerView.Seccion.ACADEMY);
        } else if (tag.equals(LineUpFragment.TAG)) {
            initBanner(BannerView.Seccion.LINE_UP);
        } else if (tag.equals(WallAndChatFragment.TAG)) {
            if (Constant.Menu.CHAT.equals(((WallAndChatFragment) fragment).getSelected())) {
                initBanner(BannerView.Seccion.CHAT);
            } else {
                initBanner(BannerView.Seccion.WALL);
            }
            Log.d(TAG, "getSeccion " + tag);
        } else if (tag.equals(VRFragment.TAG)) {
            trackFragment(Constant.Analytics.VIRTUAL_REALITY);
            initBanner(BannerView.Seccion.VIRTUAL_REALITY);
        } else if (tag.equals(VirtualShopFragment.TAG)) {
            trackFragment(Constant.Analytics.STORE);
            initBanner(BannerView.Seccion.STORE);
        } else if (tag.equals(TableFragment.TAG)) {
            initBanner(BannerView.Seccion.TABLE);
        } else if (tag.equals(TeamFragment.TAG)) {
            initBanner(BannerView.Seccion.TEAM);
        } else if (tag.equals(StatisticsFragment.TAG)) {
            trackFragment(Constant.Analytics.STATISTICS);
            initBanner(BannerView.Seccion.STATISTICS);
        } else if (tag.equals(YouChooseFragment.TAG)) {
            initBanner(BannerView.Seccion.YOU_CHOOSE);
        } else if (tag.equals(MapActivity.TAG)) {
            trackFragment(Constant.Analytics.MAP);
            initBanner(BannerView.Seccion.MAP);
        } else if (tag.equals(MultimediaFragment.TAG)) {
            initBanner(BannerView.Seccion.LIVE);
        } else {
            trackFragment(Constant.Analytics.NOTIFICATION);
            initBanner(BannerView.Seccion.SETINGS);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getExtras() != null) {
            if (intent.getStringExtra(Constant.Key.SECCION) != null) {
                if (intent.getStringExtra(Constant.Key.SECCION).equals(Constant.Seccion.CHAT)) {
                    if (intent.getStringExtra(ChatActivity.TAG_GROUP) != null) {
                        getActivity().startActivity(ChatActivity.intent(intent.getStringExtra(ChatActivity.TAG_GROUP), this));
                    } else if (intent.getStringExtra(ChatActivity.TAG_PRIVATE) != null) {
                        getActivity().startActivity(ChatActivity.intent(Long.parseLong(intent.getStringExtra(ChatActivity.TAG_PRIVATE)), this));
                    }
                }
            }
            if ((intent.getStringExtra(Constant.Key.SECCION_PARTIDO) != null)) {
                idPartido = Integer.parseInt(intent.getStringExtra(Constant.Key.SECCION_PARTIDO));
            }
            if (!intent.getExtras().getString(Constant.Key.SECCION_SELECTED, "").equals(""))
                presenter.onItemMenuSelected(intent.getExtras().getString(Constant.Key.SECCION_SELECTED));
            presenter.setFragmentFromSeccion(intent.getExtras().getString(Constant.Key.SECCION, ""));
        }
        Log.i(TAG, "--->onNewIntent");
    }

    @Override
    public void setTitle(String title) {
        super.setSubTitle(title);
        if (ConfigurationManager.getInstance().getConfiguration().getTit4().equals(title) ||
                ConfigurationManager.getInstance().getConfiguration().getTit5().equals(title) ||
                ConfigurationManager.getInstance().getConfiguration().getTit7().equals(title)) {
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
        super.onDestroy();

    }


    @Override
    public void onGoWebView(String url) {
        WebUpdateActivity payUPaymentActivity = new WebUpdateActivity();
        payUPaymentActivity.setParams(url);
        showDialogFragment(payUPaymentActivity);
    }

    @Override
    public void onGoSection(String section) {
        presenter.setFragmentFromSeccion(section);
    }

    @Override
    public void onGoExternalBrowser(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
