package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Carlos on 09/01/2018.
 */
public class SquareHeightImageView extends com.jackandphantom.circularimageview.RoundedImage {
    public SquareHeightImageView(Context context) {
        super(context);
    }

    public SquareHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareHeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        setMeasuredDimension(height, height);
    }
}