package com.BarcelonaSC.BarcelonaApp.utils;

import android.content.ClipData;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.BarcelonaSC.BarcelonaApp.models.IdealElevenData;
import com.BarcelonaSC.BarcelonaApp.models.NominaItem;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Carlos on 15/11/2017.
 */

public class CircleDragDrop extends CircleImageView {

    private IdealElevenData idealElevenData = new IdealElevenData();


    public int getPositionX() {
        return idealElevenData.getPositionX();
    }

    public void setPositionX(int positionX) {
        idealElevenData.setPositionX(positionX);
    }

    public int getPositionY() {
        return idealElevenData.getPositionY();
    }

    public void setPositionY(int positionY) {
        idealElevenData.setPositionY(positionY);
    }

    public NominaItem getPlayerPlayByPlay() {
        return idealElevenData.getPlayerPlayByPlay();
    }

    public void setPlayerPlayByPlay(NominaItem playerPlayByPlay) {
        idealElevenData.setPlayerPlayByPlay(playerPlayByPlay);
    }

    public IdealElevenData getIdealElevenData() {
        return idealElevenData;
    }

    public CircleDragDrop(Context context) {
        super(context);
        setOnTouchListener(new MyTouchListener());
    }

    public CircleDragDrop(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(new MyTouchListener());
    }

    public CircleDragDrop(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOnTouchListener(new MyTouchListener());
    }

    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }
}
