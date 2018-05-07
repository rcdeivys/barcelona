package com.BarcelonaSC.BarcelonaApp.ui.wall;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.models.UsuariosAplauso;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Leonardojpr on 3/22/18.
 */

public class WallClapListAdapter extends RecyclerView.Adapter<WallClapListViewHolder> {

    Context context;
    List<UsuariosAplauso> claps;
    WallAdapter.WallClickListener wallClickListener;
    WallCommentAdapter.WallCommentClickListener wallCommentClickListener;

    public DialogClose dialogClose;

    public void setDialogClose(DialogClose dialogClose) {
        this.dialogClose = dialogClose;
    }

    public WallClapListAdapter(Context context, List<UsuariosAplauso> claps, WallAdapter.WallClickListener wallClickListener, WallCommentAdapter.WallCommentClickListener wallCommentClickListener, DialogClose dialogClose) {
        this.context = context;
        this.claps = claps;
        this.wallClickListener = wallClickListener;
        this.wallCommentClickListener = wallCommentClickListener;
        this.dialogClose = dialogClose;
    }

    @Override
    public WallClapListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return WallClapListViewHolder.getInstance(parent);
    }

    @Override
    public void onBindViewHolder(WallClapListViewHolder holder, int position) {
        holder.setData(claps.get(position), wallClickListener, wallCommentClickListener, dialogClose);
    }

    @Override
    public int getItemCount() {
        return claps.size();
    }
}

class WallClapListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_profile)
    CircleImageView imgProfile;
    @BindView(R.id.name)
    FCMillonariosTextView name;
    @BindView(R.id.content_list)
    RelativeLayout contentList;

    Context context;

    public static WallClapListViewHolder getInstance(ViewGroup parent) {
        return new WallClapListViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_wall_clap, parent, false));
    }

    public WallClapListViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void setData(final UsuariosAplauso usuariosAplauso, final WallAdapter.WallClickListener wallClickListener, final WallCommentAdapter.WallCommentClickListener wallCommentClickListener, final DialogClose dialogClose) {
        Glide.with(context).load(usuariosAplauso.getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        name.setText(usuariosAplauso.getNombre());
        contentList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (wallClickListener != null)
                    wallClickListener.onShowProfileListener(String.valueOf(usuariosAplauso.getId()));

                if (wallCommentClickListener != null)
                    wallCommentClickListener.onShowProfileListener(String.valueOf(usuariosAplauso.getId()));

                dialogClose.closeDialogListener();

            }
        });
    }
}
