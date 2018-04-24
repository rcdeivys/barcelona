package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 03/04/2018.
 */

public class CustomProfileRelativeView extends RelativeLayout {

    LayoutInflater mInflater;

    public CustomProfileRelativeView(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();

    }

    public CustomProfileRelativeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public CustomProfileRelativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.custom_profile_icon, this, true);
        CircleImageView circleImageView = (CircleImageView) v.findViewById(R.id.civ_icon_profile);
        Glide.with(App.get())
                .load(SessionManager.getInstance().getUser().getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(circleImageView);

    }
}