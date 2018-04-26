package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.VideoActivity;
import com.BarcelonaSC.BarcelonaApp.models.ChatReportData;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Conversacion;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.ChatAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.di.ChatModule;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.di.DaggerChatComponent;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp.ChatContract;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.mvp.ChatPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.BarcelonaSC.BarcelonaApp.ui.register.fragments.RegisterFragment;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.EndlessScrollListener;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.giphy.GiphyFragment;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.iceteck.silicompressorr.SiliCompressor;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.net.URISyntaxException;
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

public class ChatFragment extends Fragment implements ChatContract.View, ChatAdapter.OnItemClickListener, GiphyAdapter.OnGifClickListener {

    public static final String TAG = ChatActivity.class.getSimpleName();
    private static int SELECT_IMAGE = 1001;
    private static int SELECT_VIDEO = 1000;
    private static int CAMERA_REQUEST = 1002;
    private static final int REQUEST_VIDEO_CAPTURE = 1003;
    public static final String TAG_GROUP = "group_chat";
    public static final String TAG_PRIVATE = "private_chat";
    ProgressDialog dialogProgress;

    //--------------------------------------//

    @BindView(R.id.rv_chat_message_list)
    RecyclerView listMessagesView;


    @BindView(R.id.iv_chat_emoji)
    ImageView btnAddEmoji;

    @BindView(R.id.et_chat_message)
    EmojiconEditText chatMessage;

    @BindView(R.id.iv_chat_send_msg)
    ImageView btnSendMessage;

    @BindView(R.id.chat_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    Unbinder unbinder;
    @BindView(R.id.fl_container_gif)
    FrameLayout flContainerGif;
    @BindView(R.id.iv_chat_attachment)
    ImageView ivChatAttachment;
    @BindView(R.id.emoticon)
    ImageView emoticon;

    private View rootView;
    boolean camPermission = false;
    boolean storePermission = false;
    private ChatAdapter chatAdapter;
    private LinearLayoutManager mLayoutManager;

    private Grupo grupo = null;
    private String idGrupo = null;
    private Conversacion conversacion = null;
    private Amigos amigo = null;
    private String chat_type = "";
    private Uri imageUri = null;
    private File video;

    private EmojIconActions emojIcon;

    @Inject
    public ChatPresenter presenter;

    GiphyFragment giphyFragment;

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

    @Override
    public void deleteMember() {
        if (getActivity() != null)
            getActivity().finish();
    }

    public boolean isAdminGroup(long idAdmin) {

        Log.i(TAG, "idAdmin" + idAdmin + " session" + SessionManager.getInstance().getUser().getId_usuario());
        return (idAdmin == Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()));
    }

    public void onClickMemberToDelete() {
        sendInfo();
        presenter.onClickMemberToDelete(Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()), grupo, isAdminGroup(grupo.getAdmin()));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_view, container, false);

        rootView = view.findViewById(R.id.root_view);
        unbinder = ButterKnife.bind(this, view);

        swipeRefreshLayout.setEnabled(false);
        initRecyclerView();


        if (conversacion != null)
            presenter.loadMessagesPrivate(conversacion);
        else {
            presenter.getAllMessage();
        }

        chatMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                boolean handled = false;
                if (chatMessage.getText().toString().length() > 0) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        sendMessage();
                        handled = true;
                    }
                }
                return handled;
            }
        });
        if (grupo == null)
            presenter.setBlock(amigo.isBloqueado());

        emojIcon = new EmojIconActions(getContext(), view, chatMessage, btnAddEmoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_keyboard, R.drawable.smiley);
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

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        if (getArguments() != null) {
            idGrupo = null;
            if (getArguments().getParcelable(TAG_GROUP) != null) {
                grupo = getArguments().getParcelable(TAG_GROUP);
                if (grupo != null) {
                    idGrupo = grupo.getKey();
                    conversacion = grupo.getConversacion();
                }
            } else {

                amigo = getArguments().getParcelable(TAG_PRIVATE);
                if (amigo != null)
                    conversacion = amigo.getConversacion();
            }

        }


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
                imageUri = null;
                showDialogProgress("Cargando Imagen");
                presenter.toSendMessage(conversacion.getId()
                        , FirebaseManager.getInstance().getUsuario().getId()
                        , conversacion.getMiembros()
                        , chatMessage.getText().toString()
                        , resultUri
                        , idGrupo);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Log.e(RegisterFragment.class.getSimpleName(), error.toString());
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


    private void setVideoSelect(Uri selectedVideo) {
        String[] projection = {MediaStore.Video.Media.DATA};
        new VideoCompressAsyncTask(App.get(), selectedVideo, App.get().getFilesDir().getAbsolutePath()).execute();
    }

    @OnClick(R.id.emoticon)
    public void onViewClicked() {
    }

    private void showDialogProgress(String text) {
        dialogProgress = ProgressDialog.show(getActivity(), "", text, true);
        dialogProgress.setMax(100);
        dialogProgress.show();
    }

    private void hideDialogProgress() {
        if (dialogProgress != null)
            dialogProgress.dismiss();
    }

    public static String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = App.get().getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    class VideoCompressAsyncTask extends AsyncTask<String, String, String> {

        Context mContext;
        Uri uri;
        String path;


        public VideoCompressAsyncTask(Context context, Uri uri, String path) {
            mContext = context;
            this.uri = uri;
            this.path = path;
            showDialogProgress("Vídeo cargando, por favor espera...");

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... paths) {
            String filePath = null;
            try {
                filePath = SiliCompressor.with(App.get()).compressVideo(uri, path, 1280, 720, 0);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return filePath;

        }


        @Override
        protected void onPostExecute(String compressedFilePath) {
            super.onPostExecute(compressedFilePath);
            try {
                final File imageFile = new File(compressedFilePath);

                try {
                    Glide.with(App.get()).load(imageFile).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            ChatFragment.this.multimediaFailed();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            sendVideo(imageFile, ((BitmapDrawable) resource).getBitmap());
                            return false;
                        }
                    }).submit();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                Toast.makeText(App.get(), "Hubo un error inesperado, vuelve a intentarlo", Toast.LENGTH_LONG).show();
            }

        }
    }


    public void sendVideo(final File video, final Bitmap thumbnail) {
        Commons.initUploadWithTransferUtility(video).setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

                if (TransferState.COMPLETED == state) {

                    presenter.toSendVideo(conversacion.getId()
                            , FirebaseManager.getInstance().getUsuario().getId()
                            , conversacion.getMiembros()
                            , chatMessage.getText().toString()
                            , "https://s3.amazonaws.com/millos-videos/" + video.getName()
                            , idGrupo
                            , thumbnail);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                //      showProgress();
            }

            @Override
            public void onError(int id, Exception ex) {
                //      hideProgress();
            }
        });
    }


    @SuppressLint("RestrictedApi")
    public void onPopUpEmojisButtonClick(View button) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_emojis, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_emojis: {
                        emoticon.callOnClick();

                        break;
                    }
                    case R.id.menu_gif: {
                        flContainerGif.setVisibility(View.VISIBLE);

                        if (giphyFragment == null) {
                            giphyFragment = new GiphyFragment();
                            giphyFragment.setGifListener(ChatFragment.this);
                            getChildFragmentManager().beginTransaction().add(R.id.fl_container_gif
                                    , giphyFragment, GiphyFragment.TAG).commit();
                            giphyFragment.setGifListener(ChatFragment.this);
                        }
                        break;
                    }

                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }

    @OnClick(R.id.btn_gif)
    public void btnGif() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        flContainerGif.setVisibility(View.VISIBLE);

        if (giphyFragment == null) {
            giphyFragment = new GiphyFragment();
            giphyFragment.setGifListener(ChatFragment.this);
            getChildFragmentManager().beginTransaction().add(R.id.fl_container_gif
                    , giphyFragment, GiphyFragment.TAG).commit();
            giphyFragment.setGifListener(ChatFragment.this);
        }

    }

    public void showDialog() {
        if (getActivity() == null)
            return;
        final Dialog dialog = new Dialog(getActivity());
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

    public void showCameraDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        final View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_camera, null);
        AppCompatImageView foto = v.findViewById(R.id.btn_foto);
        foto.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera));
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
                chat_type = "video";
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

    public void openVideoCamera() {
        long fileSize = 7 * 1024 * 1024;
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        // intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, fileSize);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }

    }

    // @OnClick(R.id.btn_gallery)
    public void openGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);

    }

    public void openVideoGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        intent.setType("video/*");
        startActivityForResult(intent, SELECT_VIDEO);

    }

    public void openCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        imageUri = Uri.fromFile(Commons.getOutputMediaFile());
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @SuppressLint("RestrictedApi")
    public void onPopUpAdjuntButtonClick(View button) {
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_adjunt, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.menu_galerry: {
                        showDialog();
                    /*    Intent intent = new Intent();
                        intent.setType("image*//**//*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);//
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);*/
                        break;
                    }
                    case R.id.menu_camera: {

                        showCameraDialog();
                      /*  Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        imageUri = Uri.fromFile(Commons.getOutputMediaFile());
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);*/
                        break;
                    }


                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
    }


    public void sendGif(String url) {
        presenter.toSendGif(conversacion.getId()
                , FirebaseManager.getInstance().getUsuario().getId()
                , conversacion.getMiembros()
                , chatMessage.getText().toString()
                , url
                , idGrupo);
    }


    // @OnClick(R.id.iv_chat_send_msg)
    void sendMessage() {
        presenter.toSendMessage(conversacion.getId()
                , FirebaseManager.getInstance().getUsuario().getId()
                , conversacion.getMiembros()
                , chatMessage.getText().toString()
                , imageUri
                , idGrupo);
        chatMessage.setText("");
        imageUri = null;
    }

    void sendInfo() {
        presenter.toInfoMessage(conversacion.getId()
                , FirebaseManager.getInstance().getUsuario().getId()
                , conversacion.getMiembros()
                , FirebaseManager.getInstance().getUsuario().getApodo() + " ha dejado el grupo"
                , null
                , idGrupo);
        chatMessage.setText("");

    }


    @OnTextChanged(value = R.id.et_chat_message, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    void onTextChanged() {
       /* if (chatMessage.getText().toString().length() > 0) {
            btnTakePhoto.setVisibility(View.GONE);
            btnAddPicFromArchive.setVisibility(View.GONE);
        } else {
            btnTakePhoto.setVisibility(View.VISIBLE);
            btnAddPicFromArchive.setVisibility(View.VISIBLE);
        }*/
    }

    public void clearMessages() {
        presenter.clearMessage();
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

    public void reportUser() {
        ChatReportData chatReportData = new ChatReportData(SessionManager.getInstance().getUser().getId_usuario(),
                amigo.getConversacion().getMiembros().get(0).getId(), "reporte de chat");
        presenter.reportUser(chatReportData);
    }

    @Override
    public void blockUser() {
        Toast.makeText(App.get(), "Usuario bloqueado, desbloquealo para poder conversar", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReportFailed() {
        Toast.makeText(App.get(), "Reporte Fallido", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onReportSuccess() {
        Toast.makeText(App.get(), "Reporte Exitoso", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void multimediaSuccess() {
        hideDialogProgress();
        Toast.makeText(getContext(), "Carga Exitosa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void multimediaFailed() {
        hideDialogProgress();
        Toast.makeText(getContext(), "Algo salió mal, por favor intenta de nuevo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClickItem(MessageModelView friend) {

    }

    @Override
    public void onClickViewImage(String url) {
        if (getActivity() == null)
            return;
        ((ChatActivity) getActivity()).galleryFragment.setTouchImageView(url);
        ((ChatActivity) getActivity()).viewPager.setCurrentItem(1);
    }

    @Override
    public void onClickVideo(String url) {
        Intent intent = new Intent(getActivity(), VideoActivity.class);
        intent.putExtra(Constant.Video.CURRENT_POSITION, 1);
        intent.putExtra(Constant.Video.PLAY, true);
        intent.putExtra(Constant.Video.URL, url);
        startActivity(intent);
    }

    @Override
    public void onClickGif(String link) {
        Log.i("TAG", "/////--->222" + link);
        if (link != null) {
            sendGif(link);
        }
        flContainerGif.setVisibility(View.GONE);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public boolean isGifVisible() {
        return flContainerGif.getVisibility() == View.VISIBLE;
    }

    @OnClick(R.id.iv_chat_attachment)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_chat_attachment:
                onPopUpAdjuntButtonClick(view);
                break;
        }
    }

}
