package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.holders.WallCommentVH;
import com.BarcelonaSC.BarcelonaApp.utils.CustomRecyclerView;

import java.util.List;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentAdapter extends CustomRecyclerView<RecyclerView.ViewHolder> {

    private final static String TAG = WallCommentAdapter.class.getSimpleName();
    private final static int ITEM_COMMENT = 1000;

    private List<WallCommentItem> commentList;
    private WallCommentClickListener wallCommentClickListener;

    public WallCommentAdapter(Context context, List<WallCommentItem> commentList) {
        super(context);
        this.commentList = commentList;
    }

    @Override
    public int getCurrentItemCount() {
        return commentList.size();
    }

    @Override
    public void onCurrentBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_COMMENT:
                configureCommentVH((WallCommentVH) holder, position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCurrentCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_COMMENT:
                return WallCommentVH.instance(parent);
        }
        return null;
    }

    @Override
    public int getCurrentItemViewType(int position) {
        if (getCurrentItemCount() != position) {
            return ITEM_COMMENT;
        }
        return -1;
    }

    private void configureCommentVH(final WallCommentVH holder, final int position) {
        holder.setData(commentList.get(position));
        holder.setDataUser(commentList.get(position).getUsuario());
        holder.clap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallCommentClickListener.onClapCommentListener(commentList.get(position).getIdcomentario());
                if (commentList.get(position).getYaaplaudio() != 1) {
                    commentList.get(position).setNaplausos(commentList.get(position).getNaplausos() + 1);
                    commentList.get(position).setYaaplaudio(1);
                } else {
                    commentList.get(position).setNaplausos(commentList.get(position).getNaplausos() - 1);
                    commentList.get(position).setYaaplaudio(0);
                }
                holder.setYaAplaudio(commentList.get(position));
                holder.countLike.setText(String.valueOf( commentList.get(position).getNaplausos()));


            }
        });
        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!commentList.get(position).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario()))
                    wallCommentClickListener.onShowProfileListener(commentList.get(position).getUsuario());
            }
        });

    }

    public void setWallCommentClickListener(WallCommentClickListener wallClickListener) {
        this.wallCommentClickListener = wallClickListener;
    }

    public interface WallCommentClickListener {

        void onShowProfileListener(UserItem wallItem);

        void onClapCommentListener(String idcomentario);

    }

    public void addAll(List<WallCommentItem> list) {
        commentList.addAll(list);
        notifyDataSetChanged();
    }

}