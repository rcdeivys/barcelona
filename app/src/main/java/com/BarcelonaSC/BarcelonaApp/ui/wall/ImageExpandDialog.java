package com.BarcelonaSC.BarcelonaApp.ui.wall;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cesar on 12/3/2018.
 */

public class ImageExpandDialog extends DialogFragment {

    @BindView(R.id.frame_back)
    FrameLayout frame_back;
    @BindView(R.id.img1)
    PhotoView img1;

    public Drawable imageToExpand = null;
    private String url;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle1);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.setContentView(R.layout.dialog_imagen_expand);
        ButterKnife.bind(this, dialogo);
        expandImage();
        return dialogo;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
            dialog.getWindow().setLayout(width, height);
        }
    }

    @OnClick(R.id.frame_back)
    public void closeDialog() {
        this.dismiss();
    }

    private void expandImage() {
        if (url == null)
            img1.setImageDrawable(getImageToExpand() != null ? getImageToExpand() : Commons.getDrawable(R.drawable.silueta));
        else {
            Glide.with(App.get())
                    .asGif()
                    .load(url)
                    .thumbnail(0.1f)
                    .into(img1);
        }
    }

    public Drawable getImageToExpand() {
        return imageToExpand;
    }

    public void setImageToExpand(Drawable imageToExpand) {
        this.imageToExpand = imageToExpand;
    }

    public void setImageToExpand(String url) {
        this.url = url;
    }
}