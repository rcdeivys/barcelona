package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.ConfigurationManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseControllers.chat.ChatController;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.commons.BaseSideMenuActivity;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.Dialogs.Dialog_add_group;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.ChatAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.BarcelonaSC.BarcelonaApp.ui.gallery.views.GalleryFragment;
import com.BarcelonaSC.BarcelonaApp.ui.home.HomeActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.CustomViewPager;
import com.BarcelonaSC.BarcelonaApp.utils.Notifications;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.BarcelonaSC.BarcelonaApp.utils.SquareHeightImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends BaseSideMenuActivity implements Dialog_add_group.OnItemClickListenerDialog, ChatAdapter.OnItemClickListener {

    public static final String TAG = "chat_activity";
    public static final String TAG_GROUP = "group_chat";
    public static final String TAG_PRIVATE = "private_chat";

    @BindView(R.id.chat_avatar_receiver)
    CircleImageView chatAvatarReceiver;

    @BindView(R.id.chat_name_receiver)
    TextView chatAvatarName;

    int GROUP_PROFILE = 666;

    @BindView(R.id.content_viewpager)
    public CustomViewPager viewPager;

    ChatViewPagerAdapter chatViewPagerAdapter;
    ChatFragment chatFragment;
    ProfileUserFragment profileUserFragment;
    public GalleryFragment galleryFragment;
    @BindView(R.id.circle_state)
    CircleImageView circleState;
    @BindView(R.id.include)
    Toolbar toolbar;
    @BindView(R.id.sqhiv_options)
    SquareHeightImageView sqhivOptions;
    Dialog_add_group.OnItemClickListenerDialog onItemClickListenerDialog;


    private Grupo grupo;
    private Amigos amigos;
    private Uri mCropImageUri;

    public static Intent intent(Long amigos, Context context) {
        Log.i(TAG, "55555--->" + amigos.toString());
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(TAG_PRIVATE, amigos);

        return intent;
    }

    public static Intent intent(String idGrupo, Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(TAG_GROUP, idGrupo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        onItemClickListenerDialog = this;
        super.initMenu();
        super.setSubTitle(ConfigurationManager.getInstance().getConfiguration().getTit163());
        if (getIntent().getLongExtra(TAG_PRIVATE, -1) != -1) {
            String idFriend = String.valueOf(getIntent().getLongExtra(TAG_PRIVATE, -1));
            (new ChatController()).getFriendById(idFriend, new ChatController.ChatFriendListener() {
                @Override
                public void onGetFriendReady(Amigos amigo) {
                    amigos = amigo;
                    if (FirebaseManager.getInstance().getUsuario() != null &&
                            FirebaseManager.getInstance().getUsuario().getAmigos() != null) {
                        for (Amigos friends : FirebaseManager.getInstance().getUsuario().getAmigos()) {
                            if (friends.getId() != null && friends.getId().equals(amigo.getId())) {
                                if (friends.getConversacion().getMiembros().get(0) != null)
                                    friends.getConversacion().getMiembros().get(0)
                                            .copy(amigos.getConversacion().getMiembros().get(0));
                                break;
                            }
                        }
                    }
                    initPrivateChat();
                }

                @Override
                public void onGetFriendError() {
                    if (getActivity() != null)
                        ChatActivity.this.finish();
                }
            });


        }
        if (getIntent().getStringExtra(TAG_GROUP) != null) {
            String idGroup = getIntent().getStringExtra(TAG_GROUP);
            (new ChatController()).getGroupById(idGroup, new ChatController.ChatGroupListener() {
                @Override
                public void onGetGroupReady(Grupo grupoAux) {
                    grupo = grupoAux;
                  /*  if (FirebaseManager.getInstance().getUsuario() != null &&
                            FirebaseManager.getInstance().getUsuario().getGrupos() != null) {
                        for (Grupo group : FirebaseManager.getInstance().getUsuario().getGrupos()) {
                            if (group.getKey() != null && group.getKey().equals(grupo.getKey())) {
                                if (group.getConversacion().getMiembros() != null)
                                    for (Miembro miembro : group.getConversacion().getMiembros()) {

                                    }
                                if (group.getConversacion().getMiembros().get(0) != null)
                                    group.getConversacion().getMiembros().get(0)
                                            .copy(amigos.getConversacion().getMiembros().get(0));

                                break;
                            }
                        }
                    }*/

                    initGroup();
                }

                @Override
                public void onGetGroupError() {
                    if (getActivity() != null)
                        ChatActivity.this.finish();
                }
            });
        }


    }

    public void initPrivateChat() {

        chatFragment = ChatFragment.getInstance(amigos);
        profileUserFragment = ProfileUserFragment.getInstance((amigos).getId());
        Notifications.setCurrentConversationId(amigos.getId_conversacion());

        PhotoUpload.uploadFoto(amigos.getConversacion().getMiembros().get(0).getId()
                , amigos.getConversacion().getMiembros().get(0).getFoto(), new PhotoUpload.PhotoListener() {
                    @Override
                    public void onPhotoSucces(String foto) {
                        amigos.getConversacion().getMiembros().get(0).setFoto(foto);
                        Glide.with(App.get())
                                .load(foto)
                                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                .into(chatAvatarReceiver);
                    }

                    @Override
                    public void onError() {

                    }
                });

        chatAvatarName.setText(amigos.getConversacion().getMiembros().get(0).getApodo());

        initViewPager();
    }


    public void initGroup() {
        chatFragment = ChatFragment.getInstance(grupo);
        profileUserFragment = ProfileUserFragment.getInstance((grupo).getKey());
        Notifications.setCurrentConversationId(grupo.getId_conversacion());
        Glide.with(App.get())
                .load(grupo.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(chatAvatarReceiver);
        chatAvatarName.setText(grupo.getNombre());

        initViewPager();
    }

    private void initViewPager() {

        galleryFragment = GalleryFragment.getInstance(null);
        viewPager.setPagingEnabled(false);
        chatViewPagerAdapter = new ChatViewPagerAdapter(getSupportFragmentManager(), chatFragment, galleryFragment, profileUserFragment);
        viewPager.setAdapter(chatViewPagerAdapter);
    }

    @SuppressLint("RestrictedApi")
    public void onPopupUserButtonClick(View button) {
        if (amigos == null)
            return;

        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_2, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_group: {
                        Log.i(TAG, "TAG_GROUP agregar grupo");
                        Dialog_add_group dialog_add_group = new Dialog_add_group();
                        dialog_add_group.setParams(Dialog_add_group.TAG_GROUP, onItemClickListenerDialog);
                        dialog_add_group.setamigo(amigos);
                        showDialogFragment(dialog_add_group);
                        break;
                    }
                    case R.id.clear_chat: {
                        showUserDialog("clear_chat");

                        break;
                    }
                    case R.id.block_user: {
                        showUserDialog("block_user");
                        break;
                    }
                    case R.id.report_user: {

                        showUserDialog("report_user");
                        break;
                    }
                }
                return true;
            }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(wrapper, (MenuBuilder) popup.getMenu(), button);
        menuHelper.setForceShowIcon(true);
        menuHelper.show();
        if (amigos.isBloqueado())
            popup.getMenu().findItem(R.id.block_user).setTitle("Desbloquear Contacto");
    }

    @SuppressLint("RestrictedApi")
    public void onPopupGroupButtonClick(View button) {
        if (grupo == null)
            return;
        Context wrapper = new ContextThemeWrapper(getActivity(), R.style.PopUpChat);
        PopupMenu popup = new PopupMenu(wrapper, button);
        popup.getMenuInflater().inflate(R.menu.menu_chat_group, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_group: {
                        //AÑADIR PARTICIPANTES A GRUPO
                        Log.i(TAG, "TAG_FRIENDS agregar participante a grupo");
                        Dialog_add_group dialog_add_group = new Dialog_add_group();
                        dialog_add_group.setParams(Dialog_add_group.TAG_FRIENDS, onItemClickListenerDialog);
                        dialog_add_group.setGrupo(grupo);
                        showDialogFragment(dialog_add_group);
                        break;
                    }
                    case R.id.clear_chat: {
                        showGroupDialog("clear_chat");
                        break;
                    }
                    case R.id.leave_group: {
                        showGroupDialog("leave_group");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Notifications.setCurrentConversationId("");
    }

    public void showUserDialog(final String tag) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_chat, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        String titulo = "";
        switch (tag) {
            case "clear_chat":
                titulo = App.get().getString(R.string.clear_chat);
                break;
            case "block_user":
                if (amigos.isBloqueado())
                    titulo = String.format(App.get().getString(R.string.not_block_user)
                            , amigos.getConversacion().getMiembros().get(0).getApodo());
                else
                    titulo = String.format(App.get().getString(R.string.block_user)
                            , amigos.getConversacion().getMiembros().get(0).getApodo());
                break;
            case "report_user":
                titulo = "¿Deseas Reportar a " + amigos.getConversacion().getMiembros().get(0).getApodo() + "?";
                break;

        }
        TextView title = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        title.setText(titulo);
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

                switch (tag) {
                    case "clear_chat":
                        clearChat(amigos.getId_conversacion());
                        break;
                    case "block_user":
                        blockFriend();
                        break;
                    case "report_user":
                        reportFriend();
                        break;

                }
                alertDialog.dismiss();
            }
        });
    }

    public void changedGroupImage(String urlFoto) {
        grupo.setFoto(urlFoto);
        Glide.with(App.get())
                .load(urlFoto)
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                .into(chatAvatarReceiver);
    }

    public void clearChat(String idConversacion) {
        FirebaseManager.getInstance().borrarConversacion(
                FirebaseManager.getInstance().getUsuario().getId(),
                idConversacion, true);
        chatFragment.clearMessages();

    }

    public void blockFriend() {
        FirebaseManager.getInstance()
                .updateBloqueo(FirebaseManager.getInstance().getUsuario().getId()
                        , amigos.getId(), amigos.isBloqueado());
        finish();
    }


    public void reportFriend() {
        chatFragment.reportUser();
    }

    public boolean isAdminGroup(long idAdmin) {

        Log.i(TAG, "idAdmin" + idAdmin + " session" + SessionManager.getInstance().getUser().getId_usuario());
        return (idAdmin == Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()));
    }


    public void showGroupDialog(final String tag) {
        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_chat, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        String titulo = "";
        switch (tag) {
            case "clear_chat":
                titulo = App.get().getString(R.string.clear_chat);
                break;
            case "leave_group":
                titulo = "¿Deseas salir de este grupo?";
                break;

        }
        TextView title = dialoglayout.findViewById(R.id.fcm_tv_tittle);
        title.setText(titulo);
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

                switch (tag) {
                    case "clear_chat":
                        clearChat(grupo.getId_conversacion());
                        break;
                    case "leave_group":
                        chatFragment.onClickMemberToDelete();
                        break;

                }
                alertDialog.dismiss();
            }
        });
    }


    @OnClick(R.id.ly_go_detail_profile)
    void goToAvatarGroup() {
        if (amigos == null && grupo == null)
            return;

        chatAvatarReceiver.setVisibility(View.INVISIBLE);
        sqhivOptions.setVisibility(View.INVISIBLE);
        chatAvatarName.setText("Perfil");
        viewPager.setCurrentItem(2);
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        if (viewPager.getCurrentItem() == 1) {
            viewPager.setCurrentItem(0);
        } else if (viewPager.getCurrentItem() == 2) {
            chatAvatarReceiver.setVisibility(View.VISIBLE);
            sqhivOptions.setVisibility(View.VISIBLE);
            if (amigos != null)
                chatAvatarName.setText(amigos.getConversacion().getMiembros().get(0).getApodo());
            else if (grupo != null)
                chatAvatarName.setText(grupo.getNombre());
            viewPager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 1) {
            viewPager.setCurrentItem(0);
        } else if (chatFragment != null && chatFragment.isGifVisible()) {
            chatFragment.flContainerGif.setVisibility(View.GONE);
        } else if (viewPager != null && viewPager.getCurrentItem() == 2) {
            chatAvatarReceiver.setVisibility(View.VISIBLE);
            sqhivOptions.setVisibility(View.VISIBLE);
            if (amigos != null)
                chatAvatarName.setText(amigos.getConversacion().getMiembros().get(0).getApodo());
            else if (grupo != null)
                chatAvatarName.setText(grupo.getNombre());

            viewPager.setCurrentItem(0);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == GROUP_PROFILE) {
            if (resultCode == Activity.RESULT_OK) {

                finish();
            } else if (resultCode == 5) {
                chatFragment = ChatFragment.getInstance((Grupo) getIntent().getParcelableExtra(TAG_GROUP));
                if (data.getExtras() == null)
                    return;
                String foto = data.getExtras().getString("foto");
                if (foto != null)
                    grupo.setFoto(foto);
                Glide.with(App.get())
                        .load(grupo.getFoto())
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                        .into(chatAvatarReceiver);
            }
        }

        if (grupo != null) {
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
                final CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == this.RESULT_OK) {
                    if (viewPager != null && viewPager.getCurrentItem() != 2) {
                        Uri resultUri = result.getUri();
                        mCropImageUri = resultUri;
                    } else {
                        updatePhotoGroup(grupo.getNombre(), result.getUri(), grupo.getKey());
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
        }
    }

    private void updatePhotoGroup(String nombre, final Uri photoUri, String key) {
        if (profileUserFragment != null)
            profileUserFragment.showProgress();

        FirebaseManager.getInstance().actualizarGrupo(nombre, photoUri, key, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                if (profileUserFragment != null) {
                    profileUserFragment.hideProgress();
                    profileUserFragment.updateGroup(photoUri.toString());
                }
                showToast("Foto actualizada");
            }

            @Override
            public void onError() {
                if (profileUserFragment != null)
                    profileUserFragment.hideProgress();
                showToast("Error al cargar la foto");
            }
        });
    }

    private void showToast(String s) {
        if (getActivity() != null)
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setCropShape(CropImageView.CropShape.OVAL)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setFixAspectRatio(true)
                .start(this);
    }


    @Override
    public void onClickItem(MessageModelView friend) {

    }

    @Override
    public void onClickViewImage(String url) {
        if (url != null) {
            //startActivity(ImageActivity.intent(url,getBaseContext()));
        }
    }

    @Override
    public void onClickVideo(String url) {

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

    @OnClick(R.id.sqhiv_options)
    public void onViewClicked() {
        if (amigos != null) {
            onPopupUserButtonClick(sqhivOptions);
        } else {
            onPopupGroupButtonClick(sqhivOptions);
        }

    }

    @Override
    public void onClickDialogAddGroup(List<Amigos> friends, List<Grupo> grupos, String TAG) {

    }
}
