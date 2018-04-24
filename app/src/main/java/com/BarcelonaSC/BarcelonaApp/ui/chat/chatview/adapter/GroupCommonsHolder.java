package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels.GroupModelView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cesar on 05/04/2018.
 */

public class GroupCommonsHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.friend_img)
    CircleImageView imgFriend;
    @BindView(R.id.friend_name)
    TextView nameFriend;

    private Context context;

    public static GroupCommonsHolder getInstance(ViewGroup parent) {
        return new GroupCommonsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_group, parent, false));
    }

    public GroupCommonsHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(final GroupModelView item) {
        nameFriend.setText(item.getNameGroup());
        Glide.with(App.get())
                .load(item.getImageGroup())
                .apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm))
                .into(imgFriend);
    }
}