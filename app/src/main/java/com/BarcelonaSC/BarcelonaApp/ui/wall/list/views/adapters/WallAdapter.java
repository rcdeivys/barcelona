
package com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallHeaderViewHolder;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallPostViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.CustomRecyclerView;

import java.util.List;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallAdapter extends CustomRecyclerView<RecyclerView.ViewHolder> {

    private final static int ITEM_HEADER = 1000;
    private final static int ITEM_POST = 10002;

    private Context context;
    private WallHeaderViewHolder headerVH;
    private WallPostViewHolder postVH;

    private List<Object> wallList;

    private WallClickListener wallClickListener;

    public WallAdapter(Context context, List<Object> wallList) {
        super(context);
        this.context = context;
        this.wallList = wallList;
    }

    @Override
    public int getCurrentItemCount() {
        return wallList.size();
    }

    @Override
    public void onCurrentBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_HEADER:
                headerVH = (WallHeaderViewHolder) holder;
                configureHeaderViewHolder(headerVH, position);
                break;
            case ITEM_POST:
                postVH = (WallPostViewHolder) holder;
                configurePostViewHolder(postVH, position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCurrentCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_HEADER:
                return WallHeaderViewHolder.getInstance(parent);
            case ITEM_POST:
                return WallPostViewHolder.getInstance(parent);
        }
        return null;
    }

    @Override
    public int getCurrentItemViewType(int position) {
        if (getCurrentItemCount() != position) {
            if (wallList.get(position) instanceof UserItem) {
                return ITEM_HEADER;
            } else if (wallList.get(position) instanceof WallItem) {
                return ITEM_POST;
            }
        }
        return -1;
    }

    public void addWall(List<Object> list) {
        wallList.addAll(wallList.size(), list);
        notifyDataSetChanged();
    }

    public void updateCountComment(int commentSize, int position) {
        ((WallItem) wallList.get(position)).setNcomentarios(commentSize);
        notifyItemChanged(position);
    }

    public void initList(List<Object> list) {
        wallList.addAll(1, list);
        notifyDataSetChanged();
    }

    private void configureHeaderViewHolder(final WallHeaderViewHolder holder, final int position) {
        holder.setData((UserItem) wallList.get(position));
        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onProfileListener();
            }
        });
        holder.createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onCreatePostListener();
            }
        });
    }

    private void configurePostViewHolder(final WallPostViewHolder holder, final int position) {
        holder.setDataPost((WallItem) wallList.get(position));
        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopUpWall);
                PopupMenu popup = new PopupMenu(wrapper, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        wallClickListener.onDeletePost(((WallItem) wallList.get(position)).getIdpost());
                        wallList.remove(position);
                        notifyDataSetChanged();
                        return false;
                    }
                });
                popup.inflate(R.menu.menu_pop_up_wall);
                popup.show();
            }
        });
        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((WallItem) wallList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario()))
                    wallClickListener.onShowProfileListener(((WallItem) wallList.get(position)).getUsuario());
            }
        });
        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((WallItem) wallList.get(position)).getYaaplaudio() == 1) {
                    holder.setLike(false);
                    ((WallItem) wallList.get(position)).setYaaplaudio(0);
                    ((WallItem) wallList.get(position)).setNaplausos(((WallItem) wallList.get(position)).getNaplausos() - 1);
                    holder.sizeLike.setText(String.valueOf(((WallItem) wallList.get(position)).getNaplausos()));
                } else {
                    holder.setLike(true);
                    ((WallItem) wallList.get(position)).setYaaplaudio(1);
                    ((WallItem) wallList.get(position)).setNaplausos(((WallItem) wallList.get(position)).getNaplausos() + 1);
                    holder.sizeLike.setText(String.valueOf(((WallItem) wallList.get(position)).getNaplausos()));
                }
                wallClickListener.onClickLikeListener(((WallItem) wallList.get(position)).getIdpost());

                // notifyItemChanged(position);
            }
        });
        holder.sizeComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onClickCommentListener(((WallItem) wallList.get(position)).getIdpost(), ((WallItem) wallList.get(position)).getNaplausos(), position);
            }
        });
        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onClickCommentListener(((WallItem) wallList.get(position)).getIdpost(), ((WallItem) wallList.get(position)).getNaplausos(), position);
            }
        });
    }

    public void setWallClickListener(WallClickListener wallClickListener) {
        this.wallClickListener = wallClickListener;
    }

    public interface WallClickListener {
        void onCreatePostListener();

        void onProfileListener();

        void onDeletePost(String id_post);

        void onShowProfileListener(UserItem userItem);

        void onClickLikeListener(String id_post);

        void onClickCommentListener(String id_post, Integer count_clap, int position);
    }
}
