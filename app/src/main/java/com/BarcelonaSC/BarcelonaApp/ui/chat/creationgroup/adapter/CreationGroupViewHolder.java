package com.BarcelonaSC.BarcelonaApp.ui.chat.creationgroup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pedro Gomez on 24/01/2018.
 */

public class CreationGroupViewHolder  extends RecyclerView.ViewHolder{

    @BindView(R.id.friend_img)
    CircleImageView imgFriend;

    @BindView(R.id.friend_delete)
    ImageView deleteFriend;

    @BindView(R.id.friend_name)
    TextView nameFriend;

    private Context context;

    public static CreationGroupViewHolder getInstance(ViewGroup parent) {
        return new CreationGroupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_selection, parent, false));
    }

    public CreationGroupViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(Amigos item) {
        imgFriend.setAlpha((float) 1.0);


        PhotoUpload.uploadFoto(item.getConversacion().getMiembros().get(0).getId(), new PhotoUpload.PhotoListener() {
            @Override
            public void onPhotoSucces(String foto) {

                Glide.with(App.get())
                        .load(foto)
                        .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                        .into(imgFriend);
            }

            @Override
            public void onError() {

            }
        });

        if ( item.getConversacion().getMiembros().get(0).getApodo()!=null && !item.getConversacion().getMiembros().get(0).getApodo().equals("") )
            nameFriend.setText(item.getConversacion().getMiembros().get(0).getApodo());
        else
            nameFriend.setText(context.getText(R.string.group_no_name));

    }
}
