package com.BarcelonaSC.BarcelonaApp.ui.chat.friendselection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.crashlytics.android.Crashlytics;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Amigos;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pedro Gomez on 23/01/2018.
 */

public class FriendSelectedViewHolder extends RecyclerView.ViewHolder{

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
    }

    public void setData(Amigos item) {
        imgFriend.setAlpha((float) 1.0);
        if (item.getConversacion().getMiembros().get(0).getFoto()!=null && item.getConversacion().getMiembros().get(0).getFoto().length()>0) {
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde url");
            Glide.with(App.getAppContext())
                    .load(item.getConversacion().getMiembros().get(0).getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(imgFriend);
        }else{
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde drawable");
            Glide.with(context)
                    .load(R.drawable.silueta)
                    .into(imgFriend);
        }
        if (item.getConversacion().getMiembros().get(0).getApodo()!=null && item.getConversacion().getMiembros().get(0).getApodo().length()>0)
            nameFriend.setText(item.getConversacion().getMiembros().get(0).getApodo());
        else
            nameFriend.setText(context.getText(R.string.group_no_name));

    }
}
