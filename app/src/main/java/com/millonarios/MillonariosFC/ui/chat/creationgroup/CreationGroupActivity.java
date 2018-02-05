package com.millonarios.MillonariosFC.ui.chat.creationgroup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.ui.chat.chatview.ChatActivity;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.ui.chat.groups.GroupModelView;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.adapter.CreationGroupAdapter;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.di.CreationGroupModule;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.di.DaggerCreationGroupComponent;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp.CreationGroupContract;
import com.millonarios.MillonariosFC.ui.chat.creationgroup.mvp.CreationGroupPresenter;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class CreationGroupActivity extends AppCompatActivity implements CreationGroupContract.View, CreationGroupAdapter.OnItemClickListener {

    public static final String TAG = CreationGroupActivity.class.getSimpleName();
    public static final String TAG_MEMBERS = "newMembers";

    @BindView(R.id.friend_selected_recycler_view)
    RecyclerView listaSeleccionados;

    @BindView(R.id.iv_group_photo)
    ImageView btnGroupPhoto;

    @BindView(R.id.iv_emoji)
    ImageView btnAddEmoji;

    @BindView(R.id.iv_creation)
    ImageButton btnCreateGroup;

    @BindView(R.id.et_group_name)
    EmojiconEditText groupName;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    Unbinder unbinder;
    protected ArrayList<Amigos> newMembers = new ArrayList<Amigos>();

    private CreationGroupAdapter creationGroupAdapter;
    private GridLayoutManager mGLayoutManager;

    public Uri mCropImageUri;

    @Inject
    public CreationGroupPresenter presenter;

    private EmojIconActions emojIcon;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_group);
        rootView = findViewById(R.id.root_view_creation_group);
        unbinder = ButterKnife.bind(this);
        initComponent();
        try {
            newMembers = getIntent().getParcelableArrayListExtra(TAG_MEMBERS);
            Crashlytics.log(Log.DEBUG, "AMIGO", "--->" + newMembers.size());
        } catch (Exception e) {
            Crashlytics.log(Log.DEBUG, "AMIGO", "---> No hay miembros e: " + e.getMessage());
        }
        mGLayoutManager = new GridLayoutManager(this, 4);
        listaSeleccionados.setLayoutManager(mGLayoutManager);
        creationGroupAdapter = new CreationGroupAdapter(newMembers, this);
        creationGroupAdapter.setOnItemClickListener(this);
        listaSeleccionados.setAdapter(creationGroupAdapter);
        presenter.initGroupMembers(newMembers);
        emojIcon = new EmojIconActions(this, rootView, groupName, btnAddEmoji);
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
    }

    public void initComponent() {
        DaggerCreationGroupComponent.builder()
                .appComponent(App.get().component())
                .creationGroupModule(new CreationGroupModule(this))
                .build().inject(CreationGroupActivity.this);
    }

    @OnClick(R.id.iv_creation)
    void createGroup() {
        //TODO: insertar validacion
        if (mCropImageUri != null && groupName.getText().toString().length() > 0) {
            InputStream imageStream = null;
          /*  try {
                imageStream = getContentResolver().openInputStream(mCropImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/
            //    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            //   String encodedImage = encodeImage(selectedImage);
            Crashlytics.log(Log.DEBUG, "AMIGO", " ---> Creando grupo");
            showProgress();
            presenter.createGroupChat(groupName.getText().toString(), mCropImageUri);
        } else {
            Toast.makeText(getBaseContext(), getText(R.string.group_missing_params), Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.iv_emoji)
    void addEmoji() {
        Toast.makeText(getBaseContext(), "Esto es el Emoji Button", Toast.LENGTH_SHORT).show();
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> Esto es el Emoji Button");
    }

    @OnClick(R.id.iv_group_photo)
    void addPhoto() {
        if (CropImage.isExplicitCameraPermissionRequired(this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
            }
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri;
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageUri = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
                }
            } else {
                startCropImageActivity(imageUri);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == this.RESULT_OK) {
                Uri resultUri = result.getUri();
                //Glide.with(this).load(result.getUri()).into(imgProfile);
                mCropImageUri = resultUri;
                Log.d("probando", resultUri.toString());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(CreationGroupActivity.class.getSimpleName(), error.toString());
            }
        }
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setFixAspectRatio(true)
                .start(this);
    }

    private String encodeImage(Bitmap bm) {
        Bitmap resize = scaleDown(bm, 748, true);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resize.compress(Bitmap.CompressFormat.PNG, 25, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min((float) maxImageSize / realImage.getWidth()
                , (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());
        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width, height, filter);
        return newBitmap;
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        this.finish();
    }


    @Override
    public void lunchChatActivity(Grupo newGroup) {
        hideProgress();
        if (newGroup == null) {
            Toast.makeText(getBaseContext(), getText(R.string.unexpected_error), Toast.LENGTH_SHORT).show();
            Log.i("GRUPO", " ---> ESTA NULO");
        } else {
            Toast.makeText(getBaseContext(), getText(R.string.group_create), Toast.LENGTH_SHORT).show();
            Intent intent=ChatActivity.intent(newGroup, getBaseContext());
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void updateSelectedMembers(List<Amigos> friends) {
        if (creationGroupAdapter != null) {
            if (friends.size() == 0) {
                this.finish();
            }
            creationGroupAdapter.updateAll(friends);

        }
    }

    @Override
    public void onClickItem(Amigos friend) {
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> selected " + friend.getId());
        presenter.onFutureMemberPress(friend);
    }
}
