package com.BarcelonaSC.BarcelonaApp.ui.wall.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.models.WallSearchItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 26/01/2018.
 */

public class WallSearchViewHolder extends RecyclerView.ViewHolder {

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

    public static WallSearchViewHolder getInstance(ViewGroup parent) {
        return new WallSearchViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_wall_search, parent, false));
    }

    public WallSearchViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(final WallSearchItem item) {
        timeMsg.setVisibility(View.GONE);
        msgCont.setVisibility(View.GONE);
        trashBtn.setVisibility(View.GONE);
        imgFriend.setAlpha((float) 1.0);

        Glide.with(App.get())
                .load(item.getFoto())
                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                .into(imgFriend);

        if (item.getApodo() != null && item.getApodo().length() > 0)
            nameFriend.setText(item.getApodo());
        else
            nameFriend.setText(item.getNombre() + " " + item.getApellido());

    }
}
