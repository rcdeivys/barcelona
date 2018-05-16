package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Carlos on 09/04/2018.
 */

public class SquareRoundedImage extends com.jackandphantom.circularimageview.RoundedImage {
    public SquareRoundedImage(Context context) {
        super(context);
    }

    public SquareRoundedImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundedImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
