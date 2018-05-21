package com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.holders;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.models.UsuariosAplauso;
import com.BarcelonaSC.BarcelonaApp.models.WallItem;
import com.BarcelonaSC.BarcelonaApp.ui.wall.DialogClose;
import com.BarcelonaSC.BarcelonaApp.ui.wall.WallClapListAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.comment.views.adapters.WallCommentAdapter;
import com.BarcelonaSC.BarcelonaApp.ui.wall.list.views.adapters.WallAdapter;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomVideoView;
import com.BarcelonaSC.BarcelonaApp.utils.FCMillonariosTextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by Leonardojpr on 1/9/18.
 */

public class WallPostViewHolder extends RecyclerView.ViewHolder implements CustomVideoView.CustomVideoViewOnListener {

    @BindView(R.id.img_profile)
    public CircleImageView imgProfile;
    @BindView(R.id.text_nickname)
    FCMillonariosTextView textNickName;
    @BindView(R.id.menu_icon)
    public ImageView menuIcon;
    @BindView(R.id.like_icon)
    public ImageView likeIcon;
    @BindView(R.id.arrow_drop_up)
    public AppCompatImageView arrowDropUpIcon;
    @BindView(R.id.comment_icon)
    public ImageView commentIcon;
    @BindView(R.id.date_post)
    FCMillonariosTextView datePost;
    @BindView(R.id.hour_post)
    FCMillonariosTextView hourPost;
    @BindView(R.id.cant_comment)
    public FCMillonariosTextView sizeComent;
    @BindView(R.id.count_like)
    public FCMillonariosTextView sizeLike;
    @BindView(R.id.img_post)
    public GifImageView imgPost;
    @BindView(R.id.text_post)
    EmojiconTextView textPost;
    @BindView(R.id.video_post)
    public CustomVideoView videoPost;
    @BindView(R.id.list_clap)
    FCMillonariosTextView listClap;

    WallItem wallItem;
    StringBuilder aplausos;
    int count_clap = 0;

    private Context context;

    WallAdapter.WallClickListener wallClickListener;
    WallCommentAdapter.WallCommentClickListener wallCommentClickListener;


    public static WallPostViewHolder getInstance(ViewGroup parent) {
        return new WallPostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false));
    }


    public WallPostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }


    public void setDataPost(final WallItem item, WallAdapter.WallClickListener wallClickListener, WallCommentAdapter.WallCommentClickListener wallCommentClickListener) {
        this.wallClickListener = wallClickListener;
        this.wallCommentClickListener = wallCommentClickListener;
        this.wallItem = item;
        textNickName.setText(item.getUsuario().getApodo() != null && !item.getUsuario().getApodo().equals("") ? item.getUsuario().getApodo() : item.getUsuario().getNombre() + " " + item.getUsuario().getApellido());


        if (item.getUsuario().getFoto() != null) {
            Glide.with(context).load(item.getUsuario().getFoto()).apply(new RequestOptions().error(Commons.getDrawable(R.drawable.silueta)).placeholder(Commons.getDrawable(R.drawable.silueta))).into(imgProfile);
        }

        if (item.getFecha() != null) {

            datePost.setText(Commons.getStringDate(item.getFecha()));
            hourPost.setText(Commons.getStringHour(item.getFecha()));

        }

        if (item.getYaaplaudio() == 1) {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_2));
        } else {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_1));
        }

        if (item.getNcomentarios() >= 0) {
            sizeComent.setVisibility(View.VISIBLE);
            sizeComent.setText(String.valueOf(item.getNcomentarios()));
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

            if (Commons.isVideoFile(item.getFoto())) {

                videoPost.setVideoUrl(item.getFoto(), 0, false);
                videoPost.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Commons.dpToPx(250)));
                imgPost.setVisibility(View.GONE);
                videoPost.setVisibility(View.VISIBLE);
                videoPost.setCustomVideoViewOnListener(this);
            } else {
                setPhoto(item.getFoto());
                imgPost.setVisibility(View.VISIBLE);
                videoPost.setVisibility(View.GONE);
            }
        } else {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(Commons.dpToPx(10), Commons.dpToPx(35), Commons.dpToPx(10), Commons.dpToPx(10));
            textPost.setLayoutParams(params);
        }
        aplausos = new StringBuilder();
        listName(item.getUsuariosAplausos());


    }

    private void listName(List<UsuariosAplauso> item) {
        if (item != null && !item.isEmpty()) {
            if (item.size() == 0) {
                listClap.setVisibility(View.INVISIBLE);
            } else if (item.size() == 1) {
                listClap.setText(Html.fromHtml("<b>" + item.get(0).getNombre() + "</b>" + " ha aplaudido"));
                listClap.setVisibility(View.VISIBLE);
            } else if (item.size() == 2) {
                listClap.setText(Html.fromHtml("<b>" + item.get(0).getNombre() + "</b> y <b>" + item.get(1).getNombre() + "</b>" + " han aplaudido"));
                listClap.setVisibility(View.VISIBLE);
            } else {
                listClap.setText(Html.fromHtml("<b>" + item.get(0).getNombre() + "</b>, <b>" + item.get(1).getNombre() + "</b>" + " y <b>" + (item.size() - 2) + ((item.size() - 2) == 1 ? " hincha" : " hinchas") + "</b> más han aplaudido"));
                listClap.setVisibility(View.VISIBLE);
            }
            if (listClap.getVisibility() == View.VISIBLE)
                listClap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showClapList();
                    }
                });
        } else {
            listClap.setVisibility(View.INVISIBLE);
        }
    }

    private void showClapList() {
        final View dialoglayout = LayoutInflater.from(context).inflate(R.layout.dialog_wall_clap_post, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialoglayout);
        final AlertDialog alertDialog = builder.show();
        WallClapListAdapter wallClapListAdapter = new WallClapListAdapter(context, wallItem.getUsuariosAplausos(), wallClickListener, wallCommentClickListener, new DialogClose() {
            @Override
            public void closeDialogListener() {
                alertDialog.dismiss();
            }
        });
        RecyclerView recyclerView = (RecyclerView) dialoglayout.findViewById(R.id.rv_claps);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(wallClapListAdapter);

        Button btnYes = (Button) dialoglayout.findViewById(R.id.back);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void setMessage(String message) {
        textPost.setVisibility(View.VISIBLE);
        imgPost.setVisibility(View.GONE);
        videoPost.setVisibility(View.GONE);

        textPost.setText(StringEscapeUtils.unescapeJava(message));

    }

    private void setPhoto(String photo) {
        imgPost.setVisibility(View.VISIBLE);
        Glide.with(context).load(photo).apply(new RequestOptions().error(Commons.getDrawable(R.drawable.bsc_news_wm)).placeholder(Commons.getDrawable(R.drawable.bsc_news_wm)).override(Commons.getWidthDisplay()).diskCacheStrategy(DiskCacheStrategy.ALL)).into(imgPost);

    }

    public void openComment() {
        // menuIcon.setVisibility(View.GONE);
        sizeComent.setVisibility(View.GONE);
        arrowDropUpIcon.setVisibility(View.VISIBLE);
    }

    private void setVideo(WallItem item) {
     /*   imgPost.setVisibility(View.GONE);
        videoPost.setVisibility(View.VISIBLE);
        textPost.setVisibility(View.GONE);
        videoPost.setVideoUrl(item.getMessage());
*/
    }

    private List<UsuariosAplauso> deleteUserListClap(List<UsuariosAplauso> item) {
        for (int i = 0; i < item.size(); i++) {
            if (item.get(i).getId() == Integer.parseInt(SessionManager.getInstance().getUser().getId_usuario())) {
                item.remove(i);
            }
        }
        return item;
    }

    public void setLike(boolean like) {
        if (like) {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_2));
            UsuariosAplauso usuariosAplauso = new UsuariosAplauso();
            usuariosAplauso.setId(Integer.parseInt(SessionManager.getInstance().getUser().getId_usuario()));
            usuariosAplauso.setFoto(SessionManager.getInstance().getUser().getFoto());
            usuariosAplauso.setNombre(SessionManager.getInstance().getUser().getNombre() + " " + SessionManager.getInstance().getUser().getApellido());
            wallItem.getUsuariosAplausos().add(usuariosAplauso);
            listName(wallItem.getUsuariosAplausos());
        } else {
            likeIcon.setBackground(Commons.getDrawable(R.drawable.icon_aplauso_1));
            listName(deleteUserListClap(wallItem.getUsuariosAplausos()));
        }
    }


    @Override
    public void onPrepared() {
        //  videoPost.onVideoSizeChanged(1280, 720);


        videoPost.setLayoutParamsVideo(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        if (wallItem.getThumbnail() != null)
            videoPost.setImage(context, wallItem.getThumbnail());
        else
            videoPost.setImage(context, wallItem.getFoto());


    }

    @Override
    public void videoIsDorado() {

    }
}
