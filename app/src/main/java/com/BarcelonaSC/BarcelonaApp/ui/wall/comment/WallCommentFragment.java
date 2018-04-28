package com.BarcelonaSC.BarcelonaApp.ui.wall.comment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseFragment;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallCreatePostEvent;
import com.BarcelonaSC.BarcelonaApp.eventbus.WallEvent;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.models.WallReportarPost;
import com.BarcelonaSC.BarcelonaApp.permissions.CreateCommentPermissionListener;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.ui.wall.ImageExpandDialog;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.DaggerWallCommentComponent;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.di.WallCommentModule;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentContract;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.mvp.WallCommentPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.WallFragmentList;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.post.WallCreatePostActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.CompositeMultiplePermissionsListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.multi.SnackbarOnAnyDeniedMultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

/**
 * Created by Leonardojpr on 1/10/18.
 */

public class WallCommentFragment extends BaseFragment implements WallCommentContract.View, WallCommentAdapter.WallCommentClickListener {

    public static final String TAG = WallCommentFragment.class.getSimpleName();
    public static final String WALL_ITEM = "wall_item";
    public static final String WALL_NOTIFICATION = "notification";
    public static final String COUNT_CLAP = "count_clap";

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_comment)
    RecyclerView recyclerView;
    @BindView(R.id.count_like_comment)
    FCMillonariosTextView countClap;
    @BindView(R.id.btn_back)
    ImageView btnBack;
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
    @BindView(R.id.btn_gallery)
    ImageView btnGalerry;

    private LinearLayoutManager mLayoutManager;

    private WallCommentAdapter wallCommentAdapter;

    private WallItem wallItem;

    InputMethodManager imm;

    @Inject
    WallCommentPresenter presenter;

    private Uri imageUri;

    EmojIconActions emojIcon;

    boolean camPermission = false;
    boolean storePermission = false;

    public static WallCommentFragment newInstance(WallItem wallItem, boolean notification) {
        Bundle args = new Bundle();
        args.putSerializable(WALL_ITEM, wallItem);
        args.putBoolean(WALL_NOTIFICATION, notification);
        WallCommentFragment wallCommentFragment = new WallCommentFragment();
        wallCommentFragment.setArguments(args);

        return wallCommentFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initComponent();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wall_comment, container, false);
        ButterKnife.bind(this, view);

        wallItem = (WallItem) getArguments().getSerializable(WALL_ITEM);

        initRecyclerView();
        setPermissions();

        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        presenter.load(wallItem.getIdpost(), false);

        emojIcon = new EmojIconActions(getContext(), view, message, btnEmoji);
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
        return view;
    }


    @OnClick(R.id.btn_back)
    public void finisComment() {
        if (!getArguments().getBoolean(WALL_NOTIFICATION, false)) {
            getFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right)
                    .remove(getFragmentManager().findFragmentByTag(WallCommentFragment.TAG))
                    .show(getFragmentManager().findFragmentByTag(WallFragmentList.TAG))
                    .commit();
        } else {
            getActivity().finish();
        }
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
        if (message.getText().toString().trim().equals("")) {
            presenter.createComment(wallItem.getIdpost(), message.getText().toString().trim(), imagePost);
        } else {
            presenter.createComment(wallItem.getIdpost(), message.getText().toString(), imagePost);
        }

    }

    @OnClick(R.id.btn_camera)
    public void onSelectImageClick() {
        if (camPermission && storePermission) {
            CropImage.startPickImageActivity(getActivity());
        }
    }

    @OnClick(R.id.btn_gallery)
    public void onGallerySelect() {
        if (camPermission && storePermission) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);//
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE);
        } else {
            setPermissions();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                .build().inject(WallCommentFragment.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        List<Object> itemList = new ArrayList<>();
        itemList.add(wallItem);
        wallCommentAdapter = new WallCommentAdapter(getContext(), itemList, String.valueOf(wallItem.getIdpost()));
        wallCommentAdapter.setWallCommentClickListener(this);
        recyclerView.setAdapter(wallCommentAdapter);
        recyclerView.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecyclerView();
                presenter.load(wallItem.getIdpost(), true);
            }
        });
        wallCommentAdapter.setTextEmptyListColor(Commons.getColor(R.color.textColorPrimary));
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (!swipeRefreshLayout.isRefreshing()) {
                    presenter.loadPage(wallItem.getIdpost(), current_page);
                    wallCommentAdapter.showLoading();
                }
            }
        };
    }

    @Override
    public void onShowProfileListener(String userItem) {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                .add(R.id.cal_container, WallFragmentList.newInstance("profile", userItem), WallCommentFragment.TAG)
                .commitAllowingStateLoss();
    }

    @Override
    public void onClickComment() {
        finisComment();
    }

    @Override
    public void onClapCommentListener(String idcomentario, WallCommentAdapter.CommentLikeListener commentLikeListener) {
        presenter.clapComment(idcomentario, commentLikeListener);
    }

    @Override
    public void onClickLikeListener(String id_post, WallAdapter.CommentListener commentListener) {
        presenter.clapPost(id_post, commentListener);
    }

    @Override
    public void onExpandImagen(Drawable drawable) {
        ImageExpandDialog imageExpandDialog = new ImageExpandDialog();
        imageExpandDialog.setImageToExpand(drawable);
        showDialogFragment(imageExpandDialog);
    }

    @Override
    public void onDeletePost() {
        showDialogDeletedPost(wallItem.getIdpost());
    }

    @Override
    public void onDeleteComment(String idpost, WallCommentItem wallCommentItem, WallCommentAdapter.CommentDeleteListener commentDeleteListener) {
        showDialogDeletedComment(idpost, wallCommentItem, commentDeleteListener);
    }

    @Override
    public void onEditComment(String idpost, WallCommentItem item, WallCommentAdapter.CommentEditListener commentEditListener) {
        Intent intent = new Intent(getContext(), WallCommentEditActivity.class);
        intent.putExtra("idpost", idpost);
        intent.putExtra("wallCommentItem", item);
        startActivity(intent);
    }

    @Override
    public void onClickReportarPost(String id) {
        showDialogReportarPost(wallItem.getIdpost(), null);
    }

    @Override
    public void onEditPostListener(WallItem wallItem) {
        Intent intent = new Intent(getActivity(), WallCreatePostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("wallitem", wallItem);
        getActivity().startActivityForResult(intent, 2000);
    }

    @Override
    public void onClickReportarComment(String id) {
        showDialogReportarPost(null, id);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }


    @Override
    public void setLoad(List<WallCommentItem> list) {
        swipeRefreshLayout.setRefreshing(false);
        wallCommentAdapter.addAll(list);
    }

    @Override
    public void deletePost(String msg) {
        EventBus.getDefault().post(WallEvent.newInstance(WallEvent.WALL_DELETE_POST, wallItem.getIdpost()));
        finisComment();
    }

    @Override
    public void deleteComment(String msg) {
        HashMap<String, Object> sadad = new HashMap<String, Object>();
        sadad.put(WallEvent.WALL_DELETE_COMMENT_POST, wallCommentAdapter.getCurrentItemCount() - 1);
        EventBus.getDefault().post(new WallEvent(sadad));
        EventBus.getDefault().post(WallEvent.newInstance(WallEvent.WALL_DELETE_COMMENT_POST, wallItem.getIdpost()));
    }

    @Override
    public void setLike() {
        EventBus.getDefault().post(WallEvent.newInstance(WallEvent.WALL_LIKE_POST, ((WallItem) wallCommentAdapter.getCommentList().get(0)).getYaaplaudio()));
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
                presenter.load(wallItem.getIdpost(), true);
                HashMap<String, Object> sadad = new HashMap<String, Object>();
                sadad.put(WallEvent.WALL_CREATE_COMMENT_POST, wallCommentAdapter.getCurrentItemCount() - 1);
                EventBus.getDefault().post(new WallEvent(sadad));
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
        wallCommentAdapter.showNoMoreDataToDisplay();
        disableView(true);
    }

    @Override
    public void setRefreshing(boolean state) {
        if (state)
            recyclerView.smoothScrollToPosition(wallCommentAdapter.getCurrentItemCount());
    }

    @Override
    public void showToastError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
        disableView(true);
    }

    @Override
    public void editComment() {

    }

    @Override
    public void reportarPost() {
        showDialogSuccessPost();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    private void setPermissions() {
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new CreateCommentPermissionListener(this);
        MultiplePermissionsListener allPermissionsListener =
                new CompositeMultiplePermissionsListener(feedbackViewMultiplePermissionListener,
                        SnackbarOnAnyDeniedMultiplePermissionsListener.Builder
                                .with(getView(), "Permisos denegados")
                                .build());
        Dexter.withActivity(getActivity())
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
        if (permission.equals(Manifest.permission.CAMERA)) {
            camPermission = true;
        }
        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            storePermission = true;
        }
    }

    public void showPermissionDenied(String permission, boolean isPermanentlyDenied) {

    }

    private void showDialogDeletedPost(final String id_post) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ideal_eleven_share, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = (FCMillonariosTextView) dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText("¿Deseas eliminar esta publicación?");
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deletePost(id_post);
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogDeletedComment(final String idpost, final WallCommentItem wallCommentItem, final WallCommentAdapter.CommentDeleteListener commentDeleteListener) {
        WallCommentAdapter.canDeleted = false;
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_ideal_eleven_share, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = (FCMillonariosTextView) dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText("¿Deseas eliminar este comentario?");
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteComment(idpost, wallCommentItem, commentDeleteListener);
                alertDialog.dismiss();
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                WallCommentAdapter.canDeleted = true;
            }
        });
    }

    private void showDialogReportarPost(final String id_post, final String id_comment) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_reportar_comentario, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        RadioGroup radioGroup = dialoglayout.findViewById(R.id.radio_group);
        final RadioButton radioButtonOne = dialoglayout.findViewById(R.id.opcion_one);
        final RadioButton radioButtonDos = dialoglayout.findViewById(R.id.opcion_dos);
        final RadioButton radioButtonTres = dialoglayout.findViewById(R.id.opcion_tres);
        final RadioButton radioButtonCuatro = dialoglayout.findViewById(R.id.opcion_cuatro);


        // fcMillonariosTextView.setText("¿Deseas eliminar esta publicación?");
        Button btnNot = (Button) dialoglayout.findViewById(R.id.btn_not);
        btnNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        final String probando = "";
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_yes);
        final int index = radioGroup.indexOfChild(dialoglayout.findViewById(radioGroup.getCheckedRadioButtonId()));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "";
                if (radioButtonOne.isChecked()) {
                    text = radioButtonOne.getText().toString();
                } else if (radioButtonDos.isChecked()) {
                    text = radioButtonDos.getText().toString();
                } else if (radioButtonTres.isChecked()) {
                    text = radioButtonTres.getText().toString();
                } else if (radioButtonCuatro.isChecked()) {
                    text = radioButtonCuatro.getText().toString();
                }

                if (!text.equals("")) {
                    presenter.sendReportarPost(new WallReportarPost(SessionManager.getInstance().getSession().getToken(), text, id_post, id_comment));
                    alertDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Seleccione una opcioón", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogSuccessPost() {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_thanks_report, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        FCMillonariosTextView fcMillonariosTextView = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        fcMillonariosTextView.setText("Gracias \n por reportar esta publicación. \n\n Tus comentarios son de vital importancia para hacer de la App Oficial un lugar seguro.");
        Button btnYes = (Button) dialoglayout.findViewById(R.id.btn_ok);
        btnYes.setText("VOLVER");
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });
    }

    @Subscribe
    public void onMessageEvent(WallEvent event) {
        for (Map.Entry<String, Object> map : event.getAction().entrySet()) {
            if (map.getKey().equals(WallEvent.WALL_EDIT_COMMENT_POST)) {
                initRecyclerView();
                presenter.load(wallItem.getIdpost(), false);
            }

            if (map.getKey().equals(WallEvent.WALL_EDIT_POST)) {
                wallCommentAdapter.updatePost((WallItem) map.getValue());
            }
        }
    }

    @Subscribe
    public void onMessageEvent(WallCreatePostEvent event) {
        wallCommentAdapter.updatePost(event.getWallItem());

    }

}