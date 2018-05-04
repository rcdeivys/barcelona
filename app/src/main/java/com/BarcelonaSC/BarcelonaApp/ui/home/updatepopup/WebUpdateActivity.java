package com.BarcelonaSC.BarcelonaApp.ui.home.updatepopup;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomWebView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by pedro Gomez on 13/03/18.
 */

public class WebUpdateActivity extends DialogFragment {

    private static final String TAG = WebUpdateActivity.class.getSimpleName();

    @BindView(R.id.wv_general)
    CustomWebView customWebView;

    @BindView(R.id.ib_return)
    ImageButton ibReturn;

    @BindView(R.id.tv_sub_header_title)
    FCMillonariosTextView tvSubHeaderTitle;

    private OnItemClickListener onItemClickListener;

    private String url;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);


        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialogo.setContentView(R.layout.activity_web_update);

        dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);

        ButterKnife.bind(this, dialogo);

        customWebView.setUrl(url);

        tvSubHeaderTitle.setText(Commons.getString(R.string.name_init_popup));

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
        if(onItemClickListener!=null){
            onItemClickListener.onClickWebView(UpdateConstants.TAG_CLOSE.getTag());
        }
        Log.i(TAG, "--->onDismiss");
    }

    public void setParams(String url){
        setParams(null,url);
    }

    public void setParams(OnItemClickListener onItemClickListener,String url) {
        this.onItemClickListener = onItemClickListener;
        this.url = url;
    }

    public interface OnItemClickListener {
        void onClickWebView(String TAG);
    }

}