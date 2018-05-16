package com.BarcelonaSC.BarcelonaApp.ui.home.updatepopup;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Pedro Gomez on 13/03/18.
 */

public class UpdateDialog extends DialogFragment {

    private static final String TAG = UpdateDialog.class.getSimpleName();

    @BindView(R.id.btn_1)
    Button btn1;

    @BindView(R.id.btn_2)
    Button btn2;

    @BindView(R.id.iv_update)
    ImageView imageBanner;

    private String banner;
    private String url;
    private String section;
    private String target;

    private OnItemClickListener listener;

    private boolean isBtn1Act;
    private String btnTxt1;
    private boolean isBtn2Act;
    private String btnTxt2;

    public void setParams(String banner, String url, String section, String target, boolean isBtn1Act, String btnTxt1, boolean isBtn2Act, String btnTxt2, OnItemClickListener listener) {
        this.banner = banner;
        this.url = url;
        this.section = section;
        this.target = target;
        this.listener = listener;
        this.isBtn1Act = isBtn1Act;
        this.btnTxt1 = btnTxt1;
        this.isBtn2Act = isBtn2Act;
        this.btnTxt2 = btnTxt2;
    }

    public void setParams(String banner, String url, String section, String target, boolean isBtn1Act, String btnTxt1, OnItemClickListener listener) {
        this.banner = banner;
        this.url = url;
        this.section = section;
        this.target = target;
        this.listener = listener;
        this.isBtn1Act = isBtn1Act;
        this.btnTxt1 = btnTxt1;
        this.isBtn2Act = false;
        this.btnTxt2 = "";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_update);
        dialogo.getWindow().setLayout(Commons.getWidthDisplay(), ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialogo);
        if (isBtn1Act) {
            btn1.setText(btnTxt1);
        } else {
            btn1.setVisibility(View.GONE);
        }
        if (isBtn2Act) {
            btn2.setText(btnTxt2);
        } else {
            btn2.setVisibility(View.GONE);
        }
        Glide.with(getContext())
                .load(banner)
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(imageBanner);
        return dialogo;
    }


    /* Esto verifica si una URL no es nula para lanzar un web view
     * al presionar el boton de "Aceptar". Si es nula verifica si la seccion
     * no lo es, para ir a una seccion especifica de la app.
     * TODO: la seccion esta harcodeada esperando por el recurso de backend
     * */
    @OnClick(R.id.iv_update)
    void onAcceptUpdateFromBanner() {
        if (!isBtn1Act) {
            try {
                target = target.toUpperCase();
                switch (target) {
                    case Constant.Key.POPUP_EXTERNO:
                        listener.onGoExternalBrowser(url);
                        break;
                    case Constant.Key.POPUP_INTERNO:
                        listener.onGoWebView(url);
                        break;
                    case Constant.Key.POPUP_SECCION:
                        listener.onGoSection(section);
                        break;
                    case Constant.Key.POPUP_UPDATE:
                        updateApp();
                        break;
                    default:
                        //THIS IS INFORMATIVO
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(getContext(), "SUCEDIO ALGO INESPERADO", Toast.LENGTH_SHORT).show();
            }
            if (!target.equals(Constant.Key.POPUP_UPDATE))
                this.dismiss();
        }
    }

    /**
     * Esto verifica si una URL no es nula para lanzar un web view
     * al presionar el boton de "Aceptar". Si es nula verifica si la seccion
     * no lo es, para ir a una seccion especifica de la app.
     * TODO: la seccion esta harcodeada esperando por el recurso de backend
     */
    @OnClick(R.id.btn_1)
    void onAcceptUpdate() {
        try {
            target = target.toUpperCase();
            switch (target) {
                case Constant.Key.POPUP_EXTERNO:
                    listener.onGoExternalBrowser(url);
                    break;
                case Constant.Key.POPUP_INTERNO:
                    listener.onGoWebView(url);
                    break;
                case Constant.Key.POPUP_SECCION:
                    listener.onGoSection(section);
                    break;
                case Constant.Key.POPUP_UPDATE:
                    updateApp();
                    break;
                default:
                    //THIS IS INFORMATIVO
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "SUCEDIO ALGO INESPERADO", Toast.LENGTH_SHORT).show();
        }
        if (!target.equals(Constant.Key.POPUP_UPDATE))
            this.dismiss();
    }

    private void updateApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + App.get().getPackageName()));
        try {
            startActivity(intent);
        } catch (Exception e) {
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + App.get().getPackageName()));
            startActivity(intent);
        }
    }

    @OnClick(R.id.btn_2)
    void onCancelUpdate() {
        this.dismiss();
    }

    public interface OnItemClickListener {
        /**
         * Esto es usado para ir a un web view dada la url
         *
         * @param url direccion que mostrara el web view
         */
        void onGoWebView(String url);

        /**
         * Esto es usado para ir a una seccion de la aplicacion
         *
         * @param section es una cadena con el nombre de la seccion a la que se desea ir
         */
        void onGoSection(String section);

        /**
         * Esto es usado para ir a al browser del dispositivo
         *
         * @param url es la direccion que mostrara el browser
         */
        void onGoExternalBrowser(String url);

    }


    @Override
    public String toString() {
        return "UpdateDialog{" +
                "banner='" + banner + '\'' +
                ", url='" + url + '\'' +
                ", section='" + section + '\'' +
                ", target='" + target + '\'' +
                ", btn1='" + isBtn1Act + '\'' +
                ", btn2='" + isBtn2Act + '\'' +
                '}';
    }
}
