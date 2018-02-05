package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.millonarios.MillonariosFC.R;


/**
 * Created by root on 7/17/17.
 */

public class FCMillonariosFontUtils {


//    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public static void applyCustomFont(TextView customFontTextView, Context context, AttributeSet attrs) {
        TypedArray attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.FCMillonariosTextView);

        int textStyle = attributeArray.getInt(R.styleable.FCMillonariosTextView_textStyle, 2);

        Typeface customFont = selectTypeface(context, textStyle);
        customFontTextView.setTypeface(customFont);

        attributeArray.recycle();
    }

    public static Typeface selectTypeface(Context context, int textStyle) {
        switch (textStyle) {

            case 0: // font_style_thin
                return FontCache.getTypeface(context, context.getString(R.string.font_path_helvetica_condensed_bold));

            case 1: // font_style_light
                return FontCache.getTypeface(context, context.getString(R.string.font_path_medium));

            case 2: // font_style_regular
                return FontCache.getTypeface(context, context.getString(R.string.font_path_thin));

            case 3: // font_style_medium
                return FontCache.getTypeface(context, context.getString(R.string.font_path_semibolditalic));

            case 4: // font_style_bold
                return FontCache.getTypeface(context, context.getString(R.string.font_agencyb));

            case 5: // font_style_black
                return FontCache.getTypeface(context, context.getString(R.string.font_agencyr));

            case 6:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_helvetica_condensed));

            case 7:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_roboto_bold));

            case 8:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_roboto_regular));

            case 9:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_roboto_light));

            default:
                return FontCache.getTypeface(context, context.getString(R.string.font_path_helvetica_condensed));
        }
    }
}
