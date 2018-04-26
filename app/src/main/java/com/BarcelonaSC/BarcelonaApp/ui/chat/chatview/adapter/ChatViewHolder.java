package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.app.manager.SessionManager;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.BarcelonaSC.BarcelonaApp.utils.SquareRoundedImage;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter.ChatViewHolder.TagMsg.TAG_NO_SELF;


public class ChatViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    boolean isImageFitToScreen;

    public enum TagMsg {

        TAG_SELF(1), TAG_NO_SELF(2);

        private int value = 0;

        private TagMsg(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    @BindView(R.id.chat_text_name)
    TextView nombreMsj;

    @BindView(R.id.circle_foto)
    CircleImageView circleFoto;

    @Nullable
    @BindView(R.id.circle_state)
    ImageView circleState;

    @BindView(R.id.iv_play)
    ImageView ivPlay;
    @BindView(R.id.chat_text_msg)
    TextView contentMsj;

    @BindView(R.id.chat_time_msg)
    TextView timeMsj;
    @BindView(R.id.ll_container_background)
    LinearLayout llContainerBackground;
    @BindView(R.id.chat_gif_msg)
    SimpleDraweeView chatGifMsg;

    @BindView(R.id.ll_content_message)
    LinearLayout llContentMessage;

    @BindView(R.id.rl_info_group)
    RelativeLayout rlInfoGroup;

    @BindView(R.id.tv_info_name)
    TextView tvInfoName;

    @BindView(R.id.chat_img_msg)
    public SquareRoundedImage imagenMsj;


    public static ChatViewHolder getInstance(ViewGroup parent, TagMsg type) {
        if (type == TAG_NO_SELF)
            return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_self_message, parent, false));
        else
            return new ChatViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_self_message, parent, false));
    }

    private ChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void setData(final MessageModelView item, boolean isgroup, boolean isNewUser) {
        Log.i("MESSAGE", " ---> " + item.toString());

        if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.INFO)) {
            llContentMessage.setVisibility(View.GONE);
            rlInfoGroup.setVisibility(View.VISIBLE);
            tvInfoName.setText(item.getApodo() + " ha dejado el grupo");

        } else {
            llContentMessage.setVisibility(View.VISIBLE);
            rlInfoGroup.setVisibility(View.GONE);

            timeMsj.setVisibility(View.VISIBLE);

            timeMsj.setText("");
            if (item.getTime() != null) {
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTimeInMillis(item.getTime());
                String date;
                if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
                    date = "0" + calendar.get(Calendar.HOUR_OF_DAY) + ":";
                } else {
                    date = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + ":");
                }

                if (calendar.get(Calendar.MINUTE) < 10) {
                    date = date + "0" + calendar.get(Calendar.MINUTE);
                } else {
                    date = date + String.valueOf(calendar.get(Calendar.MINUTE));
                }

                timeMsj.setText(date);
            }
            if (isNewUser) {
                if (item.isMine()) {

                    llContainerBackground.setBackground(ContextCompat.getDrawable(App.get(), R.drawable.background_self_message));
                } else {
                    llContainerBackground.setBackground(ContextCompat.getDrawable(App.get(), R.drawable.background_message));
                }
                nombreMsj.setVisibility(View.VISIBLE);
                circleFoto.setVisibility(View.VISIBLE);
            } else {
                if (item.isMine()) {
                    llContainerBackground.setBackground(ContextCompat.getDrawable(App.get(), R.drawable.background_self_message2));
                } else {

                    llContainerBackground.setBackground(ContextCompat.getDrawable(App.get(), R.drawable.background_message2));
                }
                circleFoto.setVisibility(View.INVISIBLE);
                nombreMsj.setVisibility(View.GONE);
            }


            if (isgroup) {

                nombreMsj.setText(item.getApodo());
                if (item.getFoto() == null || !URLUtil.isValidUrl(item.getFoto())) {

                    final Long id;
                    if (item.isMine())
                        id = Long.valueOf(SessionManager.getInstance().getUser().getId_usuario());
                    else
                        id = item.getIdSender();

                    circleFoto.setImageDrawable(ContextCompat.getDrawable(App.get(), R.drawable.silueta));
                    circleFoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    circleFoto.setTag(R.string.accept, id);
                    PhotoUpload.uploadFoto(id
                            , item.getFoto(), new PhotoUpload.PhotoListener() {
                                @Override
                                public void onPhotoSucces(String foto) {
                                    item.setFoto(foto);
                                    if (circleFoto.getTag(R.string.accept) == id)
                                        Glide.with(App.get())
                                                .load(foto)
                                                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                                .into(circleFoto);
                                }

                                @Override
                                public void onError() {

                                }
                            });
                } else
                    Glide.with(App.get())
                            .load(item.getFoto())
                            .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                            .into(circleFoto);
            } else {
                circleFoto.setVisibility(View.INVISIBLE);
                nombreMsj.setVisibility(View.GONE);
            }


            if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.IMAGEN)) {

                if (!item.getContent().isEmpty()) {

                    ivPlay.setVisibility(View.GONE);
                    chatGifMsg.setVisibility(View.GONE);
                    imagenMsj.setVisibility(View.VISIBLE);
                    contentMsj.setVisibility(View.GONE);

                    Glide.with(App.get())
                            .load(item.getContent())
                            .thumbnail(0.1f)
                            .apply(new RequestOptions().centerCrop())
                            .into(imagenMsj);

                    //timeMsj.setText(item.getTime());
                }
            } else if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.GIF)) {

                ivPlay.setVisibility(View.GONE);
                chatGifMsg.setVisibility(View.VISIBLE);
                imagenMsj.setVisibility(View.GONE);
                contentMsj.setVisibility(View.GONE);

                chatGifMsg.getHierarchy()
                        .setPlaceholderImage(new ColorDrawable(ContextCompat.getColor(context, R.color.gray_300)));
                chatGifMsg.getHierarchy().setFailureImage(new ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent)));

                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(item.getContent()))
                        .setLocalThumbnailPreviewsEnabled(true)
                        .setCacheChoice(ImageRequest.CacheChoice.SMALL)
                        .build();


                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setAutoPlayAnimations(true)
                        .build();
                chatGifMsg.setController(controller);
            } else if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.TEXTO)) {

                if (!item.getContent().isEmpty()) {
                    ivPlay.setVisibility(View.GONE);
                    chatGifMsg.setVisibility(View.GONE);
                    imagenMsj.setVisibility(View.GONE);
                    contentMsj.setVisibility(View.VISIBLE);

                    contentMsj.setText(item.getContent());

                    //timeMsj.setText(item.getTime());
                }
            } else if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.VIDEO)) {
                if (!item.getContent().isEmpty()) {

                    ivPlay.setVisibility(View.VISIBLE);
                    chatGifMsg.setVisibility(View.GONE);
                    imagenMsj.setVisibility(View.VISIBLE);
                    contentMsj.setVisibility(View.GONE);
                    Log.i("TAG", "----///55 " + item.getContent());
                    if (item.getThunbmailVideoUrl() != null) {
                        Glide.with(App.get())
                                .load(item.getThunbmailVideoUrl())
                                .thumbnail(0.1f)
                                .apply(new RequestOptions().centerCrop())
                                .into(imagenMsj);
                    } else {
                        Glide.with(App.get())
                                .load(item.getContent())
                                .thumbnail(0.1f)
                                .apply(new RequestOptions().centerCrop())
                                .into(imagenMsj);
                    }
                    //timeMsj.setText(item.getTime());
                }
            }
        }
    }


}
