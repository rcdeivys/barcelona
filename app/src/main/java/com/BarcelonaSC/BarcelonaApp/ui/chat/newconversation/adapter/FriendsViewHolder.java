package com.BarcelonaSC.BarcelonaApp.ui.chat.newconversation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.FriendsModelView;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pedro Gomez on 26/01/2018.
 */

public class FriendsViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.civ_photo)
    CircleImageView imgFriend;

    @BindView(R.id.civ_state)
    CircleImageView isOnline;

    @BindView(R.id.tv_apodo)
    TextView nameFriend;

    @BindView(R.id.tv_time)
    TextView timeMsg;

    @BindView(R.id.tv_message_content)
    TextView msgCont;

    @BindView(R.id.iv_trash)
    ImageView trashBtn;
    @BindView(R.id.item_content)
    LinearLayout itemContent;

    private Context context;

    public static FriendsViewHolder getInstance(ViewGroup parent) {
        return new FriendsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false));
    }

    public FriendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(final FriendsModelView item) {
        timeMsg.setVisibility(View.INVISIBLE);
        msgCont.setVisibility(View.GONE);
        trashBtn.setVisibility(View.GONE);
        imgFriend.setAlpha((float) 1.0);
        if (item.getId_amigo() != 0) {

            if (item.getPhoto() == null || !URLUtil.isValidUrl(item.getPhoto())) {
                imgFriend.setImageDrawable(ContextCompat.getDrawable(App.get(), R.drawable.silueta));
                imgFriend.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgFriend.setTag(R.string.accept, item.getId_amigo());
                PhotoUpload.uploadFoto(item.getId_amigo()
                        , item.getPhoto(), new PhotoUpload.PhotoListener() {
                            @Override
                            public void onPhotoSucces(String foto) {
                                item.setPhoto(foto);
                                if (imgFriend.getTag(R.string.accept) == item.getId_amigo())
                                    Glide.with(App.get())
                                            .load(foto)
                                            .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                            .into(imgFriend);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            } else
                Glide.with(App.get())
                        .load(item.getPhoto())
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                        .into(imgFriend);
        }
        if (item.getIsonline() != null) {
            switch (item.getIsonline()) {
                case ONLINE:
                    Glide.with(context)
                            .load(R.drawable.state_online)
                            .into(isOnline);
                    break;
                case BUSY:
                    Glide.with(context)
                            .load(R.drawable.state_offline)
                            .into(isOnline);
                    break;
                case OFFLINE:
                    Glide.with(context)
                            .load(R.drawable.state_offline)
                            .into(isOnline);
                    break;
                default:
                    Glide.with(context)
                            .load(R.drawable.state_offline)
                            .into(isOnline);
                    break;
            }
        }
        nameFriend.setText(item.getNombre() +" "+ item.getApellido());
        if (item.getApodo() != null && item.getApodo().length() > 0) {

            msgCont.setVisibility(View.INVISIBLE);
            if (!item.getNombre().equals(item.getApodo())) {
                msgCont.setText(item.getApodo());
                msgCont.setVisibility(View.VISIBLE);
            }

        } else {
            msgCont.setText("");
        }
    }
}
