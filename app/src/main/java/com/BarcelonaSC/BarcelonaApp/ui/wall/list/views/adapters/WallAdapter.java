
package com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import com.BarcelonaSC.BarcelonaApp.models.response.WallProfileResponse;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallHeaderViewHolder;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallPostProfileVIewHolder;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallPostViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.CustomRecyclerView;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;

import java.util.List;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallAdapter extends CustomRecyclerView<RecyclerView.ViewHolder> {

    private final static int ITEM_HEADER = 1000;
    private final static int ITEM_POST = 10002;
    private final static int ITEM_PROFILE = 10003;

    private Context context;
    private WallHeaderViewHolder headerVH;
    private WallPostViewHolder postVH;
    private WallPostProfileVIewHolder profileVH;

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
            case ITEM_PROFILE:
                profileVH = (WallPostProfileVIewHolder) holder;
                configureProfileWallViewHolder(profileVH, position);
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
            case ITEM_PROFILE:
                return WallPostProfileVIewHolder.getInstance(parent);
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
            } else if (wallList.get(position) instanceof WallProfileResponse) {
                return ITEM_PROFILE;
            }
        }
        return -99;
    }

    public void addWall(List<Object> list) {
        wallList.addAll(wallList.size(), list);
        notifyDataSetChanged();
    }

    public void updateCountComment(int commentSize, int position) {
        ((WallItem) wallList.get(position)).setNcomentarios(((WallItem) wallList.get(position)).getNcomentarios() + 1);
        notifyItemChanged(position);
    }

    public void removeCountComment(int commentSize, int position) {
        ((WallItem) wallList.get(position)).setNcomentarios(((WallItem) wallList.get(position)).getNcomentarios() - 1);
        notifyItemChanged(position);
    }

    public void updateClapPost(int yaaplaudio, int position) {
        ((WallItem) wallList.get(position)).setYaaplaudio(yaaplaudio);
        notifyItemChanged(position);
    }

    public void deletePost(int position) {
        wallList.remove(position);
        notifyItemChanged(position);
    }

    public void initList(List<Object> list) {
        wallList.clear();
        wallList.addAll(list);
        notifyDataSetChanged();
    }

    private void configureProfileWallViewHolder(final WallPostProfileVIewHolder holder, final int position) {
        holder.setData(((WallProfileResponse) wallList.get(position)));
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
        holder.iconSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onCickSearch();
            }
        });
    }

    private void configurePostViewHolder(final WallPostViewHolder holder, final int position) {
        holder.setDataPost((WallItem) wallList.get(position), wallClickListener, null);

        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopUpWall);
                PopupMenu popup = new PopupMenu(wrapper, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.delete_post) {
                            wallClickListener.onDeletePost(((WallItem) wallList.get(position)).getIdpost(), new RemoveListener() {
                                @Override
                                public void onSuccessDeleted() {
                                    wallList.remove(position);
                                    notifyDataSetChanged();
                                }
                            });
                        } else if (item.getItemId() == R.id.editar_post) {
                            wallClickListener.onEditPostListener(((WallItem) wallList.get(position)));
                        } else {
                            wallClickListener.onClickReportarPost(((WallItem) wallList.get(position)).getIdpost());
                        }

                        return false;
                    }
                });
                if (((WallItem) wallList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario())) {
                    popup.inflate(R.menu.menu_pop_up_wall);
                } else {
                    popup.inflate(R.menu.pop_up_other_user);
                }

                popup.show();
            }
        });

        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((WallItem) wallList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario()))
                    wallClickListener.onShowProfileListener(((WallItem) wallList.get(position)).getUsuario().getId_usuario());
            }
        });

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentListener commentListener = new CommentListener() {
                    @Override
                    public void onSuccessComment() {
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
                    }
                };


                wallClickListener.onClickLikeListener(((WallItem) wallList.get(position)).getIdpost(), commentListener);

                // notifyItemChanged(position);
            }
        });
        holder.sizeComent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onClickCommentListener(((WallItem) wallList.get(position)), position);
            }
        });

        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onClickCommentListener(((WallItem) wallList.get(position)), position);
            }
        });

        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallClickListener.onExpandImagen(holder.imgPost.getDrawable());
            }
        });

        holder.videoPost.setCustomVideoViewPlayListener(new CustomVideoView.CustomVideoViewPlayListener() {
            @Override
            public void play() {
                wallClickListener.playVideo(position);
            }
        });

      /*  holder.videoPost.setButtonFullScreen(R.drawable.ic_media_fullscreen, Commons.getColor(R.color.white), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoPost.pause();
                wallClickListener.onFullScreenVideo(((WallItem) wallList.get(position)).getFoto(), holder.videoPost.getCurrentPosition());
            }
        });*/


    }

    public void pauseVideo(int position) {
        notifyItemChanged(position);
    }

    public void setWallClickListener(WallClickListener wallClickListener) {
        this.wallClickListener = wallClickListener;
    }

    public interface CommentListener {

        void onSuccessComment();

    }

    public interface RemoveListener {
        void onSuccessDeleted();
    }

    public interface WallClickListener {
        void onCreatePostListener();

        void onEditPostListener(WallItem wallItem);

        void onProfileListener();

        void onDeletePost(String id_post, RemoveListener removeListener);

        void onShowProfileListener(String id);

        void onClickLikeListener(String id_post, CommentListener commentListener);

        void onClickCommentListener(WallItem wallItem, int position);

        void onExpandImagen(Drawable drawable);

        void onCickSearch();

        void onClickReportarPost(String id);

        void onFullScreenVideo(String url, int position);

        void playVideo(int position);

    }
}
