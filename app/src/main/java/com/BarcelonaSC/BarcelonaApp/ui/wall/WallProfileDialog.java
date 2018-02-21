package com.BarcelonaSC.BarcelonaApp.ui.wall;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.Dialogs.FriendsPopup;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.text.DateFormatSymbols;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 1/15/18.
 */

public class WallProfileDialog extends DialogFragment {

    private static String TAG = WallProfileDialog.class.getSimpleName();

    @BindView(R.id.icon_close)
    ImageView iconClose;
    @BindView(R.id.text_name)
    public FCMillonariosTextView textName;
    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.text_registrado)
    TextView registrado;
    @BindView(R.id.id_hincha)
    FCMillonariosTextView id_hincha;
    @BindView(R.id.content_button_add_friend)
    RelativeLayout addFriend;
    @BindView(R.id.icon_add)
    ImageView iconAdd;
    @BindView(R.id.input_add_friend)
    FCMillonariosTextView txtAddFriend;
    @BindView(R.id.content_button_invite_group)
    RelativeLayout contentInviteGroup;
    @BindView(R.id.block_user)
    FCMillonariosTextView btnBlockUser;
    @BindView(R.id.btn_accept)
    Button btnAccept;
    @BindView(R.id.content_block_user)
    ConstraintLayout contentBlockUser;
    @BindView(R.id.input_block)
    FCMillonariosTextView inputBlock;

    UserItem userItem;

    FriendsModelView friend;
    Amigos amigos;
    Usuario usuario;

    boolean block = true;

    private boolean isRequestPending = false;
    private boolean isFriend = false;

    public static WallProfileDialog instance(UserItem userItem) {
        WallProfileDialog dialog = new WallProfileDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userItem);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_profile_user);
        dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        usuario = FirebaseManager.getInstance().getUsuario();
        ButterKnife.bind(this, dialogo);
        setData();
        return dialogo;
    }

    @OnClick(R.id.content_button_add_friend)
    public void addFriend() {

        //if (!isRequestPending) {
        //    sendInvitedToUser(SessionManager.getInstance().getUser().getId_usuario(), userItem.getId_usuario());
        //}

        //if (isFriend() && !block) {
        //    startActivity(ChatActivity.intent(amigos, getContext()));
        //    this.dismissDialog();
        //}

    }

    @OnClick(R.id.btn_accept)
    public void dismissDialog() {
        dismiss();
    }

    @OnClick(R.id.content_button_invite_group)
    public void inviteGroup() {
        if (!block) {
            Intent intent = new Intent(this.getActivity(), GroupsActivity.class);
            startActivity(intent);
            this.dismissDialog();
        }
    }

    @OnClick(R.id.block_user)
    public void initBlockUser() {
        if (!block) {
            inputBlock.setText("Este hincha ha sido bloqueado");
        } else {
            inputBlock.setText("Este hincha ha sido desbloqueado");
        }
        contentBlockUser.setVisibility(View.VISIBLE);
        FirebaseManager.getInstance().updateBloqueo(Long.valueOf(SessionManager.getInstance().getUser().getId_usuario()), Long.valueOf(userItem.getId_usuario()), block);
    }

    public void setData() {
        if (getArguments().getSerializable("user") != null) {
            userItem = (UserItem) getArguments().getSerializable("user");
            textName.setText(userItem.getApodo() != null && !userItem.getApodo().equals("") ? userItem.getApodo() : userItem.getNombre() + " " + userItem.getApellido());
            Glide.with(getActivity()).load(userItem.getFoto()).apply(new RequestOptions().placeholder(Commons.getDrawable(R.drawable.silueta)).error(Commons.getDrawable(R.drawable.silueta))).into(imgProfile);
            registrado.setText("DESDE EL \n" + Commons.simpleDateFormat(userItem.getFechaRegistro()).substring(0, 2) + "/" + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(userItem.getFechaRegistro()).substring(3, 5))) + "/" + userItem.getFechaRegistro().substring(0, 4));
            id_hincha.setText("N°" + userItem.getId_usuario());
            addFriend.setVisibility(View.GONE);

//            if (isFriend()) {
//                iconAdd.setImageDrawable(Commons.getDrawable(R.drawable.pelota));
//                txtAddFriend.setText(Commons.getString(R.string.init_chat));
//                contentInviteGroup.setVisibility(View.VISIBLE);
//                btnBlockUser.setVisibility(View.VISIBLE);
//                if (block) {
//                    btnBlockUser.setText("Desbloquear");
//                    addFriend.setEnabled(false);
//                    addFriend.setAlpha(0.5f);
//                    contentInviteGroup.setAlpha(0.5f);
//                    block = false;
//                } else {
//                    btnBlockUser.setText("Bloquear");
//                    block = true;
//                }
//            } else {
                //verifiedPendingRequest();
                contentInviteGroup.setVisibility(View.GONE);
            //}
        }
    }

    public void setParams(FriendsModelView friend, Amigos amigos, FriendsPopup.OnItemClickListener onItemClickListener) {
        this.friend = friend;
        this.amigos = amigos;
    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols(new Locale("es", "ES"));
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 12) {
            month = months[num - 1];
        }
        return month;
    }

    @OnClick({R.id.icon_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_close:
                dismiss();
                break;
        }
    }

    private boolean isFriend() {
        for (Amigos amg : usuario.getAmigos()) {
            if (String.valueOf(amg.getId()).equals(userItem.getId_usuario())) {
                block = amg.isBloqueado();
                isRequestPending = false;
                amigos = amg;
                return true;
            }
        }
        return false;
    }

    public void sendInvitedToUser(String myID, String UserToInvite) {
        FirebaseManager.getInstance().enviarNuevaSolicitud(myID, UserToInvite, new FirebaseManager.FireResultListener() {
            @Override
            public void onComplete() {
                iconAdd.setImageDrawable(Commons.getDrawable(R.drawable.solicitud_enviada));
                txtAddFriend.setText(Commons.getString(R.string.request_friends_send));
            }

            @Override
            public void onError() {

            }
        });
    }

    public void verifiedPendingRequest() {
        FirebaseManager.getInstance().isFriendInvited(SessionManager.getInstance().getUser().getId_usuario(), userItem.getId_usuario(), new FirebaseManager.FireValuesListener() {
            @Override
            public void onComplete(String value) {
                Log.i(TAG, "verifiedPendingRequest: " + value);
                if (value.equals("0")) {
                    //TODO solicitud enviada esperando por respuesta del amigo
                    iconAdd.setImageDrawable(Commons.getDrawable(R.drawable.solicitud_enviada));
                    txtAddFriend.setText(Commons.getString(R.string.request_friends_send));
                    isRequestPending = true;
                }
            }

            @Override
            public void onCanceled() {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        //isFriend();
    }
}