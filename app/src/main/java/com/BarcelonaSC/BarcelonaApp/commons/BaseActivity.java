package com.BarcelonaSC.BarcelonaApp.commons;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;

import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VirtualActivity;
import com.google.android.gms.analytics.Tracker;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.GalleryListActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsDetailsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsVideoActivity;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;

/**
 * Created by root on 10/31/17.
 */

public class BaseActivity extends AppCompatActivity implements Navigator, BannerView.BannerListener {

    public static final String TAG = BaseActivity.class.getSimpleName();

    DrawerLayout dl_menu;

    ActionBarDrawerToggle mDrawerToggle;
    private Tracker mTracker;
    private PreferenceManager preferenceManager;
    public SessionManager sessionManager;
    public static BannerView.Seccion lastSeccionBanner;
    public BannerView bv_banner;

    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(App.getAppContext());
        sessionManager = new SessionManager(this);
        // Activar Firebase
        //    subscribeToPushService();
        mTracker = App.get().getDefaultTracker();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void navigateToNewsDetailsActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constant.Key.TITLE, news.getTitulo());
        intent.putExtra(Constant.Key.DESC_NEW, news.getDescripcion());
        intent.putExtra(Constant.Key.IMG, news.getFoto());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToVideoNewsActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsVideoActivity.class);
        intent.putExtra(Constant.Key.URL, news.getLink());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToInfografiaActivity(News news) {
        Intent intent = new Intent(getActivity(), NewsInfografyActivity.class);
        intent.putExtra(Constant.Key.URL, news.getLink());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToHomeActivity() {
        Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToGalleryActivity(int id) {
        Intent intent = new Intent(getActivity(), GalleryListActivity.class);
        intent.putExtra(Constant.Key.ID, id);
        startActivity(intent);
    }

    @Override
    public void navigateToPlayerActivity(int playerId, String type) {
        Intent intent = new Intent(this, PlayerActivity.class);
        intent.putExtra(Constant.Key.PLAYER_ID, playerId);
        intent.putExtra(Constant.Key.TYPE, type);
        startActivity(intent);
    }

    @Override
    public void navigateToMonumentalProfile(String monumentalId, String pollId) {

    }

    @Override
    public void navigateToLiveActivity() {

    }

    @Override
    public void navigateToGameActivity() {

    }

    @Override
    public void navigateToActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void navigateVirtualActivity(VideoReality videoReality) {
        /*if (!SessionManager.getInstance().getUser().isDorado() && videoReality.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/

        Intent intent = new Intent(getActivity(), VirtualActivity.class);
        intent.putExtra(Constant.Key.VIRTUAL_REALITY_SECTION, videoReality);
        intent.putExtra(Constant.Key.ID, videoReality);
        String url = videoReality.getUrl().replace("\\", "");
        intent.putExtra("url", url);
        navigateToActivity(intent);
    }

    public void initBanner(BannerView.Seccion seccion) {
        bv_banner = findViewById(R.id.bv_banner);
        if (bv_banner != null) {
            bv_banner.setLayoutParams(new LinearLayout.LayoutParams(Commons.getWidthDisplay(), LinearLayout.LayoutParams.WRAP_CONTENT));
            initBannerComponent(bv_banner, seccion);
        }
    }

    public void initBannerMenu(int idBanner, BannerView.Seccion seccion) {
        BannerView bv_banner = findViewById(idBanner);
        initBannerComponent(bv_banner, seccion);
    }

    public void initBannerComponent(BannerView bannerView, BannerView.Seccion seccion) {
        lastSeccionBanner = seccion;
        if (bannerView != null)
            bannerView.putBannerData(seccion, this);
    }

    @Override
    public void onClickBannerInternoListener(String url, String title) {
        if (url != null) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(Constant.Key.URL, url);
            intent.putExtra(Constant.Key.TITLE, title);
            navigateToActivity(intent);
            Log.i(TAG, "--->PRUEBA onClickBannerInternoListener url: " + url);
        }
    }

    @Override
    public void onClickBannerExternoListener(String url) {
        if (url != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
            Log.i(TAG, "--->PRUEBA onClickBannerExternoListener url: " + url);
        }
    }

    @Override
    public void onClickBannerSeccionListener(String seccionDestino) {
        if (seccionDestino != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constant.Key.SECCION, seccionDestino);
            navigateToActivity(intent);
            if (!(this instanceof HomeActivity))
                finish();
            Log.i(TAG, "--->PRUEBA onClickBannerSeccionListener seccionDestino: " + seccionDestino);
        }
    }
}
