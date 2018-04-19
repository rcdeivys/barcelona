package com.BarcelonaSC.BarcelonaApp.ui.wall.comment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallEvent;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.permissions.EditCommentPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.DaggerWallCommentComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.WallCommentModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import okhttp3.MultipartBody;

/**
 * Created by Leonardojpr on 3/29/18.
 */

public class WallCommentEditActivity extends BaseActivity implements WallCommentContract.View, GiphyAdapter.OnGifClickListener {

    private static int SELECT_VIDEO = 1000;
    private static int SELECT_IMAGE = 1001;
    private static int CAMERA_REQUEST = 1002;
    private static final int REQUEST_VIDEO_CAPTURE = 1003;

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
    Button btnSend;
    @BindView(R.id.icon_close_img)
    AppCompatImageView iconClose;

    boolean camPermission = false;
    boolean storePermission = false;

    @Inject
    WallCommentPresenter presenter;
    @BindView(R.id.fl_container_gif)
    FrameLayout flContainerGif;
    @BindView(R.id.btn_gif)
    ImageView btnGif;
    @BindView(R.id.video_select)
    VideoView videoView;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.content_gif)
    LinearLayout contentGif;
    @BindView(R.id.btn_close_gif)
    AppCompatImageView btnCloseGif;

    private Uri imageUri;
    private MultipartBody.Part video;
    private String decodableString;
    private String post_type = "foto";
    private String filePath;

    EmojIconActions emojIcon;
    Dialog dialog;

    String idPost;
    WallCommentItem wallCommentItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_comment_edit);
        ButterKnife.bind(this);
        initComponent();
        setPermissions();
        UserItem userItem = SessionManager.getInstance().getUser();
        Glide.with(this).load(userItem.getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        idPost = getIntent().getStringExtra("idpost");
        wallCommentItem = (WallCommentItem) getIntent().getSerializableExtra("wallCommentItem");
        textNameUser.setText(userItem.getNombre() + " " + userItem.getApellido());
        emojIcon = new EmojIconActions(this, rootView, message, btnEmoji);
        emojIcon.ShowEmojIcon();

        emojIcon.setIconsIds(R.drawable.ic_keyboard, R.drawable.emoji_icn);
        imgSelect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (contentGif.getVisibility() == View.VISIBLE) {
                    contentGif.setVisibility(View.GONE);
                    llContainer.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });

        message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (contentGif.getVisibility() == View.VISIBLE) {
                    contentGif.setVisibility(View.GONE);
                    llContainer.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
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

        message.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (contentGif.getVisibility() == View.VISIBLE) {
                    contentGif.setVisibility(View.GONE);
                    llContainer.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        presenter.onAttach(this);

        message.setText(StringEscapeUtils.unescapeJava(wallCommentItem.getComentario()));
       /* if (wallCommentItem.getFoto() != null && !wallCommentItem.getFoto().equals("")) {
            Glide.with(getBaseContext()).load(wallCommentItem.getFoto()).into(imgSelect);
        }*/
    }

    public void initComponent() {
        DaggerWallCommentComponent.builder()
                .appComponent(App.get().component())
                .wallCommentModule(new WallCommentModule(this))
                .build().inject(WallCommentEditActivity.this);
    }

    @OnClick(R.id.btn_send)
    public void sendPost() {
        if (post_type.equals("foto")) {
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
            presenter.editComment(idPost, wallCommentItem.getIdcomentario(), message.getText().toString().trim(), wallCommentItem.getFoto());
        } else if (post_type.equals("gif")) {
            message.clearFocus();
            presenter.editComment(idPost, wallCommentItem.getIdcomentario(), message.getText().toString().trim(), wallCommentItem.getFoto());
        }
    }

    @OnClick({R.id.btn_back, R.id.descartar})
    public void finishCreatePost() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (contentGif.getVisibility() == View.VISIBLE) {
            contentGif.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.btn_gallery)
    public void openGallery() {
        if (camPermission && storePermission) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
        } else {
            setPermissions();
        }
    }

    public void openVideoGallery() {
        if (camPermission && storePermission) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            intent.setType("video/*");
            startActivityForResult(intent, SELECT_VIDEO);
        } else {
            setPermissions();
        }
    }

    public boolean showVideoCamera() {
        post_type = "video";
        openVideoCamera();
        return false;
    }

    //@OnClick(R.id.btn_cam)
    public void showCameraDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_camera, null);
        AppCompatImageView foto = v.findViewById(R.id.btn_foto);
        foto.setImageDrawable(getResources().getDrawable(R.drawable.camera_icn));
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
                dialog.dismiss();
            }
        });
        ImageView video = v.findViewById(R.id.btn_video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_type = "video";
                openVideoCamera();
                dialog.dismiss();
            }
        });
        ImageView close = v.findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.show();
    }

    @OnClick(R.id.btn_cam)
    public void openCamera() {
        if (camPermission && storePermission) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            imageUri = Uri.fromFile(Commons.getOutputMediaFile());
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            setPermissions();
        }
    }

    public void openVideoCamera() {
        if (camPermission && storePermission) {
            long fileSize = 7 * 1024 * 1024;
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            // intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, fileSize);
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
            }
        } else {
            setPermissions();
        }
    }

    @OnClick(R.id.icon_close_img)
    public void deleteImage() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        message.setLayoutParams(params);
        imageUri = null;
        video = null;
        imgSelect.setImageDrawable(null);
        videoView.setVisibility(View.GONE);
        iconClose.setVisibility(View.GONE);
    }

    //@OnClick(R.id.btn_gallery)
    public void showDialog() {
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_camera, null);
        ImageView foto = v.findViewById(R.id.btn_foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                dialog.dismiss();
            }
        });
        ImageView video = v.findViewById(R.id.btn_video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post_type = "video";
                openVideoGallery();
                dialog.dismiss();
            }
        });
        ImageView close = v.findViewById(R.id.btn_close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(v);
        dialog.show();
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
                post_type = "foto";
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
    public void setLoad(List<WallCommentItem> list) {

    }

    @Override
    public void deletePost(String msg) {

    }

    @Override
    public void deleteComment(String msg) {

    }

    @Override
    public void setLike() {

    }

    @Override
    public void createComment() {

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
    public void setRefreshing(boolean state) {

    }

    @Override
    public void showToastError(String error) {
        if (error.equals("")) {
            error = Commons.getString(R.string.toast_connection_error);
        }
        disableView(true);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void editComment() {
        EventBus.getDefault().post(WallEvent.newInstance(WallEvent.WALL_EDIT_COMMENT_POST, ""));
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 1000);
    }

    @Override
    public void reportarPost() {

    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new EditCommentPermissionListener(this);
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

    @OnClick(R.id.btn_gif)
    public void onViewClicked() {
        if (contentGif.getVisibility() == View.VISIBLE) {
            contentGif.setVisibility(View.GONE);
            llContainer.setVisibility(View.VISIBLE);
        } else {
            GiphyFragment giphyFragment = new GiphyFragment();
            giphyFragment.setGifListener(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_container_gif
                    , giphyFragment).commit();
            contentGif.setVisibility(View.VISIBLE);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(message.getWindowToken(), 0);
        }

    }

    @OnClick(R.id.btn_close_gif)
    public void closeGif() {
        contentGif.setVisibility(View.GONE);
    }

    @Override
    public void onClickGif(String link) {
        Log.i("TAG", "/////--->1111" + link);
        post_type = "gif";
        contentGif.setVisibility(View.GONE);
        llContainer.setVisibility(View.VISIBLE);
        iconClose.setVisibility(View.VISIBLE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        message.setLayoutParams(params);
        imageUri = Uri.parse(link);
        Log.i("TAG", "/////--->222" + imageUri);
        Glide.with(this)
                .asGif()
                .load(link)
                .apply(new RequestOptions()
                        .placeholder(new ColorDrawable(ContextCompat.getColor(this, R.color.gray_300)))
                        .error(new ColorDrawable(ContextCompat.getColor(this, R.color.gray_300)))
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(imgSelect);

    }
}