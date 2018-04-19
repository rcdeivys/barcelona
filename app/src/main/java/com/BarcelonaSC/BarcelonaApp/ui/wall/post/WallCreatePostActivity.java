package com.BarcelonaSC.BarcelonaApp.ui.wall.post;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallCreatePostEvent;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.permissions.CreatePostPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.di.DaggerWallPostComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.di.WallPostModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.mvp.WallPostPresenter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyFragment;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.iceteck.silicompressorr.SiliCompressor;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.apache.commons.lang3.StringEscapeUtils;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;

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

public class WallCreatePostActivity extends BaseActivity implements WallPostContract.View, GiphyAdapter.OnGifClickListener, CustomVideoView.CustomVideoViewOnListener {

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
    AppCompatImageView btnSend;
    @BindView(R.id.icon_close_img)
    AppCompatImageView iconClose;
    @BindView(R.id.text_create_post)
    FCMillonariosTextView textCreatePost;

    boolean camPermission = false;
    boolean storePermission = false;

    @Inject
    WallPostPresenter presenter;
    @BindView(R.id.fl_container_gif)
    FrameLayout flContainerGif;
    @BindView(R.id.btn_gif)
    ImageView btnGif;
    @BindView(R.id.video_select)
    CustomVideoView videoView;
    @BindView(R.id.ll_container)
    LinearLayout llContainer;
    @BindView(R.id.content_gif)
    LinearLayout contentGif;
    @BindView(R.id.btn_close_gif)
    AppCompatImageView btnCloseGif;
    @BindView(R.id.content_edit)
    LinearLayout contentEdit;
    @BindView(R.id.btn_edit)
    Button btnEdit;

    private Uri imageUri;
    private File video;
    private String decodableString;
    private String post_type = "foto";
    private String filePath;
    private String imageFile = "";

    EmojIconActions emojIcon;
    Dialog dialog;

    boolean editPost = false;

    WallItem wallItem;

    String thumbnail;

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
        emojIcon.setIconsIds(R.drawable.ic_keyboard, R.drawable.emoji_icn);
        videoView.setBackgroundColor(Commons.getColor(R.color.white));
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

        if (getIntent().hasExtra("wallitem")) {
            editPost();
        }
        presenter.onAttach(this);
    }

    public void initComponent() {
        DaggerWallPostComponent.builder()
                .appComponent(App.get().component())
                .wallPostModule(new WallPostModule(this))
                .build().inject(WallCreatePostActivity.this);
    }

    public void editPost() {
        btnSend.setVisibility(View.GONE);
        contentEdit.setVisibility(View.VISIBLE);
        textCreatePost.setText("EDITAR PUBLICACIÓN");
        wallItem = (WallItem) getIntent().getSerializableExtra("wallitem");
        message.setText(StringEscapeUtils.unescapeJava(wallItem.getMensaje()));
        imageFile = wallItem.getFoto();

        if (wallItem.getFoto() != null && !wallItem.getFoto().equals("")) {
            if (!Commons.isVideoFile(wallItem.getFoto())) {
                imgSelect.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.GONE);
                message.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                if (wallItem.getFoto().contains(".gif")) {
                    post_type = "gif";

                }
                Glide.with(getBaseContext()).load(wallItem.getFoto()).into(imgSelect);
            } else {
                imgSelect.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                message.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                videoView.setImage(getBaseContext(), wallItem.getThumbnail());
                videoView.setVideoUrl(wallItem.getFoto(), 0, false);
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (post_type.equals("foto")) {
                    String imagePost = wallItem.getTipoPost();
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
                    presenter.editPost(wallItem.getIdpost(), message.getText().toString().trim(), post_type, imagePost, null);
                    imageFile = imagePost;
                } else if (post_type.equals("gif")) {
                    message.clearFocus();
                    String photo;
                    photo = imageUri != null ? imageUri.toString() : wallItem.getFoto();
                    if (photo != null)
                        presenter.editPost(wallItem.getIdpost(), message.getText().toString().trim(), post_type, photo, null);
                } else if (post_type.equals("video")) {
                    if (video != null) {
                        message.clearFocus();
                        Commons.initUploadWithTransferUtility(video).setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    presenter.editPost(wallItem.getIdpost(), message.getText().toString(), post_type, "https://s3.amazonaws.com/millos-videos/" + video.getName(), thumbnail);
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                showProgress();
                            }

                            @Override
                            public void onError(int id, Exception ex) {

                            }
                        });

                    }
                }
            }
        });
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
            presenter.sendPost(message.getText().toString().trim(), post_type, imagePost, null);
        } else if (post_type.equals("gif")) {
            message.clearFocus();
            presenter.sendPost(message.getText().toString().trim(), post_type, imageUri.toString(), null);
        } else if (post_type.equals("video")) {
            if (video != null) {
                message.clearFocus();
                Commons.initUploadWithTransferUtility(video).setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        if (TransferState.COMPLETED == state) {
                            presenter.sendPost(message.getText().toString(), post_type, "https://s3.amazonaws.com/millos-videos/" + video.getName(), thumbnail);
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        showProgress();
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        hideProgress();
                        Toast.makeText(getBaseContext(), "Hubo un error al subir el video, vuelve a intentarlo", Toast.LENGTH_LONG).show();

                    }
                });

            }
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

    // @OnClick(R.id.btn_gallery)
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

    @OnClick(R.id.btn_cam)
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

    // @OnClick(R.id.btn_cam)
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
        videoView.pause();
        videoView.setVisibility(View.GONE);
        iconClose.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_gallery)
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
            }
        }

        if ((requestCode == SELECT_VIDEO) && (resultCode == RESULT_OK) && (data != null)) {
            Uri selectedVideo = data.getData();
            setVideoSelect(selectedVideo);
        }

        if ((requestCode == REQUEST_VIDEO_CAPTURE) && (resultCode == RESULT_OK) && (data != null)) {
            Uri selectedVideo = data.getData();
            setVideoSelect(selectedVideo);
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

    private void setVideoSelect(Uri selectedVideo) {
        String[] projection = {MediaStore.Video.Media.DATA};
        new VideoCompressAsyncTask(getBaseContext(), selectedVideo, getApplicationContext().getFilesDir().getAbsolutePath()).execute();
    }

    @Override
    public void onPrepared() {
        videoView.setLayoutParams(new ViewGroup.LayoutParams(videoView.getVideoWidth(), videoView.getVideoHeight()));
    }

    @Override
    public void videoIsDorado() {

    }

    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;
        Uri uri;
        String path;
        ProgressDialog dialogProgress;

        public VideoCompressAsyncTask(Context context, Uri uri, String path) {
            mContext = context;
            this.uri = uri;
            this.path = path;

            dialogProgress = ProgressDialog.show(WallCreatePostActivity.this, "", "Vídeo cargando, por favor espera...", true);
            dialogProgress.setMax(100);
            dialogProgress.show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {
                filePath = SiliCompressor.with(getBaseContext()).compressVideo(uri, path, 1280, 720, 0);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;

        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);


            try {
                File imageFile = new File(compressedFilePath);
                dialogProgress.dismiss();
                video = imageFile;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                message.setLayoutParams(params);
                imgSelect.setVisibility(View.GONE);
                iconClose.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);
                videoView.setVideoUrl(imageFile.toString(), 0, false);
                videoView.setImage(getBaseContext(), imageFile.toString());
                videoView.pause();
                videoView.controllerVisible(View.GONE);
                videoView.requestFocus();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Hubo un error inesperado, vuelve a intentarlo", Toast.LENGTH_LONG).show();
                hideProgress();
            }

            try {
                thumbnail = Constant.BASE64 + Commons.encodeImage(Commons.createThumbnailAtTime(compressedFilePath, System.currentTimeMillis()));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void createPost() {
        WallItem wallItem = new WallItem();
        wallItem.setFoto(imageFile);
        wallItem.setMensaje(message.getText().toString());
        EventBus.getDefault().post(new WallCreatePostEvent(wallItem));
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
        hideProgress();
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