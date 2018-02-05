package com.millonarios.MillonariosFC.ui.chat.groupdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.crashlytics.android.Crashlytics;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Pedro Gomez on 31/01/2018.
 */

public class GroupDetailViewHolder extends RecyclerView.ViewHolder{

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

    public static GroupDetailViewHolder getInstance(ViewGroup parent) {
        return new GroupDetailViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false));
    }

    public GroupDetailViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(FriendsModelView item) {
        timeMsg.setVisibility(View.GONE);
        msgCont.setVisibility(View.GONE);
        trashBtn.setVisibility(View.GONE);
        imgFriend.setAlpha((float) 1.0);
        if (item.getPhoto().length()>0) {
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde url");
            Glide.with(context)
                    .load(item.getPhoto())
                    .into(imgFriend);
        }else{
            Crashlytics.log(Log.DEBUG,"IMAGEN"," ---> desde drawable");
            Glide.with(context)
                    .load(R.drawable.silueta)
                    .into(imgFriend);
        }
        if(item.getIsonline() == FriendsModelView.STATUS.ONLINE) {
            Glide.with(context)
                    .load(R.drawable.state_online)
                    .into(isOnline);
        }else if(item.getIsonline() == FriendsModelView.STATUS.OFFLINE) {
            Glide.with(context)
                    .load(R.drawable.state_offline)
                    .into(isOnline);
        }else if(item.getIsonline() == FriendsModelView.STATUS.BUSY) {
            Glide.with(context)
                    .load(R.drawable.state_offline)
                    .into(isOnline);
        }

        if (item.getApodo().length()>0)
            nameFriend.setText(item.getApodo());
        else
            nameFriend.setText(context.getText(R.string.group_no_name));

    }
}
