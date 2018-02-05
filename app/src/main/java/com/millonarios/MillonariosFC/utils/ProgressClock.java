package com.millonarios.MillonariosFC.utils;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.RecoverySystem;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.millonarios.MillonariosFC.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Carlos on 16/01/2018.
 */

public class ProgressClock extends RelativeLayout {

    LayoutInflater mInflater;
    long time;
    TextView tvTime;
    DonutProgress donutProgress;
    ProgressClockListener progressClockListener;

    public ProgressClock(Context context) {
        super(context);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ProgressClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public ProgressClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        init();
    }

    public void init() {
        View v = mInflater.inflate(R.layout.custom_progress_clock, this, true);
        tvTime = (TextView) v.findViewById(R.id.tv_time);
        donutProgress = (DonutProgress) v.findViewById(R.id.donut_progress);
    }

    public void setProgressListener(ProgressClockListener progressClockListener) {
        this.progressClockListener = progressClockListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

    public void initclock(String dateInicio, String dateFinal, ProgressClockListener progressClockListener) {
        init();
        setProgressListener(progressClockListener);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("es", "ES"));
        long diffActual = 0L;
        long diffCompleta = 0L;
        try {
            String date = dateFormat.format(Calendar.getInstance().getTime());
            dateFinal = dateFinal + " 00:00:00";
            dateInicio = dateInicio + " 00:00:00";
            Date dActual = dateFormat.parse(date);
            Date dInicial = dateFormat.parse(dateInicio);
            Date dFinal = dateFormat.parse(dateFinal);
            diffActual = (dFinal.getTime() - dInicial.getTime()) - (dActual.getTime() - dInicial.getTime());
            diffCompleta = dFinal.getTime() - dInicial.getTime();
            donutProgress.setMax((int) ((diffCompleta)));
            if (diffCompleta < (dActual.getTime() - dInicial.getTime()))
                donutProgress.setProgress(diffCompleta);
            else
                donutProgress.setProgress((dActual.getTime() - dInicial.getTime()));
            Log.i("TAG", "/--->max " + ((diffCompleta)));
            Log.i("TAG", "/--->progress " + (((dActual.getTime() - dInicial.getTime()))));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        tvTime.setText(convertMilisToTime(diffActual));
        initCountDownTimer(diffActual);

    }

    private String convertMilisToTime(long milisecond) {
        Long seconds = (milisecond / 1000) % 60;
        Long minutes = ((milisecond / (1000 * 60)) % 60);
        Long hours = ((milisecond / (1000 * 60 * 60)) % 24);
        Long days = (milisecond / (1000 * 60 * 60 * 24));

        return days + "d " + hours + "h " + minutes + "m " + seconds + "s ";

    }

    private void initCountDownTimer(long milisecond) {
        //1000 = 1 second interval
        CountDownTimer cdt = new CountDownTimer(milisecond, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTime.setText((convertMilisToTime(millisUntilFinished)));
                donutProgress.setProgress(donutProgress.getProgress() + 1000);

            }

            @Override
            public void onFinish() {
                tvTime.setText("Finalizada");
                donutProgress.setMax(100);
                donutProgress.setProgress(100);
                progressClockListener.onFinish();

            }
        };
        cdt.start();
    }

    public interface ProgressClockListener {

        void onFinish();
    }
}
