package com.millonarios.MillonariosFC.ui.wall.list.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.millonarios.MillonariosFC.R;
import com.millonarios.MillonariosFC.app.manager.SessionManager;
import com.millonarios.MillonariosFC.models.WallItem;
import com.millonarios.MillonariosFC.utils.Commons;
import com.millonarios.MillonariosFC.utils.CustomDate;
import com.millonarios.MillonariosFC.utils.CustomVideoView;
import com.millonarios.MillonariosFC.utils.FCMillonariosTextView;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallPostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.text_nickname)
    FCMillonariosTextView textNickName;
    @BindView(R.id.menu_icon)
    public ImageView menuIcon;
    @BindView(R.id.like_icon)
    public ImageView likeIcon;
    @BindView(R.id.comment_icon)
    public ImageView commentIcon;
    @BindView(R.id.date_post)
    FCMillonariosTextView datePost;
    @BindView(R.id.cant_comment)
    public FCMillonariosTextView sizeComent;
    @BindView(R.id.count_like)
    public FCMillonariosTextView sizeLike;
    @BindView(R.id.img_post)
    ImageView imgPost;
    @BindView(R.id.text_post)
    EmojiconTextView textPost;
    @BindView(R.id.video_post)
    CustomVideoView videoPost;

    private Context context;

    public static WallPostViewHolder getInstance(ViewGroup parent) {
        return new WallPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }


    public WallPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public void setDataPost(WallItem item) {

        textNickName.setText(item.getUsuario().getApodo() != null && !item.getUsuario().getApodo().equals("") ? item.getUsuario().getApodo() : item.getUsuario().getNombre() + " " + item.getUsuario().getApellido());

        if (item.getUsuario().getFoto() != null) {
            Glide.with(context).load(item.getUsuario().getFoto()).apply(new RequestOptions().error(Commons.getDrawable(R.drawable.silueta)).placeholder(Commons.getDrawable(R.drawable.silueta))).into(imgProfile);
        }

        if (item.getFecha() != null) {
            datePost.setText(CustomDate.timeAgo(Commons.getDateString(item.getFecha().toString()).getTime()));
        }

        if (item.getYaaplaudio() == 1) {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso));
        } else {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_2));
        }

        if (item.getUsuario().getId_usuario().toString().trim().toLowerCase().equals(SessionManager.getInstance().getUser().getId_usuario().toString().trim().toLowerCase())) {
            menuIcon.setVisibility(View.VISIBLE);
        } else {
            menuIcon.setVisibility(View.GONE);
        }

        if (item.getNcomentarios() > 0) {
            sizeComent.setVisibility(View.VISIBLE);
            sizeComent.setText(String.valueOf(item.getNcomentarios()) + " Comentarios");
        } else {
            sizeComent.setVisibility(View.INVISIBLE);
        }
        sizeLike.setText(String.valueOf(item.getNaplausos()));

        if (item.getMensaje() != null && !item.getMensaje().equals("")) {
            setMessage(item.getMensaje());
        } else {
            textPost.setVisibility(View.GONE);
        }

        if (item.getFoto() != null && !item.getFoto().equals("")) {
            setPhoto(item.getFoto());
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Commons.dpToPx(10), Commons.dpToPx(35), Commons.dpToPx(10), Commons.dpToPx(10));
            textPost.setLayoutParams(params);
        }
    }

    private void setMessage(String message) {
        textPost.setVisibility(View.VISIBLE);
        imgPost.setVisibility(View.GONE);
        videoPost.setVisibility(View.GONE);

        textPost.setText(StringEscapeUtils.unescapeJava(message));

    }

    private void setPhoto(String photo) {
        imgPost.setVisibility(View.VISIBLE);
        Glide.with(context).load(photo).apply(new RequestOptions().error(Commons.getDrawable(R.drawable.logo_transparencia)).placeholder(Commons.getDrawable(R.drawable.logo_transparencia))).into(imgPost);

    }

    private void setVideo(WallItem item) {
     /*   imgPost.setVisibility(View.GONE);
        videoPost.setVisibility(View.VISIBLE);
        textPost.setVisibility(View.GONE);
        videoPost.setVideoUrl(item.getMessage());
*/
    }

    public void setLike(boolean like) {
        if (like) {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso));
        } else {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_2));
        }
    }


}
