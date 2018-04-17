package com.BarcelonaSC.BarcelonaApp.utils;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;

/**
 * Created by quint on 2/20/2018.
 */

public class FullScreenDialog extends DialogFragment {

    public static final String TAG = "FullScreenDialog";
    View view;
    SubscriptionListener subscriptionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
    }


    public void setListener(SubscriptionListener subscriptionListener) {
        this.subscriptionListener = subscriptionListener;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
        super.onCreateView(inflater, parent, state);

        view = getActivity().getLayoutInflater().inflate(R.layout.dialog_dorado, parent, false);
        ImageView container = (ImageView) view.findViewById(R.id.iv_popup_container);
       /* Glide.with(App.getAppContext())
                .load(ConfigurationManager.getInstance().getConfiguration().getUrlPopupEmbajadorDorado())
                .apply(new RequestOptions()
                                .centerCrop()
                        //.fitCenter() // no need for this
                )
                .into(container);*/
        view.findViewById(R.id.iv_closet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullScreenDialog.this.dismiss();
            }
        });

        view.findViewById(R.id.btn_suscribirme).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subscriptionListener.onSubscription();
            }
        });
        return view;
    }

    public interface SubscriptionListener {
        void onSubscription();
    }

    public View getDoradoView() {
        return view;
    }
}