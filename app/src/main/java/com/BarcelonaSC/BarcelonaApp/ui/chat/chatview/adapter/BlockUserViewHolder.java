package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Miembro;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by cesar on 11/4/2018.
 */

public class BlockUserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.civ_photo)
    CircleImageView imgFriend;

    @BindView(R.id.civ_state)
    ImageView isOnline;

    @BindView(R.id.tv_apodo)
    TextView nameFriend;

    @BindView(R.id.tv_time)
    TextView timeMsg;

    @BindView(R.id.tv_message_content)
    TextView msgCont;

    @BindView(R.id.iv_trash)
    ImageView trashBtn;

    private Context context;

    public static BlockUserViewHolder getInstance(ViewGroup parent) {
        return new BlockUserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_friends, parent, false));
    }

    public BlockUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(final Miembro miembro){
        timeMsg.setVisibility(View.INVISIBLE);
        msgCont.setVisibility(View.GONE);
        trashBtn.setVisibility(View.GONE);

        if (miembro.getApodo() != null && miembro.getApodo().length() > 0) {
            nameFriend.setText(miembro.getNombre());
            msgCont.setVisibility(View.INVISIBLE);
            if (!miembro.getNombre().equals(miembro.getApodo())) {
                msgCont.setText(miembro.getApodo());
                msgCont.setVisibility(View.VISIBLE);
            }

        } else {
            nameFriend.setText(context.getText(R.string.group_no_name));
        }

        if (miembro.getId() != 0) {
            Crashlytics.log(Log.DEBUG, "IMAGEN", " ---> desde url");
            if (miembro.getFoto() == null
                    || !URLUtil.isValidUrl(miembro.getFoto())) {

                imgFriend.setImageDrawable(ContextCompat.getDrawable(App.get(), R.drawable.silueta));
                imgFriend.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgFriend.setTag(R.string.accept, miembro.getId());

                PhotoUpload.uploadFoto(miembro.getId()
                        , miembro.getFoto(), new PhotoUpload.PhotoListener() {
                            @Override
                            public void onPhotoSucces(String foto) {
                                miembro.setFoto(foto);
                                if (imgFriend.getTag(R.string.accept) == miembro.getId())
                                    Glide.with(App.get())
                                            .load(foto)
                                            .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                            .into(imgFriend);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            } else {
                Glide.with(App.get())
                        .load(miembro.getFoto())
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                        .into(imgFriend);
            }
        }
    }
}
