package com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.text_create_post)
    public FCMillonariosTextView createPost;

    private Context context;

    public static WallHeaderViewHolder getInstance(ViewGroup parent) {
        return new WallHeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_create, parent, false));
    }

    public WallHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(UserItem item) {
        if (item.getFoto() != null) {
            Glide.with(context).load(item.getFoto()).apply(new RequestOptions().error(Commons.getDrawable(R.drawable.silueta)).placeholder(R.drawable.silueta
            )).into(imgProfile);
        }
    }
}
