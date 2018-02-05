package com.millonarios.MillonariosFC.ui.chat.chatview;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.app.manager.FirebaseManager;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Conversacion;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.ui.chat.chatview.adapter.ChatAdapter;
import com.millonarios.MillonariosFC.ui.chat.chatview.di.ChatModule;
import com.millonarios.MillonariosFC.ui.chat.chatview.di.DaggerChatComponent;
import com.millonarios.MillonariosFC.ui.chat.chatview.mvp.ChatContract;
import com.millonarios.MillonariosFC.ui.chat.chatview.mvp.ChatPresenter;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.GroupDetailActivity;
import com.millonarios.MillonariosFC.ui.chat.messages.MessageModelView;
import com.millonarios.MillonariosFC.ui.register.fragments.RegisterFragment;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.EndlessScrollListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Leonardojpr on 2/5/18.
 */

public class ChatFragment extends Fragment implements ChatContract.View, ChatAdapter.OnItemClickListener {

    public static final String TAG = ChatActivity.class.getSimpleName();
    private static int SELECT_IMAGE = 1001;
    private static int CAMERA_REQUEST = 1002;

    public static final String TAG_GROUP = "group_chat";
    public static final String TAG_PRIVATE = "private_chat";


    //--------------------------------------//

    @BindView(R.id.rv_chat_message_list)
    RecyclerView listMessagesView;

    @BindView(R.id.iv_chat_pic)
    ImageView btnAddPicFromArchive;

    @BindView(R.id.iv_chat_emoji)
    ImageView btnAddEmoji;

    @BindView(R.id.iv_chat_cam)
    ImageView btnTakePhoto;

    @BindView(R.id.et_chat_message)
    EmojiconEditText chatMessage;

    @BindView(R.id.iv_chat_send_msg)
    ImageView btnSendMessage;

    @BindView(R.id.chat_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;

    private View rootView;

    private ChatAdapter chatAdapter;
    private LinearLayoutManager mLayoutManager;

    private Grupo grupo = null;
    private Conversacion conversacion = null;
    private Amigos amigo = null;

    private Uri imageUri = null;

    @Inject
    public ChatPresenter presenter;

    EmojIconActions emojIcon;

    public static ChatFragment getInstance(Amigos amigos) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG_PRIVATE, amigos);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }

    public static ChatFragment getInstance(Grupo grupo) {
        ChatFragment chatFragment = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TAG_GROUP, grupo);
        chatFragment.setArguments(bundle);
        return chatFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_view, container, false);

        rootView = view.findViewById(R.id.root_view);
        unbinder = ButterKnife.bind(this, view);

        try {
            grupo = getArguments().getParcelable(TAG_GROUP);
            conversacion = grupo.getConversacion();
        } catch (Exception e) {
            Log.i(TAG, "--->" + e);
        }
        try {
            amigo = getArguments().getParcelable(TAG_PRIVATE);
            conversacion = amigo.getConversacion();
        } catch (Exception e) {
            Log.i(TAG, "--->" + e);
        }
        swipeRefreshLayout.setEnabled(false);
        initRecyclerView();

        emojIcon = new EmojIconActions(getContext(), rootView, chatMessage, btnAddEmoji);
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

        if (conversacion != null)
            presenter.loadMessagesPrivate(conversacion);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();

    }

    public void initComponent() {
        DaggerChatComponent.builder()
                .appComponent(App.get().component())
                .chatModule(new ChatModule(this))
                .build().inject(ChatFragment.this);
    }

    public void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setStackFromEnd(true);
        listMessagesView.setLayoutManager(mLayoutManager);
        chatAdapter = new ChatAdapter(grupo != null, new ArrayList<MessageModelView>(), getContext());
        chatAdapter.setOnItemClickListener(this);
        listMessagesView.setAdapter(chatAdapter);
        listMessagesView.addOnScrollListener(initRecyclerViewScroll());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
                if (!swipeRefreshLayout.isRefreshing()) {
                    //refresh(current_page);
                    //progressBar.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void setImageSelect(Uri uri) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        chatMessage.setLayoutParams(params);
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

        if (requestCode == SELECT_IMAGE && resultCode == RESULT_OK) {
            startCropImageActivity(data.getData());
        }

        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            startCropImageActivity(imageUri);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //setImageSelect(resultUri);
                Log.i("CHATACTIVITY", " ---> seteada la URL de la foto");

                presenter.toSendMessage(conversacion.getId()
                        , FirebaseManager.getInstance().getUsuario().getId()
                        , conversacion.getMiembros()
                        , chatMessage.getText().toString()
                        , resultUri
                        , grupo != null);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
            }
        }
    }

    @OnClick(R.id.iv_chat_pic)
    void sendPhotoFromArchive() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    @OnClick(R.id.iv_chat_emoji)
    void sendEmoji() {

    }

    @OnClick(R.id.iv_chat_cam)
    void sendVideoFromCam() {

    }

    @OnClick(R.id.iv_chat_send_msg)
    void sendMessage() {

        if (imageUri != null)
            Log.i("CHATACTIVITY", " ---> sending uri: " + imageUri.toString() + " content: " + chatMessage.getText().toString());
        if (chatMessage.getText().toString() != null)
            Log.i("CHATACTIVITY", " ---> sending content: " + chatMessage.getText().toString());

        presenter.toSendMessage(conversacion.getId()
                , FirebaseManager.getInstance().getUsuario().getId()
                , conversacion.getMiembros()
                , chatMessage.getText().toString()
                , imageUri
                , grupo != null);
        chatMessage.setText("");
        imageUri = null;
    }

    @OnTextChanged(value = R.id.et_chat_message, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
        if (chatMessage.getText().toString().length() > 0) {
            btnTakePhoto.setVisibility(View.GONE);
            btnAddPicFromArchive.setVisibility(View.GONE);
        } else {
            btnTakePhoto.setVisibility(View.VISIBLE);
            btnAddPicFromArchive.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.iv_chat_cam)
    public void openCamera() {

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        imageUri = Uri.fromFile(Commons.getOutputMediaFile());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void updateMesage(List<MessageModelView> messageModelViews) {
        if (chatAdapter != null) {
            initRecyclerView();
            chatAdapter.updateAll(messageModelViews);
            swipeRefreshLayout.setRefreshing(false);
            //progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMissingParams() {
        Toast.makeText(getContext(), getText(R.string.missing_text_params), Toast.LENGTH_SHORT).show();
        imageUri = null;
    }

    @Override
    public void refresh() {
        presenter.loadMessagesPrivate(conversacion);

    }

    @Override
    public void onClickItem(MessageModelView friend) {

    }

    @Override
    public void onClickViewImage(String url) {
        ((ChatActivity) getActivity()).galleryFragment.setTouchImageView(url);
        ((ChatActivity) getActivity()).viewPager.setCurrentItem(1);
    }
}
