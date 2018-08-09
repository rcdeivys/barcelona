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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.GalleryListActivity;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsDetailsActivity;
import com.BarcelonaSC.BarcelonaApp.ui.news.NewsInfografyActivity;
import com.BarcelonaSC.BarcelonaApp.ui.playerdetails.PlayerActivity;
import com.BarcelonaSC.BarcelonaApp.ui.virtualreality.VirtualActivity;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.PreferenceManager;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by root on 10/31/17.
 */

public class BaseActivity extends AppCompatActivity implements Navigator, BannerView.BannerListener, BaseView {

    public static final String TAG = BaseActivity.class.getSimpleName();

    DrawerLayout dl_menu;

    ActionBarDrawerToggle mDrawerToggle;
    private Tracker mTracker;
    private PreferenceManager preferenceManager;
    public SessionManager sessionManager;
    public static BannerView.Seccion lastSeccionBanner;
    public BannerView bv_banner;
    protected int doradoCode = 989;

    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceManager = new PreferenceManager(App.getAppContext());
        sessionManager = new SessionManager(App.getAppContext());
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
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void navigateToNewsDetailsActivity(News news) {
      /*  if (!SessionManager.getInstance().getUser().isDorado() && news.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constant.Key.TITLE, news.getTitulo());
        intent.putExtra(Constant.Key.DESC_NEW, news.getDescripcion());
        intent.putExtra(Constant.Key.IMG, news.getFoto());
        intent.putExtra(Constant.Key.URL, news.getLink());
        intent.putExtra(Constant.Key.ID, "" + news.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToVideoNewsActivity(News news, int currentPosition) {
       /* if (!SessionManager.getInstance().getUser().isDorado() && news.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, currentPosition);
        intent.putExtra(Constant.Video.PLAY, true);
        intent.putExtra(Constant.Video.URL, news.getLink());
        intent.putExtra(Constant.Key.ID, "" + news.getId());
        startActivity(intent);
    }

    @Override
    public void navigateToVideoBeneficiosActivity(BeneficioData beneficioData, int currentVideo) {

    }

    @Override
    public void navigateToInfografiaActivity(News news) {
       /* if (!SessionManager.getInstance().getUser().isDorado() && news.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/
        Intent intent = new Intent(getActivity(), NewsInfografyActivity.class);
        intent.putExtra(Constant.Key.URL, news.getLink());
        intent.putExtra(Constant.Key.ID, "" + news.getId());
        getActivity().startActivity(intent);
    }

    @Override
    public void navigateToVideoMultimediaActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition) {
//        if (!SessionManager.getInstance().getUser().isDorado() && news.isDorado()) {
//            showBuyDoradoDialog();
//            return;
//        }
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, currentPosition);
        intent.putExtra(Constant.Video.PLAY, true);
        intent.putExtra(Constant.Video.URL, multimediaDataResponse.getLink());
        intent.putExtra(Constant.Key.ID, "" + multimediaDataResponse.getId());
        startActivity(intent);
    }

    @Override
    public void navigateToHomeActivity() {
        Intent intent = new Intent(BaseActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToGalleryActivity(News news) {
       /* if (!SessionManager.getInstance().getUser().isDorado() && news.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/
        Intent intent = new Intent(getActivity(), GalleryListActivity.class);
        intent.putExtra(Constant.Key.ID, news.getId());
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


    public void showBuyDoradoDialog() {

     /*   final FullScreenDialog dialog = new FullScreenDialog();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        dialog.show(ft, FullScreenDialog.TAG);
        dialog.setListener(new FullScreenDialog.SubscriptionListener() {
            @Override
            public void onSubscription() {
                startActivityForResult(new Intent(BaseActivity.this, HinchaDoradoActivity.class), doradoCode);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 200);

            }
        });*/

    }

    @Override
    public void navigateToGameActivity() {

    }

    @Override
    public void navigateToActivity(Intent intent) {
        startActivity(intent);
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
    public void onClickBannerInternoListener(String url, String section, String title) {
        // Send banner data to Google Analytics
        App.get().registerCustomTrackScreen(Constant.Analytics.BANNER, section + "." + title, 6);

        if (url != null) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(Constant.Key.URL, url);
            intent.putExtra(Constant.Key.TITLE, title);
            navigateToActivity(intent);
            Log.i(TAG, "--->PRUEBA onClickBannerInternoListener url: " + url);
        }
    }

    @Override
    public void onClickBannerExternoListener(String url, String section, String title) {
        // Send banner data to Google Analytics
        App.get().registerCustomTrackScreen(Constant.Analytics.BANNER, section + "." + title, 6);

        if (url != null) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
            Log.i(TAG, "--->PRUEBA onClickBannerExternoListener url: " + url);
        }
    }

    @Override
    public void onClickBannerSeccionListener(String seccionDestino, String bannerData, String section, String title) {
        // Send banner data to Google Analytics
        App.get().registerCustomTrackScreen(Constant.Analytics.BANNER, section + "." + title, 6);

        if (seccionDestino != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(Constant.Key.SECCION, seccionDestino);
            if (bannerData != null && !bannerData.equals(""))
                intent.putExtra(Constant.Key.SECCION_PARTIDO, bannerData);
            navigateToActivity(intent);
            if (!(this instanceof HomeActivity))
                finish();
            Log.i(TAG, "--->PRUEBA onClickBannerSeccionListener seccionDestino: " + seccionDestino);
        }
    }

    @Override
    public void navigateVirtualActivity(VideoReality videoReality) {
        /*if (!SessionManager.getInstance().getUser().isDorado() && videoReality.isDorado()) {
            showBuyDoradoDialog();
            return;
        }*/

        Intent intent = new Intent(getActivity(), VirtualActivity.class);
        //Log.i("ITEMVR"," ---> ID: "+videoReality.getId());
        intent.putExtra(Constant.Key.VIRTUAL_REALITY_SECTION, videoReality);
        intent.putExtra(Constant.Key.ID, videoReality.getId());
        String url = videoReality.getUrl().replace("\\", "");
        //Log.i("URL"," ---> "+url);
        intent.putExtra("url", url);
        navigateToActivity(intent);
    }

    @Override
    public void showDialogDorado() {
        showBuyDoradoDialog();
    }
}
