package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.graphics.Typeface;

import com.millonarios.MillonariosFC.R;


/**
 * Created by Carlos on 01/10/2017.
 */

public class FontsUtil {


/*    public static Typeface getKlavikaMediumFonts(Context context) {
        return createFromAsset(R.string.font_klavika_medium, context);
    }

    public static Typeface getCircularStdBookFonts(Context context) {
        return createFromAsset(R.string.font_circular_std_book, context);
    }

    public static Typeface getKlavikaRegularFonts(Context context) {
        return createFromAsset(R.string.font_klavika_regular, context);
    }*/

    public static Typeface getHelveticaCondesedBold0Fonts(Context context) {
        return createFromAsset(R.string.font_helvetica_condensed_bold_0, context);
    }

    public static Typeface getHelveticaCondesed2Fonts(Context context) {
        return createFromAsset(R.string.font_helvetica_condensed_2, context);
    }

    public static Typeface getRobotoRegularFonts(Context context) {
        return createFromAsset(R.string.font_roboto_regular, context);
    }

    public static Typeface getOpenSansReularFonts(Context context) {
        return createFromAsset(R.string.font_opensans_regular, context);
    }
    public static Typeface getOpenSansBoldFonts(Context context) {
        return createFromAsset(R.string.font_opensans_bold, context);
    }
    private static Typeface createFromAsset(int fontsName, Context context) {
        return Typeface.createFromAsset(context.getAssets(), "fonts/" + context.getString(fontsName));
    }


}
