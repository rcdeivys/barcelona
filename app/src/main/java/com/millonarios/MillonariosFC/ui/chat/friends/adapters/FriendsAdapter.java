package com.millonarios.MillonariosFC.ui.chat.friends.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.App;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsConsts;
import com.millonarios.MillonariosFC.ui.chat.friends.FriendsModelView;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;
import com.millonarios.MillonariosFC.utils.PhotoUpload;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Cesar on 23/01/2018.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    private static final String TAG = FriendsAdapter.class.getSimpleName();

    private Context context;
    private List<FriendsModelView> listfriendsModelViews;
    private OnItemClickListener onItemClickListener;

    public FriendsAdapter(Context context, List<FriendsModelView> listfriendsModelViews, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.listfriendsModelViews = listfriendsModelViews;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(listfriendsModelViews.get(position), context, onItemClickListener);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (listfriendsModelViews != null) {
            if (listfriendsModelViews.size() > 0) {
                return listfriendsModelViews.size();
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_content)
        LinearLayout item_content;
        @BindView(R.id.civ_photo)
        CircleImageView civPhoto;
        @BindView(R.id.civ_state)
        CircleImageView civIsOnline;
        @BindView(R.id.tv_apodo)
        FCMillonariosTextView tvApodo;
        @BindView(R.id.tv_time)
        FCMillonariosTextView tvTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.iv_trash)
        ImageView ivTrash;
        Context context;

        FriendsModelView friendsModelView;
        private OnItemClickListener onItemClickListener;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            tvTime.setVisibility(View.INVISIBLE);
            tvMessageContent.setVisibility(View.INVISIBLE);

            item_content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClickItem(friendsModelView);
                    return true;
                }
            });

            item_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickItem(friendsModelView, FriendsConsts.TAG_ON_CLICK_ITEM_CONTENT);
                }
            });

            civPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickItem(friendsModelView, FriendsConsts.TAG_ON_CLICK_VIEW_POPUP);
                }
            });


            ivTrash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickItem(friendsModelView, FriendsConsts.TAG_ON_CLICK_VIEW_TRASH);
                }
            });
        }

        public void bindData(FriendsModelView f, Context context, OnItemClickListener listener) {
            this.friendsModelView = f;
            this.onItemClickListener = listener;
            this.context = context;
            PhotoUpload.uploadFoto(friendsModelView.getId_amigo(), new PhotoUpload.PhotoListener() {
                @Override
                public void onPhotoSucces(String foto) {
                    Glide.with(App.getAppContext())
                            .load(foto)
                            .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                            .into(civPhoto);
                    civPhoto.setBorderColor(ContextCompat.getColor(App.getAppContext(), R.color.black));
                    civPhoto.setBorderWidth(4);
                }

                @Override
                public void onError() {

                }
            });
            //                            apodo-nombre                             //
            tvApodo.setText(nameOrApodo(friendsModelView));

            //                            statuschat                             //
            if (friendsModelView.getIsonline() == FriendsModelView.STATUS.ONLINE) {
                civIsOnline.setImageResource(R.color.green_online);
            } else if (friendsModelView.getIsonline() == FriendsModelView.STATUS.OFFLINE) {
                civIsOnline.setImageResource(R.color.rojo_offline);
            } else if (friendsModelView.getIsonline() == FriendsModelView.STATUS.BUSY) {
                civIsOnline.setImageResource(R.color.background_yellow);
            }

            //                            presionado                             //

            if (friendsModelView.isPressed()) {
                item_content.setBackground(Commons.getDrawable(R.drawable.divider_bottom_blue));
                ivTrash.setVisibility(View.VISIBLE);
            } else {
                item_content.setBackground(Commons.getDrawable(R.drawable.divider_bottom_white));
                ivTrash.setVisibility(View.INVISIBLE);
            }

            //                            foto                             //


            //                            usuarios bloqueados                             //
            if (friendsModelView.getBloqueado()) {
                item_content.setAlpha(Float.parseFloat("0.5"));
            } else {
                item_content.setAlpha(Float.parseFloat("1"));
            }
        }

        public String nameOrApodo(FriendsModelView friendsModelView) {
            String apodo = friendsModelView.getApodo();
            if (apodo != null && !apodo.isEmpty())
                return friendsModelView.getApodo();
            else
                return friendsModelView.getNombre();
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickItem(FriendsModelView friendsModelView, String TAG);

        void onLongClickItem(FriendsModelView friendsModelView);
    }
}
