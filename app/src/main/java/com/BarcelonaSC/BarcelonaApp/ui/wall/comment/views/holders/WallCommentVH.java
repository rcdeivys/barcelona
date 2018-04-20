package com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.UserItem;
import com.BarcelonaSC.BarcelonaApp.models.WallCommentItem;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringEscapeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by Leonardojpr on 1/16/18.
 */

public class WallCommentVH extends RecyclerView.ViewHolder {

    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.user_name)
    FCMillonariosTextView userName;
    @BindView(R.id.btn_clap)
    public FCMillonariosTextView clap;
    @BindView(R.id.count_like)
    public FCMillonariosTextView countLike;
    @BindView(R.id.remaining)
    FCMillonariosTextView remaining;
    @BindView(R.id.img_comment)
    public ImageView imgComment;
    @BindView(R.id.comment)
    EmojiconTextView inputComment;
    @BindView(R.id.content_comment)
    RelativeLayout contentComment;
    @BindView(R.id.like_icon)
    public ImageView iconLike;
    @BindView(R.id.edit_icon)
    public ImageView editIcon;
    @BindView(R.id.remove_icon)
    public ImageView removeIcon;
    @BindView(R.id.icon_menu)
    public ImageView menuIcon;

    private Context context;

    public static WallCommentVH instance(ViewGroup parent) {
        return new WallCommentVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
    }

    public WallCommentVH(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();

    }

    public void setData(WallCommentItem commentItem) {
        userName.setText(commentItem.getUsuario().getApodo() != null && !commentItem.getUsuario().getApodo().equals("") ? commentItem.getUsuario().getApodo() : commentItem.getUsuario().getNombre() + " " + commentItem.getUsuario().getApellido());
        inputComment.setText(StringEscapeUtils.unescapeJava(commentItem.getComentario()));

        if (!commentItem.getUsuario().getId_usuario().equals(SessionManager.getInstance().getUser().getId_usuario())) {
            removeIcon.setVisibility(View.GONE);
            editIcon.setVisibility(View.GONE);
            menuIcon.setVisibility(View.VISIBLE);
        } else {
            removeIcon.setVisibility(View.VISIBLE);
            editIcon.setVisibility(View.VISIBLE);
            menuIcon.setVisibility(View.GONE);
        }

        if (commentItem.getFecha() != null) {
            remaining.setText(Commons.dateToWallDate(commentItem.getFecha()));
        }

        if (commentItem.getComentario().equals("")) {
            inputComment.setVisibility(View.GONE);
        } else {
            inputComment.setVisibility(View.VISIBLE);
        }
        if (commentItem.getFoto() != null) {
            if (!commentItem.getFoto().equals("")) {

                imgComment.setVisibility(View.VISIBLE);
                Glide.with(context).load(commentItem.getFoto()).apply(new RequestOptions().placeholder(R.drawable.bsc_news_wm).error(R.drawable.bsc_news_wm)).into(imgComment);
            } else {
                imgComment.setVisibility(View.GONE);
            }
        }

        setYaAplaudio(commentItem);
    }

    public void setDataUser(UserItem user) {
        if (user.getFoto() != null) {
            Glide.with(context).load(user.getFoto()).apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta)).into(imgProfile);
        }

        userName.setText(user.getApodo() != null ? user.getApodo() : user.getNombre() + " " + user.getApellido());
    }

    public void setYaAplaudio(WallCommentItem commentItem) {
        if (commentItem.getYaaplaudio() == 1) {
            iconLike.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_2));
        } else {
            iconLike.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_1));
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            countLike.setText(Html.fromHtml("<b>" + String.valueOf(commentItem.getNaplausos()) + "</b>", Html.FROM_HTML_MODE_LEGACY) + (commentItem.getNaplausos() == 1 ? " Aplauso" : " Aplausos"));
        } else {
            countLike.setText(Html.fromHtml("<b>" + String.valueOf(commentItem.getNaplausos()) + "</b>") + (commentItem.getNaplausos() == 1 ? " Aplauso" : " Aplausos"));

        }
    }


}
