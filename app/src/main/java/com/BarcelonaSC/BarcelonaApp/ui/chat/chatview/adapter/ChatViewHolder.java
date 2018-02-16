package com.BarcelonaSC.BarcelonaApp.ui.chat.chatview.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.app.manager.FirebaseManager;
import com.BarcelonaSC.BarcelonaApp.ui.chat.messages.MessageModelView;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;

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

    @Nullable
    @BindView(R.id.chat_text_name)
    TextView nombreMsj;

    @Nullable
    @BindView(R.id.circle_foto)
    CircleImageView circleFoto;

    @Nullable
    @BindView(R.id.circle_state)
    ImageView circleState;

    @BindView(R.id.chat_text_msg)
    TextView contentMsj;

    @BindView(R.id.chat_time_msg)
    TextView timeMsj;

    @BindView(R.id.chat_img_msg)
    public com.BarcelonaSC.BarcelonaApp.utils.SquareImageView imagenMsj;


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

    public void setData(final MessageModelView item, boolean isgroup) {
        Log.i("MESSAGE", " ---> " + item.toString());

        if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.IMAGEN)) {

            if (!item.getContent().isEmpty() && !item.getApodo().isEmpty()) {

                imagenMsj.setVisibility(View.VISIBLE);
                contentMsj.setVisibility(View.GONE);

                Glide.with(App.get())
                        .load(item.getContent())
                        .into(imagenMsj);

                if (nombreMsj != null && !item.isMine()) {
                    if (isgroup) {
                        nombreMsj.setText(item.getApodo());
                        nombreMsj.setVisibility(View.VISIBLE);
                    } else {
                        nombreMsj.setVisibility(View.GONE);
                    }

                    Long id;
                    if (item.isMine())
                        id = Long.valueOf(item.getIdReceiver());
                    else
                        id = item.getIdSender();

                    PhotoUpload.uploadFoto(id, new PhotoUpload.PhotoListener() {
                        @Override
                        public void onPhotoSucces(String foto) {
                            item.setFoto(foto);
                            Glide.with(App.get())
                                    .load(foto)
                                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                    .into(circleFoto);
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
                //timeMsj.setText(item.getTime());
            }
        } else if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.TEXTO)) {

            if (!item.getContent().isEmpty() && !item.getApodo().isEmpty()) {
                imagenMsj.setVisibility(View.GONE);
                contentMsj.setVisibility(View.VISIBLE);

                contentMsj.setText(item.getContent());

                if (nombreMsj != null && !item.isMine()) {
                    if (isgroup) {
                        nombreMsj.setText(item.getApodo());
                        nombreMsj.setVisibility(View.VISIBLE);
                    } else {
                        nombreMsj.setVisibility(View.GONE);
                    }

                    Long id;
                    if (item.isMine())
                        id = Long.valueOf(item.getIdReceiver());
                    else
                        id = item.getIdSender();
                    PhotoUpload.uploadFoto(id, new PhotoUpload.PhotoListener() {
                        @Override
                        public void onPhotoSucces(String foto) {
                            Glide.with(App.get())
                                    .load(foto)
                                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta).centerCrop())
                                    .into(circleFoto);
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
                //timeMsj.setText(item.getTime());
            }
        } else if (item.getTypeMsg().equals(FirebaseManager.MsgTypes.VIDEO)) {

        }
    }


}
