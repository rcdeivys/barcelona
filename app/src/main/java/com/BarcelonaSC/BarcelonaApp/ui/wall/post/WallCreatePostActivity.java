package com.BarcelonaSC.BarcelonaApp.ui.wall.post;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallCreatePostEvent;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.permissions.CreatePostPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.di.DaggerWallPostComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.di.WallPostModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCreatePostActivity extends BaseActivity implements WallPostContract.View {

    private static int SELECT_IMAGE = 1001;
    private static int CAMERA_REQUEST = 1002;

    @BindView(android.R.id.content)
    View rootView;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;
    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.text_name_user)
    FCMillonariosTextView textNameUser;
    @BindView(R.id.input_post)
    EmojiconEditText message;
    @BindView(R.id.btn_gallery)
    ImageView btnGallery;
    @BindView(R.id.btn_cam)
    ImageView btnCam;
    @BindView(R.id.btn_emoji)
    ImageView btnEmoji;
    @BindView(R.id.img_select)
    ImageView imgSelect;
    @BindView(R.id.progressbar)
    RelativeLayout progressBar;
    @BindView(R.id.btn_send)
    AppCompatImageView btnSend;
    @BindView(R.id.icon_close_img)
    AppCompatImageView iconClose;

    boolean camPermission = false;
    boolean storePermission = false;

    @Inject
    WallPostPresenter presenter;

    private Uri imageUri;

    EmojIconActions emojIcon;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        ButterKnife.bind(this);
        initComponent();
        setPermissions();
        UserItem userItem = SessionManager.getInstance().getUser();
        Glide.with(this).load(userItem.getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        textNameUser.setText(userItem.getNombre() + " " + userItem.getApellido());
        emojIcon = new EmojIconActions(this, rootView, message, btnEmoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_keyboard, R.drawable.face);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e("Keyboard", "open");
            }

            @Override
            public void onKeyboardClose() {
                Log.e("Keyboard", "close");
            }
        });
        presenter.onAttach(this);
    }

    public void initComponent() {
        DaggerWallPostComponent.builder()
                .appComponent(App.get().component())
                .wallPostModule(new WallPostModule(this))
                .build().inject(WallCreatePostActivity.this);
    }

    @OnClick(R.id.btn_send)
    public void sendPost() {
        String imagePost = null;
        if (imageUri != null) {
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            imagePost = Constant.BASE64 + Commons.encodeImage(selectedImage);

        }
        message.clearFocus();
        presenter.sendPost(message.getText().toString().trim(), imagePost);
    }

    @OnClick(R.id.btn_back)
    public void finishCreatePost() {
        finish();
    }

    @OnClick(R.id.btn_gallery)
    public void openGallery() {
        if (camPermission && storePermission) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        } else {
            setPermissions();
        }
    }

    @OnClick(R.id.btn_cam)
    public void openCamera() {
        if (camPermission && storePermission) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = Uri.fromFile(Commons.getOutputMediaFile());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            setPermissions();
        }
    }

    @OnClick(R.id.icon_close_img)
    public void deleteImage() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        message.setLayoutParams(params);
        imageUri = null;
        imgSelect.setImageDrawable(null);
        iconClose.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            startCropImageActivity(data.getData());
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            startCropImageActivity(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                setImageSelect(resultUri);
                imageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
            }
        }
    }

    private void disableView(boolean disable) {
        btnCam.setClickable(disable);
        btnEmoji.setClickable(disable);
        btnGallery.setClickable(disable);
        btnSend.setClickable(disable);
        iconClose.setClickable(disable);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setFixAspectRatio(true)
                .start(getActivity());
    }

    private void setImageSelect(Uri uri) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        message.setLayoutParams(params);
        imgSelect.setImageURI(uri);
        iconClose.setVisibility(View.VISIBLE);
    }

    @Override
    public void createPost() {
        EventBus.getDefault().post(new WallCreatePostEvent(true));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        disableView(false);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showToastError(String error) {
        if (error.equals("")) {
            error = Commons.getString(R.string.toast_connection_error);
        }
        disableView(true);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new CreatePostPermissionListener(this);
        MultiplePermissionsListener allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(rootView, "Permisos denegados")
                                .build());
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(allPermissionsListener).check();

    }

    @TargetApi(Build.VERSION_CODES.M)
    public void showPermissionRationale(final PermissionToken token) {
        token.continuePermissionRequest();
    }

    public void showPermissionGranted(String permission) {
        Log.d(TAG, "showPermissionGranted " + permission);
        if (permission.equals(Manifest.permission.CAMERA)) {
            camPermission = true;
        }
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            storePermission = true;
        }
    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {
        Log.d(TAG, "showPermissionDenied " + permission + " boolean " + isPermanentlyDenied);
    }


}
