package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.holders.WallCommentVH;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders.WallPostViewHolder;
import com.BarcelonaSC.BarcelonaApp.utils.CustomRecyclerView;

import java.util.List;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentAdapter extends CustomRecyclerView<RecyclerView.ViewHolder> {

    private final static String TAG = WallCommentAdapter.class.getSimpleName();
    private final static int ITEM_POST = 1000;
    private final static int ITEM_COMMENT = 1001;

    private List<Object> commentList;
    private WallCommentClickListener wallCommentClickListener;
    private Context context;
    public CommentLikeListener commentLikeListener;
    private String idPost;
    public static boolean canDeleted=true;

    public WallCommentAdapter(Context context, List<Object> commentList, String idpost) {
        super(context);
        this.context = context;
        this.commentList = commentList;
        this.idPost = idpost;
    }

    @Override
    public int getCurrentItemCount() {
        return commentList.size();
    }

    @Override
    public void onCurrentBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case ITEM_POST:
                configurePostViewHolder((WallPostViewHolder) holder, position);
                break;
            case ITEM_COMMENT:
                configureCommentVH((WallCommentVH) holder, position);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCurrentCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_POST:
                return WallPostViewHolder.getInstance(parent);
            case ITEM_COMMENT:
                return WallCommentVH.instance(parent);
        }
        return null;
    }

    @Override
    public int getCurrentItemViewType(int position) {
        if (getCurrentItemCount() != position) {
            if (position == 0) {
                return ITEM_POST;
            } else {
                return ITEM_COMMENT;
            }
        }
        return -1;
    }

    private void configureCommentVH(final WallCommentVH holder, final int position) {

        holder.setData((WallCommentItem) commentList.get(position));
        holder.setDataUser(((WallCommentItem) commentList.get(position)).getUsuario());
        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopUpWall);
                PopupMenu popup = new PopupMenu(wrapper, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        wallCommentClickListener.onClickReportarPost(((WallCommentItem) commentList.get(position)).getIdcomentario());
                        return false;
                    }
                });

                popup.inflate(R.menu.pop_up_other_user_comment);

                popup.show();
            }
        });
        holder.iconLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentLikeListener commentLikeListener = new CommentLikeListener() {
                    @Override
                    public void onSuccessComment() {
                        if (((WallCommentItem) commentList.get(position)).getYaaplaudio() != 1) {
                            ((WallCommentItem) commentList.get(position)).setNaplausos(((WallCommentItem) commentList.get(position)).getNaplausos() + 1);
                            ((WallCommentItem) commentList.get(position)).setYaaplaudio(1);
                        } else {
                            ((WallCommentItem) commentList.get(position)).setNaplausos(((WallCommentItem) commentList.get(position)).getNaplausos() - 1);
                            ((WallCommentItem) commentList.get(position)).setYaaplaudio(0);
                        }
                        holder.setYaAplaudio(((WallCommentItem) commentList.get(position)));
                    }
                };

                wallCommentClickListener.onClapCommentListener(((WallCommentItem) commentList.get(position)).getIdcomentario(), commentLikeListener);


            }
        });
        holder.imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!((WallCommentItem) commentList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario()))
                    wallCommentClickListener.onShowProfileListener(((WallCommentItem) commentList.get(position)).getUsuario().getId_usuario());
            }
        });

        holder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallCommentClickListener.onExpandImagen(holder.imgComment.getDrawable());
            }
        });

        holder.editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommentEditListener commentEditListener = new CommentEditListener() {
                    @Override
                    public void onSuccessEdit(WallCommentItem wallCommentItem) {

                    }
                };

                wallCommentClickListener.onEditComment(idPost, (WallCommentItem) commentList.get(position), commentEditListener);
            }
        });

        holder.removeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (canDeleted) {
                    CommentDeleteListener commentDeleteListener = new CommentDeleteListener() {
                        @Override
                        public void onSuccessDeleteListener() {
                            Log.i(TAG, "--->borrando posicion" + position);
                            canDeleted = true;
                            commentList.remove(position);
                            notifyDataSetChanged();
                        }
                    };

                    wallCommentClickListener.onDeleteComment(idPost, (WallCommentItem) commentList.get(position), commentDeleteListener);
                }
            }
        });


    }

    private void configurePostViewHolder(final WallPostViewHolder holder, final int position) {
        holder.setDataPost((WallItem) commentList.get(position), null, wallCommentClickListener);
        holder.openComment();
        holder.commentIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallCommentClickListener.onClickComment();
            }
        });
        holder.menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(context, R.style.PopUpWall);
                PopupMenu popup = new PopupMenu(wrapper, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.delete_post) {
                            wallCommentClickListener.onDeletePost();
                        } else if (item.getItemId() == R.id.editar_post) {
                            wallCommentClickListener.onEditPostListener(((WallItem) commentList.get(position)));
                        } else {
                            wallCommentClickListener.onClickReportarPost(((WallItem) commentList.get(position)).getIdpost());
                        }

                        return false;
                    }
                });
                if (((WallItem) commentList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario())) {
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
                if (!((WallItem) commentList.get(position)).getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario()))
                    wallCommentClickListener.onShowProfileListener(((WallItem) commentList.get(position)).getUsuario().getId_usuario());
            }
        });

        holder.likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final WallAdapter.CommentListener commentListener = new WallAdapter.CommentListener() {
                    @Override
                    public void onSuccessComment() {
                        if (((WallItem) commentList.get(position)).getYaaplaudio() == 1) {
                            holder.setLike(false);
                            ((WallItem) commentList.get(position)).setYaaplaudio(0);
                            ((WallItem) commentList.get(position)).setNaplausos(((WallItem) commentList.get(position)).getNaplausos() - 1);
                            holder.sizeLike.setText(String.valueOf(((WallItem) commentList.get(position)).getNaplausos()));
                        } else {
                            holder.setLike(true);
                            ((WallItem) commentList.get(position)).setYaaplaudio(1);
                            ((WallItem) commentList.get(position)).setNaplausos(((WallItem) commentList.get(position)).getNaplausos() + 1);
                            holder.sizeLike.setText(String.valueOf(((WallItem) commentList.get(position)).getNaplausos()));
                        }
                    }
                };

                wallCommentClickListener.onClickLikeListener(((WallItem) commentList.get(position)).getIdpost(), commentListener);
            }
        });

        holder.imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wallCommentClickListener.onExpandImagen(holder.imgPost.getDrawable());
            }
        });
    }

    public void setWallCommentClickListener(WallCommentClickListener wallClickListener) {
        this.wallCommentClickListener = wallClickListener;
    }

    public interface WallCommentClickListener {

        void onShowProfileListener(String wallItem);

        void onClickComment();

        void onClapCommentListener(String idcomentario, CommentLikeListener commentLikeListener);

        void onClickLikeListener(String id_post, WallAdapter.CommentListener commentListener);

        void onExpandImagen(Drawable drawable);

        void onDeletePost();

        void onDeleteComment(String idpost, WallCommentItem item, CommentDeleteListener commentDeleteListener);

        void onEditComment(String idpost, WallCommentItem item, CommentEditListener commentEditListener);

        void onClickReportarPost(String id);

        void onEditPostListener(WallItem wallItem);

        void onClickReportarComment(String id);
    }

    public interface CommentLikeListener {
        void onSuccessComment();
    }

    public interface CommentDeleteListener {
        void onSuccessDeleteListener();
    }

    public interface CommentEditListener {
        void onSuccessEdit(WallCommentItem wallCommentItem);
    }

    public void addAll(List<WallCommentItem> list) {
        commentList.addAll(list);
        notifyDataSetChanged();
    }

    public List<Object> getCommentList() {
        return commentList;
    }

    public void updatePost(WallItem wallItem) {

        ((WallItem) commentList.get(0)).setMensaje(wallItem.getMensaje());
        ((WallItem) commentList.get(0)).setFoto(wallItem.getFoto());
        notifyItemChanged(0);
    }
}
