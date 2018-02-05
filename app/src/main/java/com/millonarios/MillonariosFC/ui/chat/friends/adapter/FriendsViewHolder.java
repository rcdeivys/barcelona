package com.millonarios.MillonariosFC.ui.chat.friends.adapter;

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
 * Created by Pedro Gomez on 26/01/2018.
 */

public class FriendsViewHolder extends RecyclerView.ViewHolder{

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

    private Context context;

    public static FriendsViewHolder getInstance(ViewGroup parent) {
        return new FriendsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false));
    }

    public FriendsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(FriendsModelView item) {
        timeMsg.setVisibility(View.GONE);
        msgCont.setVisibility(View.GONE);
        trashBtn.setVisibility(View.GONE);
        imgFriend.setAlpha((float) 1.0);
        if (item.getPhoto()!=null && item.getPhoto().length()>0) {
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
        if(item.getIsonline()!=null) {
            switch (item.getIsonline()){
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
        if (item.getApodo()!=null && item.getApodo().length()>0)
            nameFriend.setText(item.getApodo());
        else
            nameFriend.setText(context.getText(R.string.group_no_name));

    }
}
