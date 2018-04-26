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
 * Created by Pedro Gomez on 23/01/2018.
 */

public class FriendSelectedViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.friend_img)
    CircleImageView imgFriend;
    @BindView(R.id.friend_delete)
    ImageView deleteFriend;
    @BindView(R.id.friend_name)
    TextView nameFriend;

    private Context context;

    public static FriendSelectedViewHolder getInstance(ViewGroup parent) {
        return new FriendSelectedViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_selection, parent, false));
    }

    public FriendSelectedViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
        deleteFriend.setVisibility(View.GONE);
    }

    public void setData(final Miembro item) {
        imgFriend.setAlpha((float) 1.0);
        if (item.getId() != 0) {
            Crashlytics.log(Log.DEBUG, "IMAGEN", " ---> desde url");

            if (item.getFoto() == null
                    || !URLUtil.isValidUrl(item.getFoto())) {

                imgFriend.setImageDrawable(ContextCompat.getDrawable(App.get(), R.drawable.silueta));
                imgFriend.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imgFriend.setTag(R.string.accept, item.getId());

                PhotoUpload.uploadFoto(item.getId()
                        , item.getFoto(), new PhotoUpload.PhotoListener() {
                            @Override
                            public void onPhotoSucces(String foto) {
                                item.setFoto(foto);
                                if (imgFriend.getTag(R.string.accept) == item.getId())
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
                        .load(item.getFoto())
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                        .into(imgFriend);
            }
        }

        nameFriend.setText(item.getNombre()+" "+item.getApellido());
       /* if (item.getApodo() != null && item.getApodo().length() > 0)
            if (item.getId().equals(FirebaseManager.getInstance().getUsuario().getId())) {
                nameFriend.setText(FirebaseManager.getInstance().getUsuario().getApodo());
            } else {
                nameFriend.setText(item.getApodo());
            }
        else
            nameFriend.setText(context.getText(R.string.group_no_name));*/

    }
}
