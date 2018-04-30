package com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.paymentwebviews;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.SuscripcionData;
import com.BarcelonaSC.BarcelonaApp.ui.hinchadorado.HinchaDoradoConsts;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayUPaymentActivity extends DialogFragment {

    private static final String TAG = PayUPaymentActivity.class.getSimpleName();
    @BindView(R.id.wv_general)
    CustomWebView customWebView;
    @BindView(R.id.ib_return)
    ImageButton ibReturn;
    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;
    private OnItemClickListener onItemClickListener;
    SuscripcionData suscripcionData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.setContentView(R.layout.activity_pay_upayment);

        ButterKnife.bind(this, dialogo);
        String url = Commons.getString(R.string.url_api).concat("api/pago/payu");
        String tokenUser = SessionManager.getInstance().getSession().getToken();
        customWebView.setUrl(url.concat("/" + tokenUser).concat("/" + suscripcionData.getId()));
        tvSubHeaderTitle.setText(Commons.getString(R.string.with_pay_u));

        ibReturn.setVisibility(View.VISIBLE);

        return dialogo;
    }

    @OnClick(R.id.ib_return)
    public void onViewClicked() {
        this.dismissAllowingStateLoss();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        onItemClickListener.onClickWebView(HinchaDoradoConsts.TAG_Close_WebView);
        Log.i(TAG, "--->onDismiss");
    }

    public void setParams(OnItemClickListener onItemClickListener, SuscripcionData suscripcionData) {
        this.onItemClickListener = onItemClickListener;
        this.suscripcionData = suscripcionData;
    }

    public interface OnItemClickListener {
        void onClickWebView(String TAG);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
