package com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.adapter.FriendSelectedAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.adapter.FriendSelectionAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di.CreateGroupModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.di.DaggerCreateGroupComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.creategroup.mvp.CreateGroupPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class CreateGroupActivity extends BaseSideMenuActivity implements CreateGroupContract.View, FriendSelectionAdapter.OnItemClickListener, FriendSelectedAdapter.OnItemClickListener {

    public final static String TAG = CreateGroupActivity.class.getSimpleName();

    private static final String CATEGORY = "category";

    @BindView(R.id.friend_selection_recycler_view)
    RecyclerView listaAmigos;

    @BindView(R.id.friend_selected_recycler_view)
    RecyclerView listaSeleccionados;

    @BindView(R.id.friend_selection_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.next_button)
    LinearLayout btnGoToCreationGoup;

    @BindView(R.id.iv_back)
    ImageView btnBack;

    @BindView(R.id.ly_group_photo)
    LinearLayout ly_group_photo;

    @BindView(R.id.ly_group_name)
    LinearLayout ly_group_name;

    @BindView(R.id.iv_group_photo)
    CircleImageView iv_group_photo;

    @BindView(R.id.et_group_name)
    EditText et_group_name;

    @BindView(R.id.tv_photo_group_name)
    TextView tv_photo_group_name;

    @BindView(R.id.progressbar)
    ProgressBar progressbar;

    @BindView(R.id.tv_group_name)
    TextView tv_group_name;
    @BindView(R.id.et_msg_search)
    EditText searchKey;
    @BindView(R.id.iv_more_conv)
    ImageView ivMoreConv;

    private FriendSelectionAdapter friendSelectionAdapter;
    private FriendSelectedAdapter friendSelectedAdapter;

    private LinearLayoutManager mLayoutManager;
    private GridLayoutManager mGLayoutManager;

    Unbinder unbinder;

    private boolean isNeedPhoto = true;
    public Uri mCropImageUri;
    private final int MAX_SIZE_NAME = 15;

    @Inject
    public CreateGroupPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_selection);
        unbinder = ButterKnife.bind(this);
        ivMoreConv.setVisibility(View.INVISIBLE);
        super.setSubTitle(ConfigurationManager.getInstance().getConfiguration().getTit163());
        super.initMenu();
        initComponent();
        mGLayoutManager = new GridLayoutManager(this, 4);
        listaSeleccionados.setLayoutManager(mGLayoutManager);
        List<FriendsModelView> selectedList = new ArrayList<>();
        friendSelectedAdapter = new FriendSelectedAdapter(selectedList, this);
        friendSelectedAdapter.setOnItemClickListener(this);
        listaSeleccionados.setAdapter(friendSelectedAdapter);
        initRecyclerView();
        et_group_name.requestFocus();
        presenter.onAttach(this);
        presenter.loadFriends(0);
    }


    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        listaAmigos.setLayoutManager(mLayoutManager);
        List<FriendsModelView> itemList = new ArrayList<>();
        friendSelectionAdapter = new FriendSelectionAdapter(itemList, this);
        friendSelectionAdapter.setOnItemClickListener(this);
        listaAmigos.setAdapter(friendSelectionAdapter);
        //    listaAmigos.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchKey.setText("");
                initRecyclerView();
                refresh();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private EndlessScrollListener initRecyclerViewScroll() {
        return new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                if (searchKey.getText().toString().equals("")) {
                    presenter.loadFriends(current_page);
                }
            }
        };
    }

    @Override
    public void showNoResultText(boolean show) {
        hideProgress();
        if (show)
            Toast.makeText(this, getText(R.string.group_search), Toast.LENGTH_SHORT).show();
    }


    @OnClick({R.id.iv_group_photo, R.id.tv_photo_group_name})
    void addPhoto() {
        cropImage();
    }


    @SuppressLint("NewApi")
    public void cropImage() {
        if (CropImage.isExplicitCameraPermissionRequired(CreateGroupActivity.this)) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(this);
        }
    }

    private boolean moveScreenBack() {

        if (ly_group_photo.getVisibility() == View.VISIBLE) {
            ly_group_photo.setVisibility(View.GONE);
            ly_group_name.setVisibility(View.VISIBLE);
            listaAmigos.setVisibility(View.VISIBLE);
            tv_group_name.setText("");
            return true;
        } else {
            return false;
        }
    }


    @OnClick(R.id.iv_next)
    void goCreationGroup() {
        Crashlytics.log(Log.DEBUG, "AMIGO", "---> CREACION");
        if (ly_group_name.getVisibility() == View.VISIBLE) {
            if (et_group_name.getText().toString().length() > 0 && et_group_name.getText().toString().length() <= MAX_SIZE_NAME && friendSelectedAdapter != null && friendSelectedAdapter.getItemCount() > 0) {
                ly_group_photo.setVisibility(View.VISIBLE);
                ly_group_name.setVisibility(View.GONE);
                listaAmigos.setVisibility(View.GONE);
                tv_group_name.setText(et_group_name.getText().toString());
            } else {
                if (et_group_name.getText().toString().length() <= 0)
                    Toast.makeText(getBaseContext(), getText(R.string.group_missing_name), Toast.LENGTH_SHORT).show();
                else if (friendSelectedAdapter != null && friendSelectedAdapter.getItemCount() <= 0)
                    Toast.makeText(getBaseContext(), getText(R.string.group_missing_participant), Toast.LENGTH_SHORT).show();
                else if (et_group_name.getText().toString().length() > MAX_SIZE_NAME)
                    Toast.makeText(getBaseContext(), getText(R.string.group_name_wrong_length), Toast.LENGTH_SHORT).show();
            }
        } else if (ly_group_photo.getVisibility() == View.VISIBLE) {
            if (mCropImageUri != null) {
                InputStream imageStream = null;
                presenter.initGroupMembers(presenter.getAllNewGroupMembers());
                presenter.createGroupChat(et_group_name.getText().toString(), mCropImageUri);
            } else {
                Toast.makeText(getBaseContext(), getText(R.string.group_missing_image), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Crashlytics.log(Log.DEBUG, TAG, "77777---> onActivityResult()");
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
            if (resultCode == Activity.RESULT_OK) {
                Uri resultUri = result.getUri();
                mCropImageUri = resultUri;

//                btnGroupPhotoBTN.setVisibility(View.GONE);
//                iv_group_photo.setVisibility(View.VISIBLE);
                Glide.with(this)
                        .load(result.getUri())
                        .into(iv_group_photo);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
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

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        if (moveScreenBack()) {

        } else {
            this.finish();
        }

    }

    @Override
    public void onBackPressed() {
        if (moveScreenBack()) {

        } else {
            super.onBackPressed();
        }
    }

    void onFriendSearch() {
        //     presenter.findByName(searchKey.getText().toString());
    }

    @OnTextChanged(value = R.id.et_msg_search, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChange() {
        showProgress();
        presenter.findByName(searchKey.getText().toString());

    }


    public void initComponent() {
        DaggerCreateGroupComponent.builder()
                .appComponent(App.get().component())
                .createGroupModule(new CreateGroupModule(this))
                .build().inject(CreateGroupActivity.this);
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
    public void updateFriends(List<FriendsModelView> friends) {
        hideProgress();
        if (friendSelectionAdapter != null) {
            if (friends.size() == 0) {
                Toast.makeText(getBaseContext(), getText(R.string.group_search), Toast.LENGTH_SHORT).show();
            }
            friendSelectionAdapter.updateAll(friends);
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateSelectedFriends(List<FriendsModelView> friends) {
        if (friendSelectedAdapter != null) {
            if (friends.size() > 0) {
                moveScreenBack();
                Crashlytics.log(Log.DEBUG, "AMIGO", " ---> SIZE " + friends.size());
                btnGoToCreationGoup.setVisibility(View.VISIBLE);
            } else {
                moveScreenBack();
                btnGoToCreationGoup.setVisibility(View.GONE);
            }
            friendSelectedAdapter.updateAll(friends);
        }
    }

    @Override
    public void refresh() {
        presenter.loadFriends(0);
    }

    @Override
    public void showMessage(String message) {

    }


    @Override
    public void lunchChatActivity(Grupo newGroup) {
        if (newGroup == null) {
            finish();
            Toast.makeText(getBaseContext(), getText(R.string.group_created), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), getText(R.string.group_created), Toast.LENGTH_SHORT).show();
            Intent intent = ChatActivity.intent(newGroup.getKey(), getBaseContext());
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onClickFriend(FriendsModelView friend) {
        Crashlytics.log(Log.DEBUG, "AMIGO", " presione ---> to select " + friend.getId());
        presenter.onClickFriend(friend);
    }

    @Override
    public void onClickSelectedFriend(FriendsModelView friend) {
        Crashlytics.log(Log.DEBUG, "AMIGO", " ---> selected " + friend.getId());
        presenter.onClickSelectedFriend(friend);
    }

    @Override
    public void onClickMenuItem(String fragment) {
        Intent resultIntent = new Intent(this, HomeActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        resultIntent.putExtra(Constant.Key.SECCION_SELECTED, fragment);
        startActivity(resultIntent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDetach();
    }

    @OnClick(R.id.et_msg_search)
    public void onViewClicked() {
    }
}
