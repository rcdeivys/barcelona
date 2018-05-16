package com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.response.WallProfileResponse;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class WallPostProfileVIewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_profile)
    CircleImageView circleImageView;
    @BindView(R.id.name_user)
    FCMillonariosTextView userName;
    @BindView(R.id.hincha_desde)
    FCMillonariosTextView hinchaDesde;
    @BindView(R.id.count_post)
    FCMillonariosTextView countPost;
    @BindView(R.id.count_clap)
    FCMillonariosTextView countClap;
    @BindView(R.id.count_comment)
    FCMillonariosTextView countComment;

    Context context;

    public static WallPostProfileVIewHolder getInstance(ViewGroup parent) {
        return new WallPostProfileVIewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_profile_wall, parent, false));
    }

    public WallPostProfileVIewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setData(WallProfileResponse item) {


        Glide.with(context).load(item.getUsuario().getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(circleImageView);

        userName.setText(item.getUsuario().getNombre() + " " + item.getUsuario().getApellido());

        hinchaDesde.setText(item.getUsuario().getDede());

        countPost.setText(String.valueOf(item.getPublicaciones()));
        countClap.setText(String.valueOf(item.getAplausosRecibidos()));
        countComment.setText(String.valueOf(item.getComentarioRecibidos()) + "/" + String.valueOf(item.getComentarioDados()));
    }
}
