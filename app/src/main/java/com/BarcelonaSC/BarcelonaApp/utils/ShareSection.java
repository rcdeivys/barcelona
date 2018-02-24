package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.ShareActionProvider;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;

import java.sql.Timestamp;
import java.text.Normalizer;

/**
 * Created by JulianOtalora on 21/10/2017.
 */

public class ShareSection {

    Context context;
    public static ShareActionProvider mShareActionProvider;


    public ShareSection(Context context) {
        this.context = context;

    }

    public static void share(Context context, String section) {

        share(section, "");
    }


    public static void share(String section, String url) {
        String shareSection = section.replace(" ", "");
        String time = new Timestamp(System.currentTimeMillis()).toString();
        String normalized = Normalizer.normalize(shareSection, Normalizer.Form.NFD);
        String accentRemoved = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        if (url.trim().isEmpty())
            share.putExtra(Intent.EXTRA_TEXT, Commons.getString(R.string.url_api) + "compartir/" + Commons.normalizedString(shareSection).toLowerCase() + "/" +System.currentTimeMillis());
        else
            share.putExtra(Intent.EXTRA_TEXT, url);

        share.setType("text/plain");

        Intent openInChooser = Intent.createChooser(share, "Compartir");
        openInChooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        App.getAppContext().startActivity(openInChooser);

    }


    public static void shareIdealEleven(String url) {
        share("", url);

    }

}