package com.BarcelonaSC.BarcelonaApp.ui.geolocation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.models.MapData;
import com.BarcelonaSC.BarcelonaApp.permissions.MapPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.di.DaggerMapComponent;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.di.MapModule;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp.MapContract;
import com.BarcelonaSC.BarcelonaApp.ui.geolocation.mvp.MapPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.BannerView;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by RYA-Laptop on 16/04/2018.
 */

public class MapActivity extends BaseSideMenuActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, MapContract.View {

    public static String TAG = MapActivity.class.getSimpleName();
    public static final String SECCION_SELECTED = "seccion_Selected";
    public static final int MAP_REQUEST_CODE = 7000;
    private GoogleMap mapa;
    private double longitude;
    private double latitude;
    private boolean requiredOnce = false;
    private List<MapData> lista;
    private View mCustomMarkerView;

    @BindView(android.R.id.content)
    ViewGroup rootView;
    @BindView(R.id.ib_return)
    ImageButton btnBack;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView textHeader;
    @BindView(R.id.btn_points)
    LinearLayout btnPoints;
    @BindView(R.id.btn_friends)
    LinearLayout btnFriends;
    @BindView(R.id.img_points)
    ImageView imgPoints;
    @BindView(R.id.img_friends)
    ImageView imgFriends;
    @BindView(R.id.search_et)
    AutoCompleteTextView search;
    @BindView(R.id.btn_search)
    ImageView btnSearch;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.content_search)
    LinearLayout contentSearch;
//    @BindView(R.id.iv_powered)
//    ImageView imgPowered;
//    @BindView(R.id.content_powered)
//    LinearLayout contentPowered;

    Marker myMarker, prevMarker, iPoints;
    Marker[] marker;
    Dialog dialog;

    @Inject
    MapPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //btnBack.setVisibility(View.VISIBLE);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        textHeader.setText(ConfigurationManager.getInstance().getConfiguration().getTit14());

        btnPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgPoints.setImageDrawable(getResources().getDrawable(R.drawable.puntos_icn_on));
                imgFriends.setImageDrawable(getResources().getDrawable(R.drawable.amigos_icn_off));
                mapa.clear();
                showProgress();
                presenter.getPoints();
                prevMarker = null;
                requiredOnce = false;
            }
        });

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFriends.setImageDrawable(getResources().getDrawable(R.drawable.amigos_icn_on));
                imgPoints.setImageDrawable(getResources().getDrawable(R.drawable.puntos_icn_off));
                mapa.clear();
                showProgress();
                prevMarker = null;
                requiredOnce = false;
                hideProgress();
            }
        });

//        if (ConfigurationManager.getInstance().getConfiguration().getPatrocinante() != null) {
//            if (!ConfigurationManager.getInstance().getConfiguration().getPatrocinante().equals("")) {
//                contentPowered.setVisibility(View.VISIBLE);
//                Glide.with(this)
//                        .load(ConfigurationManager.getInstance().getConfiguration().getPatrocinante())
//                        .into(imgPowered);
//            }
//        }

        super.initMenu();
        initBanner(BannerView.Seccion.MAP);
        initComponent();

        showProgress();
        presenter.getPoints();

    }

    public void initComponent() {
        DaggerMapComponent.builder()
                .appComponent(App.get().component())
                .mapModule(new MapModule(this))
                .build().inject(MapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapa.getUiSettings().setZoomControlsEnabled(true);
        setPermissions();
    }

    private void setMyLocation() {
        imgPoints.setImageDrawable(getResources().getDrawable(R.drawable.puntos_icn_on));
        mapa.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();

                if (!requiredOnce) {
                    myMarker = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(latitude, longitude))
                            .title(SessionManager.getInstance().getUser().getApodo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red))
                            .snippet("Este eres tú")
                    );

                    LatLng latLng = new LatLng(latitude, longitude);
                    mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.0f));
                    //mapa.animateCamera(CameraUpdateFactory.zoomTo(15));

                    requiredOnce = true;
                }
            }
        });

    }

    @OnClick(R.id.btn_search)
    public void search() {
        if (lista != null) {
            if (!search.getText().toString().isEmpty()) {
                for (int i = 0; i < lista.size(); i++) {
                    String current = lista.get(i).getTitulo();
                    double lat = Double.valueOf(lista.get(i).getLatitud());
                    double lon = Double.valueOf(lista.get(i).getLongitud());
                    String icono;
                    if (lista.get(i).getIcono() != null) {
                        icono = lista.get(i).getIcono();
                    } else {
                        icono = "";
                    }
                    Marker marcador = marker[i];
                    if (current.toLowerCase().startsWith(search.getText().toString().toLowerCase())) {
                        goToPoint(lat, lon, marcador, icono);
                    } else if (current.toLowerCase().endsWith(search.getText().toString().toLowerCase())) {
                        goToPoint(lat, lon, marcador, icono);
                    } else if (current.toLowerCase().contains(search.getText().toString().toLowerCase())) {
                        goToPoint(lat, lon, marcador, icono);
                    } else if (current.toLowerCase().equals(search.getText().toString().toLowerCase())) {
                        goToPoint(lat, lon, marcador, icono);
                    }
                }
            } else {
                Toast.makeText(this, "Debes introducir alguna palabra para realizar una búsqueda", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No existen puntos para realizar tu búsqueda", Toast.LENGTH_LONG).show();
        }
    }

    private void goToPoint(double lati, double longi, Marker marcado, String icono) {
        LatLng latLng = new LatLng(lati, longi);
        if (prevMarker != null) {
            if (!marcado.equals(prevMarker)) {
                if (icono != null) {
                    switchIcon(prevMarker, icono);
                    switchRedIcon(marcado, icono);
                } else {
                    prevMarker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn));
                    marcado.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red));
                }
                prevMarker = marcado;
            }
        } else {
            if (icono != null) {
                switchRedIcon(marcado, icono);
            } else {
                marcado.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red));
            }
            prevMarker = marcado;
        }
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
    }

    @Override
    public void setPoints(List<MapData> points) {
        if (points.size() > 0) {
            lista = points;

            ArrayList<String> filtro = new ArrayList<>();
            for (MapData data : points) {
                filtro.add(data.getTitulo());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_list_map, filtro);
            search.setAdapter(adapter);

            marker = new Marker[points.size()];
            for (int i = 0; i < points.size(); i++) {
                marker[i] = createMarker(points.get(i));
                marker[i].setSnippet(points.get(i).toString());
            }
            mapa.setOnMarkerClickListener(this);
        }
        hideProgress();
    }

    protected Marker createMarker(MapData point) {
        final double lat = Double.valueOf(point.getLatitud());
        final double lon = Double.valueOf(point.getLongitud());

        if (point.getIcono() != null) {
            switch (point.getIcono()) {
                case "tienda":
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_tienda_icn)));
                    break;
                case "cc":
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_cc_icn)));
                    break;
                case "hotel":
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_hotel_icn)));
                    break;
                case "estadio":
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_estadio_icn)));
                    break;
                case "bar-rest":
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_bar_rest_icn)));
                    break;
                default:
                    iPoints = mapa.addMarker(new MarkerOptions()
                            .position(new LatLng(lat, lon))
                            .title(point.getTitulo())
                            .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn)));
                    break;
            }
        } else {
            iPoints = mapa.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lon))
                    .title(point.getTitulo())
                    .icon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn)));
        }

        return iPoints;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker)) {
            return false;
        } else {
            for (int i = 0; i < lista.size(); i++) {
                if (marker.getSnippet().equals(lista.get(i).toString())) {
                    openDetails(lista.get(i));
                    if (prevMarker != null) {
                        for (int j = 0; j < lista.size(); j++) {
                            if ((prevMarker != null) && (prevMarker.getSnippet().equals(lista.get(j).toString()))) {
                                if (lista.get(j).getIcono() != null) {
                                    switchIcon(prevMarker, lista.get(j).getIcono());
                                } else {
                                    prevMarker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn));
                                }
                            }
                        }
                        if (lista.get(i).getIcono() != null) {
                            switchRedIcon(marker, lista.get(i).getIcono());
                        } else {
                            marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red));
                        }
                        prevMarker = marker;
                    } else {
                        if (lista.get(i).getIcono() != null) {
                            switchRedIcon(marker, lista.get(i).getIcono());
                        } else {
                            marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red));
                        }
                        prevMarker = marker;
                    }
                }
            }
            return true;
        }
    }

    private void openDetails(final MapData data) {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_map_details, null);

        FCMillonariosTextView title = v.findViewById(R.id.title);
        title.setText(data.getTitulo());

        FCMillonariosTextView address = v.findViewById(R.id.address);
        address.setText(data.getDireccion());

        FCMillonariosTextView fullAddress = v.findViewById(R.id.full_address);
        fullAddress.setText(data.getDireccion());

        FCMillonariosTextView time = v.findViewById(R.id.time);
        String fecha = Commons.simpleDateFormat(data.getFechaEvento()).substring(0, 2) + "/" + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(data.getFechaEvento()).substring(3, 5))).substring(0, 3) + "/" + data.getFechaEvento().substring(0, 4);
        String horario = "Horario: " + fecha.toUpperCase() + " / " + data.getHora();
        time.setText(horario);

        final ImageView picture = v.findViewById(R.id.picture);
        if (data.getImagenes().size() > 0) {
            Glide.with(this)
                    .load(data.getImagenes().get(0).getUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bsc_news_wm)
                            .error(R.drawable.bsc_news_wm))
                    .into(picture);
        } else if (data.getImagenes().size() > 1) {
            Glide.with(this)
                    .load(data.getImagenes().get(0).getUrl())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.bsc_news_wm)
                            .error(R.drawable.bsc_news_wm))
                    .into(picture);
        } else {
            picture.setVisibility(View.GONE);
        }

        ImageView btnShare = v.findViewById(R.id.btn_share);
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share(data.getId());
            }
        });

        Button btnGo = v.findViewById(R.id.btn_go);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "geo:" + Double.valueOf(data.getLatitud()) + "," + Double.valueOf(data.getLongitud())
                        + "?z=12&q=" + Double.valueOf(data.getLatitud()) + "," + Double.valueOf(data.getLongitud())
                        + ",(" + data.getTitulo() + ")";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(MapActivity.this, "No tienes una aplicación para mapas instalada", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView btnClose = v.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(v);
        dialog.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setRefreshing(boolean state) {

    }

    public void share(int id) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, Commons.getString(R.string.url_api).replace("api/", "") + "compartir/punto_referencia/" + id);
        share.setType("text/plain");
        startActivity(Intent.createChooser(share, "Compartir en: "));
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        hideProgress();
    }

    @Override
    public void onClickMenuItem(String fragment) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(SECCION_SELECTED, fragment);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new MapPermissionListener(this);
        MultiplePermissionsListener allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, "Permisos denegados")
                                .build());
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ).withListener(allPermissionsListener).check();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        token.continuePermissionRequest();
    }

    public void move() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        mapa.setMyLocationEnabled(true);
        setMyLocation();
    }

    public void showColombia() {
        LatLng latLng = new LatLng(4.0000000, -72.0000000);
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f));
    }

    public void showPermissionGranted(String permission) {
        move();
    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMarginEnd(Commons.dpToPx(15));
        params.setMarginStart(Commons.dpToPx(15));
        params.addRule(RelativeLayout.BELOW, R.id.content_header);
        params.setMargins(Commons.dpToPx(15), Commons.dpToPx(15), Commons.dpToPx(15), Commons.dpToPx(15));
        contentSearch.setLayoutParams(params);

        showColombia();
    }

    private void switchIcon(Marker prev, String newIcon) {
        switch (newIcon) {
            case "tienda":
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_tienda_icn));
                break;
            case "cc":
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_cc_icn));
                break;
            case "hotel":
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_hotel_icn));
                break;
            case "estadio":
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_estadio_icn));
                break;
            case "bar-rest":
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_bar_rest_icn));
                break;
            default:
                prev.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn));
                break;
        }
    }

    private void switchRedIcon(Marker marker, String newIcon) {
        switch (newIcon) {
            case "tienda":
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_tienda_icn_red));
                break;
            case "cc":
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_cc_icn_red));
                break;
            case "hotel":
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_hotel_icn_red));
                break;
            case "estadio":
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_estadio_icn_red));
                break;
            case "bar-rest":
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_bar_rest_icn_red));
                break;
            default:
                marker.setIcon(bitmapDescriptorFromVector(MapActivity.this, R.drawable.drop_map_default_icn_red));
                break;
        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 12) {
            month = months[num - 1];
        }
        return month;
    }

}