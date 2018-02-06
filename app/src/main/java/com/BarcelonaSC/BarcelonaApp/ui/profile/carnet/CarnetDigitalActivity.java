package com.BarcelonaSC.BarcelonaApp.ui.profile.carnet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.text.DateFormatSymbols;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 11/11/17.
 */

public class CarnetDigitalActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    AppCompatImageButton btnBack;

    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.id_hincha)
    FCMillonariosTextView idHincha;
    @BindView(R.id.carnet_name)
    FCMillonariosTextView carnetName;
    @BindView(R.id.type_hincha)
    FCMillonariosTextView typeHincha;
    @BindView(R.id.text_registrado)
    FCMillonariosTextView registrado;
    @BindView(R.id.code_img)
    ImageView codeImg;
    @BindView(R.id.text_date_venc)
    FCMillonariosTextView textDateVenc;
    @BindView(R.id.content_carnet)
    RelativeLayout contentCarnet;

    public final static int CodeWith = 1000;
    public final static int CodeHeight = 175;

    private Bitmap mBitmap;
    private String mString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carnet_digital);
        ButterKnife.bind(this);
        carnetName.setText(sessionManager.getUser().getNombre() + " " + sessionManager.getUser().getApellido());
        Glide.with(getBaseContext()).load(sessionManager.getUser().getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        idHincha.setText("Hincha # " + sessionManager.getSession().getIdUser());
        registrado.setText("Desde el " + Commons.simpleDateFormat(sessionManager.getUser().getFechaRegistro()).substring(0, 2) + "/" +
                getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(sessionManager.getUser().getFechaRegistro()).substring(3, 5))) + "/" +
                sessionManager.getUser().getFechaRegistro().substring(0, 4));

        textDateVenc.setText("F.V:" + Commons.simpleDateFormat(sessionManager.getUser().getFechaVencimiento()));

        mString = sessionManager.getUser().getCodigo();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        generateBarCode(mString);

    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private Bitmap convertLayoutToImage() {
        RelativeLayout linearView = (RelativeLayout) this.getLayoutInflater().inflate(R.layout
                .view_carnet, null, false); //you can pass your xml layout

        linearView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        linearView.layout(0, 0, linearView.getMeasuredWidth(), linearView.getMeasuredHeight());

        linearView.setDrawingCacheEnabled(true);
        linearView.buildDrawingCache();
        return linearView.getDrawingCache();// creates bitmap and returns the same
    }

    public void generateBarCode(String data) {
        com.google.zxing.Writer c9 = new Code128Writer();
        try {
            BitMatrix bm = c9.encode(data, BarcodeFormat.CODE_128, Commons.dpToPx(150), Commons.dpToPx(40));
            mBitmap = Bitmap.createBitmap(Commons.dpToPx(150), Commons.dpToPx(40), Bitmap.Config.ARGB_8888);

            for (int i = 0; i < Commons.dpToPx(150); i++) {
                for (int j = 0; j < Commons.dpToPx(40); j++) {

                    mBitmap.setPixel(i, j, bm.get(i, j) ? Color.BLACK : Color.WHITE);
                }
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (mBitmap != null) {
            codeImg.setImageBitmap(mBitmap);
        }
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 12) {
            month = months[num - 1];
        }
        return month;
    }
}
