package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {

    public final static String TAG_PHOTO = "photo";

    @BindView(R.id.iv_image_full_size)
    ImageView imagenContent;

    private String imagenUrl;


    public static Intent intent(String url, Context context) {
        Intent intent = new Intent(context, ImageActivity.class);
        intent.putExtra(TAG_PHOTO, url);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        if (getIntent().getParcelableExtra(TAG_PHOTO) != null) {
            imagenUrl = (String) getIntent().getStringExtra(TAG_PHOTO);
        }
        if(imagenUrl!=null)
            Glide.with(App.get())
                .load(imagenContent)
                .into(imagenContent);
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        finish();
    }

}
