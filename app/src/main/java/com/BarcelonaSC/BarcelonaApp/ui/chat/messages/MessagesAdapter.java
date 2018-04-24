package com.BarcelonaSC.BarcelonaApp.ui.chat.messages;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import com.BarcelonaSC.BarcelonaApp.R;
import com.BarcelonaSC.BarcelonaApp.app.App;
import com.BarcelonaSC.BarcelonaApp.ui.chat.ChatConsts;
import com.BarcelonaSC.BarcelonaApp.utils.Commons;
import com.BarcelonaSC.BarcelonaApp.utils.CustomTextView;
import com.BarcelonaSC.BarcelonaApp.utils.PhotoUpload;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.io.File.separator;

/**
 * Created by Carlos on 22/01/2018.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {


    private static final String TAG = MessagesAdapter.class.getSimpleName();

    private Context context;
    private List<MessageModelView> messageModelViews;
    private OnItemClickListener onItemClickListener;


    public MessagesAdapter(Context context, OnItemClickListener onItemClickListener, List<MessageModelView> messageModelViews) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.messageModelViews = messageModelViews;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_message, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MessageModelView messageModelView = messageModelViews.get(position);

        DateTime fireBaseDate = new DateTime(messageModelView.getTime());
        DateTime now = new DateTime();
        Period period = new Period(fireBaseDate, now);
        String elapsed = getTimeWithFormat(period);

        Log.i(TAG, "--->conversion time" + elapsed);

        holder.tvApodo.setText(messageModelView.getApodo());
        holder.tvTime.setText(elapsed);
        holder.tvMessageContent.setText(messageModelView.getContent());

        if (messageModelView.getFoto() == null || !URLUtil.isValidUrl(messageModelView.getFoto())) {

            Glide.with(App.get()).load(ContextCompat.getDrawable(App.get(), R.drawable.silueta))
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(holder.civPhoto);
            holder.civPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.civPhoto.setTag(R.string.accept, messageModelView.getIdSender());
            PhotoUpload.uploadFoto(messageModelView.getIdSender(), messageModelView.getFoto(), new PhotoUpload.PhotoListener() {
                @Override
                public void onPhotoSucces(String foto) {
                    messageModelView.setFoto(foto);
                    if (holder.civPhoto.getTag(R.string.accept) == messageModelView.getIdSender())
                        Glide.with(App.get()).load(foto)
                                .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                                .into(holder.civPhoto);
                }

                @Override
                public void onError() {

                }
            });
        } else
            Glide.with(context)
                    .load(messageModelView.getFoto())
                    .apply(new RequestOptions().placeholder(R.drawable.silueta).error(R.drawable.silueta))
                    .into(holder.civPhoto);

        holder.civPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position), ChatConsts.TAG_ON_CLICK_ITEM_CONTENT);
            }
        });

        holder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position), ChatConsts.TAG_ON_CLICK_VIEW_TRASH);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickMessages(messageModelViews.get(position), ChatConsts.TAG_ON_CLICK_ITEM_CONTENT);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onLongClickMessage(messageModelViews.get(position));
                return true;
            }
        });

        if (messageModelView.isPressed()) {
            holder.itemView.setBackground(Commons.getDrawable(R.drawable.divider_bottom_blue));
            holder.ivTrash.setVisibility(View.INVISIBLE);
            holder.tvTime.setVisibility(View.VISIBLE);
        } else {
            holder.tvTime.setVisibility(View.VISIBLE);
            holder.itemView.setBackground(Commons.getDrawable(R.drawable.divider_bottom_white));
            holder.ivTrash.setVisibility(View.INVISIBLE);
        }

        if (messageModelView.getAmigos().isBloqueado()) {
            holder.itemView.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
        }


    }

    public String getTimeWithFormat(Period period) {
        PeriodFormatter formatter;
        String space = " ";
        if (period.getYears() != 0) {
            formatter = new PeriodFormatterBuilder()
                    .appendYears().appendSuffix(space).appendSuffix("año").appendSeparator(separator)
                    .printZeroNever()
                    .toFormatter();
        } else {
            if (period.getMonths() != 0) {
                formatter = new PeriodFormatterBuilder()
                        .appendMonths().appendSuffix(space).appendSuffix("mes").appendSeparator(separator)
                        .printZeroNever()
                        .toFormatter();
            } else {
                if (period.getDays() != 0) {
                    formatter = new PeriodFormatterBuilder()
                            .appendDays().appendSuffix(space).appendSuffix("días").appendSeparator(separator)
                            .printZeroNever()
                            .toFormatter();
                } else {
                    if (period.getHours() != 0) {
                        formatter = new PeriodFormatterBuilder()
                                .appendHours().appendSuffix(space).appendSuffix("horas").appendSeparator(separator)
                                .printZeroNever()
                                .toFormatter();
                    } else {
                        if (period.getMinutes() != 0) {
                            formatter = new PeriodFormatterBuilder()
                                    .appendMinutes().appendSuffix(space).appendSuffix("min").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        } else {
                            formatter = new PeriodFormatterBuilder()
                                    .appendSeconds().appendSuffix(space).appendSuffix("seg").appendSeparator(separator)
                                    .printZeroNever()
                                    .toFormatter();
                        }
                    }
                }
            }
        }
        return formatter.print(period.normalizedStandard());
    }

    @Override
    public int getItemCount() {
        return messageModelViews.size();
    }

    public void updateAll(List<MessageModelView> update) {
        messageModelViews.clear();
        messageModelViews.addAll(messageModelViews.size(), update);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.civ_photo)
        CircleImageView civPhoto;
        @BindView(R.id.tv_apodo)
        CustomTextView tvApodo;
        @BindView(R.id.tv_time)
        CustomTextView tvTime;
        @BindView(R.id.tv_message_content)
        TextView tvMessageContent;
        @BindView(R.id.iv_trash)
        ImageView ivTrash;
        @BindView(R.id.civ_state)
        CircleImageView civIsOnline;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClickMessages(MessageModelView messageModelViews, String TAG);

        void onLongClickMessage(MessageModelView messageModelViews);
    }

}
