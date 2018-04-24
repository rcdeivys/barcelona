package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.commons.VideoActivity;
import com.BarcelonaSC.BarcelonaApp.models.firebase.Media;
import com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.ChatActivity;
import com.BarcelonaSC.BarcelonaApp.ui.wall.ImageExpandDialog;
import com.BarcelonaSC.BarcelonaApp.utils.Constants.Constant;
import com.BarcelonaSC.BarcelonaApp.utils.SquareRoundedImage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 05/04/2018.
 */
public class MultimediaAdapter extends RecyclerView.Adapter<MultimediaAdapter.ViewHolder> {

    private Context context;
    private List<Media> urlsList;

    public MultimediaAdapter(Context context, List<Media> urlsList) {
        this.context = context;
        this.urlsList = urlsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_square_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindData(urlsList.get(position), context);
    }

    @Override
    public int getItemCount() {
        if (urlsList != null)
            return urlsList.size();
        else
            return 0;
    }

    public void updateAll(List<Media> update) {
        if (urlsList.size() > 0)
            urlsList.clear();
        urlsList.addAll(urlsList.size(), update);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sqr_image_view)
        SquareRoundedImage sqr_image_view;
        @BindView(R.id.iv_play)
        ImageView ivPlay;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Media media, final Context context) {

            if (getTypeMsg(media.getTipoMedia()) == FirebaseManager.MsgTypes.VIDEO) {
                ivPlay.setVisibility(View.VISIBLE);
                if (media.getVideoThumbnail() != null) {
                    Glide.with(App.get())
                            .load(media.getVideoThumbnail())
                            .thumbnail(0.1f)
                            .apply(new RequestOptions().centerCrop())
                            .into(sqr_image_view);
                } else {
                    Glide.with(App.get())
                            .load(media.getUrlMedia())
                            .thumbnail(0.1f)
                            .apply(new RequestOptions().centerCrop())
                            .into(sqr_image_view);
                }
                sqr_image_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, VideoActivity.class);
                        intent.putExtra(Constant.Video.CURRENT_POSITION, 1);
                        intent.putExtra(Constant.Video.PLAY, true);
                        intent.putExtra(Constant.Video.URL, media.getUrlMedia());
                        context.startActivity(intent);
                    }
                });
            } else {
                sqr_image_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageExpandDialog imageExpandDialog = new ImageExpandDialog();
                        imageExpandDialog.setImageToExpand(sqr_image_view.getDrawable());
                        showDialogFragment(imageExpandDialog);
                    }
                });
                ivPlay.setVisibility(View.GONE);

                Glide.with(App.get())
                        .load(media.getUrlMedia())
                        .thumbnail(0.1f)
                        .apply(new RequestOptions().centerCrop())
                        .into(sqr_image_view);
            }


        }
    }

    private void showDialogFragment(DialogFragment dialogFragment) {
        FragmentTransaction ft = ((ChatActivity) context).getSupportFragmentManager().beginTransaction();
        ft.add(dialogFragment, dialogFragment.getTag());
        ft.commitAllowingStateLoss();
    }


    public static FirebaseManager.MsgTypes getTypeMsg(String type) {
        switch (type) {
            case "imagen":
                return FirebaseManager.MsgTypes.IMAGEN;
            case "gif":
                return FirebaseManager.MsgTypes.GIF;
            case "video":
                return FirebaseManager.MsgTypes.VIDEO;
            default:
                return FirebaseManager.MsgTypes.TEXTO;
        }
    }
}
