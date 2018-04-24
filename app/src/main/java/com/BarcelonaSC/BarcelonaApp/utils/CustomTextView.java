package com.BarcelonaSC.BarcelonaApp.utils;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * Created by Leonardojpr on 7/17/17.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
        FCMillonariosFontUtils.applyCustomFont(this, context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        FCMillonariosFontUtils.applyCustomFont(this, context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        FCMillonariosFontUtils.applyCustomFont(this, context, attrs);
    }
}
