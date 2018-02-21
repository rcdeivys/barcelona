package com.BarcelonaSC.BarcelonaApp.commons;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.Tracker;
import com.BarcelonaSC.BarcelonaApp.BuildConfig;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.DrawerItem;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;
import com.BarcelonaSC.BarcelonaApp.utils.SideMenu;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 01/11/2017.
 */

public abstract class BaseSideMenuActivity extends BaseActivity implements SideMenu.OnClickMenuListener {

    protected DrawerLayout dl_menu;

    ActionBarDrawerToggle mDrawerToggle;
    private Tracker mTracker;
    private PreferenceManager preferenceManager;
    public SessionManager sessionManager;

    SideMenu sideMenu;

    public FragmentActivity getActivity() {
        return this;
    }

    private ConfigurationManager configurationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(App.getAppContext());
        sessionManager = new SessionManager(this);
        mTracker = App.get().getDefaultTracker();
        configurationManager = ConfigurationManager.getInstance();
    }

    protected void initMenu() {
        ImageView TV_Menu = findViewById(R.id.tv_menu);

        RecyclerView menuList = findViewById(R.id.menu_list);
        sideMenu = new SideMenu(assignMenuIds());
        sideMenu.setOnClickMenuListener(this);
        TextView version = findViewById(R.id.text_version);
        version.setText("Build Version " + BuildConfig.VERSION_NAME);
        super.initBannerMenu(R.id.bv_banner_menu, BannerView.Seccion.BOTTOM);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, Commons.getWidthDisplay() / 8);
        ImageView img = findViewById(R.id.bv_banner);
        img.setLayoutParams(params);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == 1) {
                    return 3;
                } else {
                    return 1;
                }
            }
        });
        menuList.setLayoutManager(gridLayoutManager);
        menuList.setAdapter(sideMenu);

        TV_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl_menu.openDrawer(Gravity.START);
            }
        });

        dl_menu = findViewById(R.id.dl_menu);

        mDrawerToggle = new ActionBarDrawerToggle(this, dl_menu, 1, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ActivityCompat.invalidateOptionsMenu(BaseSideMenuActivity.this);
                Log.d(TAG, "Drawer opended");
                // Send Menu status to analytics
                registerTrackScreen(getResources().getString(R.string.drawer_opened));
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ActivityCompat.invalidateOptionsMenu(BaseSideMenuActivity.this);
                Log.d(TAG, "Drawer closed");
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(false);
        dl_menu.addDrawerListener(mDrawerToggle);
    }

    public void setSubTitle(String title) {
        FCMillonariosTextView tvSubHeaderTitle = findViewById(R.id.tv_sub_header_title);
        tvSubHeaderTitle.setText(title);
    }

    private List<Object> assignMenuIds() {
        List<Object> ivSideMenuList = new ArrayList<>();
        ivSideMenuList.add(Constant.Key.HEADER);
        ivSideMenuList.add(Constant.Key.SOCIAL);
        //ivSideMenuList.add(new DrawerItem(Constant.Menu.PROFILE, R.drawable.perfil_icon, configurationManager.getConfiguration().getTit1()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.NEWS, R.drawable.noticias_icon, configurationManager.getConfiguration().getTit2()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.ALIGMENT, R.drawable.alineacion_icon, configurationManager.getConfiguration().getTit7()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.CALENDAR, R.drawable.calendario_icon, configurationManager.getConfiguration().getTit3()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.TABLE, R.drawable.tabla_icon, configurationManager.getConfiguration().getTit4()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.TEAM, R.drawable.plantilla_icon, configurationManager.getConfiguration().getTit6()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.IN_LIVE, R.drawable.bsc_en_vivo_icon, configurationManager.getConfiguration().getTit9()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.WALL_AND_CHAT, R.drawable.muro_chat_icon, configurationManager.getConfiguration().getTit16()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.VIRTUAL_REALITY, R.drawable.realidad_virtual_icon, configurationManager.getConfiguration().getTit8()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.YOUR_CHOOSE, R.drawable.tu_escoges_icon, configurationManager.getConfiguration().getTit10()));
        ivSideMenuList.add(new DrawerItem(Constant.Menu.MONUMENTAL, R.drawable.monumentales_icon, configurationManager.getConfiguration().getTit12()));
        //ivSideMenuList.add(new DrawerItem(Constant.Menu.GAME, R.drawable.juegos_icon, configurationManager.getConfiguration().getTit11()));
        //ivSideMenuList.add(new DrawerItem(Constant.Menu.ONLINE_SHOP, R.drawable.tienda_icon, configurationManager.getConfiguration().getTit13()));
        //ivSideMenuList.add(new DrawerItem(Constant.Menu.MAP, R.drawable.mapa_icon, configurationManager.getConfiguration().getTit14()));
        //ivSideMenuList.add(new DrawerItem(Constant.Menu.STATISTICS, R.drawable.estadisticas_icon, configurationManager.getConfiguration().getTit5()));

        return ivSideMenuList;
    }

    public void updateHeader() {
        sessionManager = new SessionManager(App.get().getBaseContext());
        Log.d("BaseActivity", "nombre:  " + sessionManager.getUser().getNombre());
        sideMenu.notifyItemChanged(0);
    }

    public void registerTrackScreen(String screenName) {
        App.get().registerTrackScreen(screenName);
    }
}