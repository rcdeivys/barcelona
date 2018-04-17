package com.BarcelonaSC.BarcelonaApp.ui.wall.comment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.models.BeneficioData;
import com.BarcelonaSC.BarcelonaApp.models.News;
import com.BarcelonaSC.BarcelonaApp.models.VideoReality;
import com.BarcelonaSC.BarcelonaApp.models.response.MultimediaDataResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.DaggerWallCommentComponent;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.commons.BaseActivity;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallCreateCommentEvent;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.permissions.CreateCommentPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.WallProfileDialog;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.WallCommentModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter.WallCommentClickListener;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallCommentActivity extends BaseActivity implements WallCommentContract.View, WallCommentClickListener {

    public static final String ID_POST = "id_post";
    public static final String COUNT_CLAP = "count_clap";

    @BindView(android.R.id.content)
    View rootView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_comment)
    RecyclerView recyclerView;
    @BindView(R.id.count_like_comment)
    FCMillonariosTextView countClap;
    @BindView(R.id.btn_back)
    AppCompatImageView btnBack;
    @BindView(R.id.input_comment)
    EmojiconEditText message;
    @BindView(R.id.btn_emoji)
    ImageView btnEmoji;
    @BindView(R.id.send_icon)
    AppCompatImageView btnSend;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.content_write_comment)
    ConstraintLayout contentWriteComment;
    @BindView(R.id.content_image)
    RelativeLayout contentImage;
    @BindView(R.id.image_select)
    ImageView imgSelect;
    @BindView(R.id.close_icon)
    ImageView closeImage;
    @BindView(R.id.btn_camera)
    ImageView btnCamera;

    private LinearLayoutManager mLayoutManager;

    private WallCommentAdapter wallCommentAdapter;

    private String id_post;
    private String count_clap;

    private boolean waiting = false;
    InputMethodManager imm;

    @Inject
    WallCommentPresenter presenter;

    private Uri imageUri;

    EmojIconActions emojIcon;

    boolean camPermission = false;
    boolean storePermission = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_comment);
        ButterKnife.bind(this);
        initRecyclerView();
        initComponent();
        setPermissions();
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        id_post = getIntent().getStringExtra(ID_POST);
        count_clap = getIntent().getStringExtra(COUNT_CLAP);

        presenter.load(id_post, false);
        countClap.setText(count_clap);

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
        //imm.hideSoftInputFromWindow(message.getWindowToken(), 0);
    }

    @OnClick(R.id.btn_back)
    public void finisComment() {
        finish();
    }

    @OnClick(R.id.send_icon)
    public void sendComment() {
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
        presenter.createComment(id_post, message.getText().toString(), imagePost);

    }

    @OnClick(R.id.btn_camera)
    public void onSelectImageClick() {
        if (camPermission && storePermission) {
            CropImage.startPickImageActivity(getActivity());
        }
    }

    @OnClick(R.id.close_icon)
    public void deleteImage() {
        imageUri = null;
        contentImage.setVisibility(View.GONE);
    }

    private void disableView(boolean disable) {
        closeImage.setClickable(disable);
        btnEmoji.setClickable(disable);
        btnSend.setClickable(disable);
        btnCamera.setClickable(disable);
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setFixAspectRatio(true)
                .start(getActivity());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(getActivity(), data);
            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                startCropImageActivity(imageUri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Glide.with(getActivity()).load(result.getUri()).into(imgSelect);
                imageUri = result.getUri();
                contentImage.setVisibility(View.VISIBLE);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
            }
        }
    }

    public void initComponent() {
        DaggerWallCommentComponent.builder()
                .appComponent(App.get().component())
                .wallCommentModule(new WallCommentModule(this))
                .build().inject(WallCommentActivity.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        List<WallCommentItem> itemList = new ArrayList<>();

        wallCommentAdapter = new WallCommentAdapter(getBaseContext(), itemList);
        wallCommentAdapter.setWallCommentClickListener(this);
        recyclerView.setAdapter(wallCommentAdapter);
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                presenter.load(id_post, true);
            }
        });
        wallCommentAdapter.setTextEmptyListColor(Commons.getColor(R.color.textColorPrimary));
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    presenter.loadPage(id_post, current_page);
                    wallCommentAdapter.showLoading();
                }
            }
        };
    }

    @Override
    public void onShowProfileListener(UserItem userItem) {
        WallProfileDialog.instance(userItem).show(getSupportFragmentManager(), "WallProfileDialog");
    }

    @Override
    public void onClapCommentListener(String idcomentario) {
        presenter.clapComment(idcomentario);
    }


    @Override
    public void setLoad(List<WallCommentItem> list) {
        swipeRefreshLayout.setRefreshing(false);
        wallCommentAdapter.addAll(list);
        EventBus.getDefault().post(new WallCreateCommentEvent(wallCommentAdapter.getCurrentItemCount()));
    }

    @Override
    public void createComment() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
                if (imageUri != null) {
                    imageUri = null;
                    contentImage.setVisibility(View.GONE);
                }
                message.setText("");
                presenter.load(id_post, true);
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
        wallCommentAdapter.hideLoading();
        disableView(true);
    }

    @Override
    public void setRefreshing(boolean state) {
        if (state)
            recyclerView.smoothScrollToPosition(wallCommentAdapter.getCurrentItemCount());
    }

    @Override
    public void showToastError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new CreateCommentPermissionListener(this);
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

    @Override
    public void navigateToVideoNewsActivity(News news, int currentPosition) {

    }

    @Override
    public void navigateToVideoMultimediaActivity(MultimediaDataResponse multimediaDataResponse, int currentPosition) {

    }

    @Override
    public void navigateToVideoBeneficiosActivity(BeneficioData beneficioData, int currentVideo) {

    }

    @Override
    public void navigateToGalleryActivity(News news) {

    }

    @Override
    public void navigateVirtualActivity(VideoReality videoReality) {

    }
}