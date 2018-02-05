package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by Carlos on 03/02/2018.
 */

public class LinealSquareLayout extends LinearLayout{
    public LinealSquareLayout(Context context) {
        super(context);
    }

    public LinealSquareLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LinealSquareLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }
}
