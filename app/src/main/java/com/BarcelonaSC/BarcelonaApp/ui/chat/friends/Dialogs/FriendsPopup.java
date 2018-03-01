package com.BarcelonaSC.BarcelonaApp.ui.chat.friends.Dialogs;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.mvp.FriendsPresenter;
import com.BarcelonaSC.BarcelonaApp.ui.chat.groups.GroupsActivity;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import java.text.DateFormatSymbols;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cesar on 24/01/2018.
 */

public class FriendsPopup extends DialogFragment {

    View view;
    private static final String TAG = FriendsPopup.class.getSimpleName();

    @Inject
    FriendsPresenter presenter;

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
    RelativeLayout addFriendContent;
    @BindView(R.id.block_user)
    FCMillonariosTextView blockUser;
    //boton init chat
    @BindView(R.id.icon_add)
    ImageView iconAdd;
    @BindView(R.id.input_add_friend)
    FCMillonariosTextView txtAddFriend;
    //boton init group
    @BindView(R.id.content_button_invite_group)
    RelativeLayout addGroupContent;
    @BindView(R.id.icon_add_group)
    ImageView iconAddGroup;
    @BindView(R.id.input_add_group)
    FCMillonariosTextView txtAddGroup;


    FriendsModelView friend;

    OnItemClickListener onItemClickListener;
    Amigos amigos;

    private String NumberUser="N° ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle state) {
//        super.onCreateView(inflater, parent, state);
//        view = getActivity().getLayoutInflater().inflate(R.layout.friends_popup, parent, false);
//        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        ButterKnife.bind(this, view);
//
//        Log.i(TAG, "--->onCreateView(): " + TAG);
//
//        initGUIComponents();
//
//        return view;
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialogo = super.onCreateDialog(savedInstanceState);
        dialogo.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogo.setContentView(R.layout.dialog_profile_user);
        dialogo.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ButterKnife.bind(this, dialogo);

        Log.i(TAG, "--->onCreateView(): " + TAG);

        initGUIComponents();

        return dialogo;
    }

    private void initGUIComponents() {

        //content init chat
        iconAdd.setImageDrawable(Commons.getDrawable(R.drawable.iniciar_chat));
        txtAddFriend.setText(Commons.getString(R.string.init_chat));

        //content init group
        iconAddGroup.setImageDrawable(Commons.getDrawable(R.drawable.invitar_grupo));
        txtAddGroup.setText(Commons.getString(R.string.invited_grupo));

        //get status is user block
        if (friend.getBloqueado()) {
            blockUser.setText(Commons.getString(R.string.unlock));
            addFriendContent.setEnabled(false);
            addFriendContent.setAlpha(Float.parseFloat("0.5"));
            addGroupContent.setEnabled(false);
            addGroupContent.setAlpha(Float.parseFloat("0.5"));
        } else {
            blockUser.setText(Commons.getString(R.string.block));
            addFriendContent.setEnabled(true);
            addFriendContent.setAlpha(Float.parseFloat("1"));
            addGroupContent.setEnabled(true);
            addGroupContent.setAlpha(Float.parseFloat("1"));
        }

        //set apodo or name
        textName.setText(friend.getApodo() != null ? friend.getApodo() : friend.getNombre());
        Log.i(TAG,"--->fecha de creacion friend"+friend.getCreated_at());
        registrado.setText(("DESDE EL \n" + Commons.simpleDateFormat(friend.getCreated_at()).substring(0, 2) + "/" + getMonthForInt(Integer.parseInt(Commons.simpleDateFormat(friend.getCreated_at()).substring(3, 5))) + "/" + friend.getCreated_at().substring(0, 4)));
        id_hincha.setText(NumberUser.concat(String.valueOf(friend.getId_amigo())));
//
        //set image profile to imageview
        Glide.with(App.getAppContext())
                .load(friend.getPhoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                .into(imgProfile);
        imgProfile.setBorderColor(ContextCompat.getColor(App.getAppContext(), R.color.colorPrimary));
        imgProfile.setBorderWidth(6);
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

    @OnClick(R.id.content_button_add_friend)
    public void initChat() {
        startActivity(ChatActivity.intent(amigos, getContext()));
    }

    @OnClick(R.id.block_user)
    public void initBlockUser() {
        Log.i(TAG, "--->ID AMIGO BLOQUEAR: " + friend.getId_amigo());
        FirebaseManager.getInstance().updateBloqueo(FirebaseManager.getInstance().getUsuario().getId(), friend.getId_amigo(), friend.getBloqueado());
        this.dismissAllowingStateLoss();
    }

    @OnClick(R.id.content_button_invite_group)
    public void initInvitedGroup() {
        Intent intent = new Intent(this.getActivity(), GroupsActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.icon_close)
    public void closeDialog() {
        this.dismissAllowingStateLoss();
    }

    public void setParams(FriendsModelView friend, Amigos amigos, OnItemClickListener onItemClickListener) {
        this.friend = friend;
        this.amigos = amigos;
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItemPopup(String TAG);
    }
}
