package com.millonarios.MillonariosFC.ui.chat.chatview;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.models.firebase.Amigos;
import com.millonarios.MillonariosFC.models.firebase.Grupo;
import com.millonarios.MillonariosFC.ui.chat.groupdetail.GroupDetailActivity;
import com.millonarios.MillonariosFC.ui.gallery.views.GalleryFragment;
import com.millonarios.MillonariosFC.utils.CustomViewPager;
import com.millonarios.MillonariosFC.utils.PhotoUpload;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    public static final String TAG_GROUP = "group_chat";
    public static final String TAG_PRIVATE = "private_chat";

    @BindView(R.id.chat_avatar_receiver)
    CircleImageView chatAvatarReceiver;

    @BindView(R.id.chat_name_receiver)
    TextView chatAvatarName;

    @BindView(R.id.content_viewpager)
    public CustomViewPager viewPager;

    ChatViewPagerAdapter chatViewPagerAdapter;
    ChatFragment chatFragment;
    public GalleryFragment galleryFragment;

    private Grupo grupo;
    private Amigos amigos;

    public static Intent intent(Amigos amigos, Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(TAG_PRIVATE, amigos);
        return intent;
    }

    public static Intent intent(Grupo grupo, Context context) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(TAG_GROUP, grupo);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        if (getIntent().getParcelableExtra(TAG_PRIVATE) != null) {
            chatFragment = ChatFragment.getInstance((Amigos) getIntent().getParcelableExtra(TAG_PRIVATE));
            amigos = getIntent().getParcelableExtra(TAG_PRIVATE);
            setImageCircle(amigos.getId());
            chatAvatarName.setText(amigos.getConversacion().getMiembros().get(0).getApodo());
        }
        if (getIntent().getParcelableExtra(TAG_GROUP) != null) {
            chatFragment = ChatFragment.getInstance((Grupo) getIntent().getParcelableExtra(TAG_GROUP));
            grupo = getIntent().getParcelableExtra(TAG_GROUP);
            Glide.with(App.get())
                    .load(grupo.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(chatAvatarReceiver);
            chatAvatarName.setText(grupo.getNombre());
        }

        galleryFragment = GalleryFragment.getInstance(null);
        viewPager.setPagingEnabled(false);
        chatViewPagerAdapter = new ChatViewPagerAdapter(getSupportFragmentManager(), chatFragment, galleryFragment);
        viewPager.setAdapter(chatViewPagerAdapter);
    }

    @OnClick(R.id.ly_go_detail_profile)
    void goToAvatarGroup() {
        if (grupo != null) {
            startActivity(GroupDetailActivity.intent(grupo, getBaseContext()));
        }
    }

    @OnClick(R.id.back_button)
    void onBackBtnToolbar() {
        if (viewPager.getCurrentItem() == 1) {
            viewPager.setCurrentItem(0);
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setImageCircle(Long iduser) {
        PhotoUpload.uploadFoto(iduser, new PhotoUpload.PhotoListener() {
            @Override
            public void onPhotoSucces(String foto) {
                Glide.with(App.get())
                        .load(foto)
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                        .into(chatAvatarReceiver);
            }

            @Override
            public void onError() {

            }
        });
    }
}
