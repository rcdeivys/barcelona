package com.BarcelonaSC.BarcelonaApp.ui.home.mvp;

import android.util.Log;

import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.ui.academy.AcademyFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.CalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.calendar.MainCalendarFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.Table.TableFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.WallAndChat.WallAndChatFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.configuration.NotificationFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.lineup.LineUpFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.live.LiveFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.news.NewsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.profile.ProfileFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.shop.VirtualShopFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.statistics.StatisticsFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.team.TeamFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.menu.youchooce.YouChooseFragment;
import com.BarcelonaSC.BarcelonaApp.ui.map.MapFragment;
import com.BarcelonaSC.BarcelonaApp.ui.monumentals.MonumentalMainFragment;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VRFragment;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

/**
 * Created by Carlos on 01/11/2017.
 */

public class HomePresenter {

    private static final String TAG = HomePresenter.class.getSimpleName();
    private HomeContract.View view;
    private HomeModel homeModel;

    private NewsFragment newsFragment;
    private NewsFragment newsProfessionalFragment;
    private YouChooseFragment mYouChooseFragment;
    private ProfileFragment profileFragment;
    private NotificationFragment settingFragment;
    private TeamFragment teamFragment;
    private StatisticsFragment statisticsFragment;
    private TableFragment tableFragment;
    private VirtualShopFragment shopFragment;
    private VRFragment vrFragment;
    private ConfigurationManager configurationManager;
    private LineUpFragment lineUpFragment;
    private MainCalendarFragment calendarFragment;
    private AcademyFragment academyFragment;
    private WallAndChatFragment wallAndChatFragment;
    private MapFragment mapFragment;
    private MonumentalMainFragment monumentalFragment;
    private LiveFragment liveFragment;

    public HomePresenter(HomeContract.View view, HomeModel homeModel) {
        this.view = view;
        this.homeModel = homeModel;
        configurationManager = ConfigurationManager.getInstance();
        init();
    }

    private void init() {
        newsProfessional();
    }

    private void mountProfile() {
        profileFragment = (ProfileFragment)
                view.getFragmentByTag(ProfileFragment.TAG);
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            view.addFragment(profileFragment, ProfileFragment.TAG);
        }
        view.showFragment(profileFragment, ProfileFragment.TAG);
    }

    private void mountVirtualReality() {
        vrFragment = (VRFragment)
                view.getFragmentByTag(VRFragment.TAG);
        if (vrFragment == null) {
            vrFragment = new VRFragment();
            view.addFragment(vrFragment, VRFragment.TAG);
        }
        view.showFragment(vrFragment, VRFragment.TAG);
    }

    private void mountTeam() {
        teamFragment = (TeamFragment)
                view.getFragmentByTag(TeamFragment.TAG);
        if (teamFragment == null) {
            teamFragment = new TeamFragment();
            view.addFragment(teamFragment, TeamFragment.TAG);
        }
        view.showFragment(teamFragment, TeamFragment.TAG);
    }

    public void newsProfessional() {
        newsProfessionalFragment = (NewsFragment)
                view.getFragmentByTag(NewsFragment.TAG + Constant.Menu.NEWS);
        if (newsProfessionalFragment == null) {
            newsProfessionalFragment = NewsFragment.getInstance(NewsFragment.NEWS_PROFESSIONAL);
            view.addFragment(newsProfessionalFragment, NewsFragment.TAG + Constant.Menu.NEWS);
        }
        view.setTitle(configurationManager.getConfiguration().getTit2());
        view.showFragment(newsProfessionalFragment, NewsFragment.TAG + Constant.Menu.NEWS);
    }

    private void mountYouChoose() {
        mYouChooseFragment = (YouChooseFragment)
                view.getFragmentByTag(YouChooseFragment.TAG);
        if (mYouChooseFragment == null) {
            mYouChooseFragment = new YouChooseFragment();
            view.addFragment(mYouChooseFragment, YouChooseFragment.TAG);
        }
        view.showFragment(mYouChooseFragment, YouChooseFragment.TAG);
    }

    private void table() {
        tableFragment = (TableFragment)
                view.getFragmentByTag(TableFragment.TAG);
        if (tableFragment == null) {
            tableFragment = new TableFragment();
            view.addFragment(tableFragment, TableFragment.TAG);
        }
        view.showFragment(tableFragment, TableFragment.TAG);
    }

    private void statistics() {
        statisticsFragment = (StatisticsFragment)
                view.getFragmentByTag(StatisticsFragment.TAG);
        if (statisticsFragment == null) {
            statisticsFragment = new StatisticsFragment();
            view.addFragment(statisticsFragment, StatisticsFragment.TAG);
        }
        view.showFragment(statisticsFragment, StatisticsFragment.TAG);
    }

    private void virtualShop() {
        shopFragment = (VirtualShopFragment)
                view.getFragmentByTag(VirtualShopFragment.TAG);
        if (shopFragment == null) {
            shopFragment = new VirtualShopFragment();
            view.addFragment(shopFragment, VirtualShopFragment.TAG);
        }
        view.showFragment(shopFragment, VirtualShopFragment.TAG);
    }

    private void setting() {
        settingFragment = (NotificationFragment)
                view.getFragmentByTag(NotificationFragment.TAG);
        if (settingFragment == null) {
            settingFragment = new NotificationFragment();
            view.addFragment(settingFragment, NotificationFragment.TAG);
        }
        view.showFragment(settingFragment, NotificationFragment.TAG);
    }

    private void lineUp() {
        lineUpFragment = (LineUpFragment)
                view.getFragmentByTag(LineUpFragment.TAG);
        if (lineUpFragment == null) {
            lineUpFragment = new LineUpFragment();
            view.addFragment(lineUpFragment, LineUpFragment.TAG);
        }
        view.showFragment(lineUpFragment, LineUpFragment.TAG);
    }

    private void mountCalendar() {
        calendarFragment = (MainCalendarFragment) view.getFragmentByTag(CalendarFragment.TAG);
        if (calendarFragment == null) {
            calendarFragment = MainCalendarFragment.newInstance(Constant.Key.CUP);
            view.addFragment(calendarFragment, CalendarFragment.TAG);
        }
        view.showFragment(calendarFragment, CalendarFragment.TAG);
    }

    private void mountAcademy() {
        academyFragment = (AcademyFragment) view.getFragmentByTag(AcademyFragment.TAG);
        if (academyFragment == null) {
            academyFragment = new AcademyFragment();
            view.addFragment(academyFragment, AcademyFragment.TAG);
        }
        view.showFragment(academyFragment, AcademyFragment.TAG);
    }

    private void mountMonumental() {
        monumentalFragment = (MonumentalMainFragment) view.getFragmentByTag(MonumentalMainFragment.TAG);
        if (monumentalFragment == null) {
            monumentalFragment = new MonumentalMainFragment();
            view.addFragment(monumentalFragment, MonumentalMainFragment.TAG);
        }
        view.showFragment(monumentalFragment, MonumentalMainFragment.TAG);
    }

    private void mountMap() {
        mapFragment = (MapFragment) view.getFragmentByTag(MapFragment.TAG);
        if (mapFragment == null) {
            mapFragment = new MapFragment();
            view.addFragment(mapFragment, MapFragment.TAG);
        }
        view.showFragment(mapFragment, MapFragment.TAG);
    }

    private void live() {
        liveFragment = (LiveFragment) view.getFragmentByTag(LiveFragment.TAG);
        if (liveFragment == null) {
            liveFragment = new LiveFragment();
            view.addFragment(liveFragment, LiveFragment.TAG);
        }
        view.showFragment(liveFragment, LiveFragment.TAG);
    }

    private void wallAndChat() {
        wallAndChatFragment = (WallAndChatFragment) view.getFragmentByTag(WallAndChatFragment.TAG);
        if (wallAndChatFragment == null) {
            wallAndChatFragment = new WallAndChatFragment();
            view.addFragment(wallAndChatFragment, WallAndChatFragment.TAG);
        }
        view.showFragment(wallAndChatFragment, WallAndChatFragment.TAG);
    }

    public void onItemMenuSelected(String fragment) {
        Log.d(TAG, "Clicked : " + fragment);
        switch (fragment) {
            case Constant.Menu.PROFILE:
                view.setTitle(configurationManager.getConfiguration().getTit1());
                mountProfile();
                view.trackFragment(configurationManager.getConfiguration().getTit1());
                break;

            case Constant.Menu.Setting:
                view.setTitle(configurationManager.getConfiguration().getTit15());
                setting();
                view.trackFragment(configurationManager.getConfiguration().getTit5());
                break;

            case Constant.Menu.NEWS:
                view.setTitle(configurationManager.getConfiguration().getTit2());
                newsProfessional();
                view.trackFragment(configurationManager.getConfiguration().getTit2());
                break;

            case Constant.Menu.CALENDAR:
                view.setTitle(configurationManager.getConfiguration().getTit3());
                mountCalendar();
                view.trackFragment(configurationManager.getConfiguration().getTit3());
                break;

            case Constant.Menu.TABLE:
                view.setTitle(configurationManager.getConfiguration().getTit4());
                table();
                view.trackFragment(configurationManager.getConfiguration().getTit4());
                break;

            case Constant.Menu.TEAM:
                view.setTitle(configurationManager.getConfiguration().getTit6());
                mountTeam();
                view.trackFragment(configurationManager.getConfiguration().getTit6());
                break;

            case Constant.Menu.MONUMENTAL:
                view.setTitle(configurationManager.getConfiguration().getTit12());
                mountMonumental();
                view.trackFragment(configurationManager.getConfiguration().getTit12());
                break;

            case Constant.Menu.IN_LIVE:
                view.setTitle(configurationManager.getConfiguration().getTit9());
                view.trackFragment(configurationManager.getConfiguration().getTit9());
                live();
                break;

            case Constant.Menu.WALL_AND_CHAT:
                view.setTitle(configurationManager.getConfiguration().getTit161());
                wallAndChat();
                view.trackFragment(configurationManager.getConfiguration().getTit16());
                break;

            case Constant.Menu.GAME:
                view.setTitle(configurationManager.getConfiguration().getTit11());
                //mountProfile();
                view.trackFragment(configurationManager.getConfiguration().getTit11());
                break;

            case Constant.Menu.ALIGMENT:
                view.setTitle(configurationManager.getConfiguration().getTit7());
                lineUp();
                view.trackFragment(configurationManager.getConfiguration().getTit7());
                break;

            case Constant.Menu.VIRTUAL_REALITY:
                view.setTitle(configurationManager.getConfiguration().getTit8());
                mountVirtualReality();
                view.trackFragment(configurationManager.getConfiguration().getTit8());
                break;

            case Constant.Menu.MAP:
                view.setTitle(configurationManager.getConfiguration().getTit14());
                mountMap();
                view.trackFragment(configurationManager.getConfiguration().getTit14());
                break;

            case Constant.Menu.ONLINE_SHOP:
                view.setTitle(configurationManager.getConfiguration().getTit13());
                virtualShop();
                view.trackFragment(configurationManager.getConfiguration().getTit13());
                break;

            case Constant.Menu.STATISTICS:
                view.setTitle(configurationManager.getConfiguration().getTit5());
                statistics();
                view.trackFragment(configurationManager.getConfiguration().getTit5());
                break;

            case Constant.Menu.YOUR_CHOOSE:
                view.setTitle(configurationManager.getConfiguration().getTit10());
                mountYouChoose();
                view.trackFragment(configurationManager.getConfiguration().getTit10());
                break;
        }
    }

    public void setFragmentFromSeccion(String seccion) {
        changeSeccionToTag(seccion);
    }

    private void changeSeccionToTag(String seccion) {
        if (BannerView.Seccion.PROFILE.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.PROFILE);//
        } else if (BannerView.Seccion.NEWS.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.NEWS);//
        } else if (BannerView.Seccion.CALENDAR.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.CALENDAR); //
        } else if (BannerView.Seccion.TABLE.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.TABLE);//
        } else if (BannerView.Seccion.VIRTUAL_REALITY.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.VIRTUAL_REALITY);//
        } else if (BannerView.Seccion.TEAM.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.TEAM);//
        } else if (BannerView.Seccion.LINE_UP.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.ALIGMENT);//
        } else if (BannerView.Seccion.WALL_AND_CHAT.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.WALL_AND_CHAT);//
        } else if (BannerView.Seccion.LIVE.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.IN_LIVE);//
        } else if (BannerView.Seccion.STATISTICS.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.STATISTICS);//
        } else if (BannerView.Seccion.YOU_CHOOSE.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.YOUR_CHOOSE);//
        } else if (BannerView.Seccion.GAMES.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.GAME);
        } else if (BannerView.Seccion.STORE.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.ONLINE_SHOP);
        } else if (BannerView.Seccion.WALL_AND_CHAT.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.WALL_AND_CHAT);
        } else if (BannerView.Seccion.MONUMENTAL.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.MONUMENTAL);
        } else if (BannerView.Seccion.MAP.getValue().equals(seccion)) {
            onItemMenuSelected(Constant.Menu.MAP);
        }
    }
}